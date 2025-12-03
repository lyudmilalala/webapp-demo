package com.jerry.webappdemo;


import com.aliyun.dm20151123.models.SingleSendMailAdvanceRequest;
import com.aliyun.dm20151123.models.SingleSendMailResponseBody;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmailUtils {

    private static String ALIYUN_MSG_ACCESS_KEY = "my_acess_key";
    private static String ALIYUN_MSG_SECRET_KEY = "my_acess_secret";
    private static final String SES_SENDER_ALIAS_ZH = "Good Mouse Jerry";
    private static final String SES_SENDER_ALIAS_EN = "Good Mouse Jerry";

    public static com.aliyun.dm20151123.Client smsClient;

    @PostConstruct
    public void initClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                .setAccessKeyId(ALIYUN_MSG_ACCESS_KEY)
                .setAccessKeySecret(ALIYUN_MSG_SECRET_KEY)
                .setEndpoint("dm.aliyuncs.com");
        smsClient = new com.aliyun.dm20151123.Client(config);
    }

    public CommonResponse sendEmail(String email, String lang, String subject, String htmlContent, Map<String, InputStream> attachmentsMap) {
        log.info("============== Enter sendEmail ==============");
        CommonResponse commonResponse = null;

        SingleSendMailAdvanceRequest singleSendMailRequest = new SingleSendMailAdvanceRequest()
                .setAccountName("no-reply@cloud.spinq.cn")
                .setFromAlias(lang.equals("zh")? SES_SENDER_ALIAS_ZH : SES_SENDER_ALIAS_EN)
                .setAddressType(1)
//                .setTagName("控制台创建的标签");
                .setReplyToAddress(true) // 是否启用管理控制台中配置好回信地址（状态须验证通过），取值范围是字符串true或者false
                .setToAddress(email)
                .setSubject(subject)
                .setHtmlBody(htmlContent);
        if (attachmentsMap != null && attachmentsMap.size() > 0) {
            List<SingleSendMailAdvanceRequest.SingleSendMailAdvanceRequestAttachments> attachments = new ArrayList<>();
            for (String filename : attachmentsMap.keySet()) {
                SingleSendMailAdvanceRequest.SingleSendMailAdvanceRequestAttachments attachment1 = new SingleSendMailAdvanceRequest.SingleSendMailAdvanceRequestAttachments();
                attachment1.setAttachmentName(filename);
                // all InputStream objects can be input into the attachment
                attachment1.setAttachmentUrlObject(attachmentsMap.get( filename));
                attachments.add(attachment1);
            }
            singleSendMailRequest.setAttachments(attachments);
        }
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            SingleSendMailResponseBody reponseBody = smsClient.singleSendMailAdvance(singleSendMailRequest, runtime).getBody();
            log.info("Mail sent successfully, RequestId = " + reponseBody.getRequestId() + ", EnvId = " + reponseBody.getEnvId());
            commonResponse = new CommonResponse(HttpStatus.OK.value(), "");
        } catch (TeaException error) {
            log.error(error.getMessage(), error);
            log.error((String) error.getData().get("Recommend"));
            commonResponse = new CommonResponse(HttpStatus.FAILED_DEPENDENCY.value(), error.getMessage());
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            log.error(error.getMessage(), error);
            log.error((String) error.getData().get("Recommend"));
            commonResponse = new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), _error.getMessage());
        }
        log.info("============== Exit sendEmail ==============");
        return commonResponse;
    }

}
