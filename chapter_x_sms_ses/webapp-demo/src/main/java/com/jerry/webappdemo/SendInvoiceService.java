package com.jerry.webappdemo;

import com.aliyun.dm20151123.models.SingleSendMailAdvanceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SendInvoiceService {

    @Autowired
    private EmailUtils emailUtils;
    @Autowired
    private AliyunOssFileUtils aliyunOssFileUtils;

    public CommonResponse sendInvoice(String receiver, String orderCode, String attachmentBucket, String attachmentPath) {
        InputStream attachment = aliyunOssFileUtils.downloadFileStream(attachmentBucket, attachmentPath);
        String filename = attachmentPath.substring(attachmentPath.lastIndexOf("/") + 1);
        Map<String, InputStream> attachmentsMap = new HashMap<>();
        attachmentsMap.put(filename, attachment);
        return emailUtils.sendEmail(receiver, "zh", "Invoice Test", buildLowBalanceEmail(orderCode), attachmentsMap);
    }

    public static String buildLowBalanceEmail(String orderCode) {
        String greeting = "<div class=\"greeting\" style=\"font-size: 18px; color: #000000;\">Dear fellow,</div>\n";

        String body = "    <div class=\"body\" style=\"font-size: 18px; line-height: 38px; margin: 20px 0 40px;\">\n";
        body += "        <div>Your invoice for order <span class=\"code\" style=\"font-size: 20px; color: #ED7D31;\">" + orderCode + "</span> is attached as below.</div>\n";
        body += "    </div>";

        String footer = "    <div class=\"footer\" style=\" font-size: 14px; line-height: 24px; color: #A6A6A6;\">\n" +
                "        <div class=\"divider\" style=\"height: 1px; width: 100%; max-width: 450px; background-color: #A6A6A6; margin: 16px 0px;\"></div>\n" +
                "        <div>This is a system email. Please do not reply.</div>\n" +
                "        <div>Copyright © 2025 GoodMouseJerry All rights reserved</div>\n" +
                "    </div>\n";

        return getString(greeting, body, footer);
    }

    private static String getString(String greeting, String body, String footer) {
        String style = "<style>\n" +
                "    .email-wrapper {\n" +
                "        font-family: \"Microsoft YaHei\", \"Hiragino Sans GB\",\"Hiragino Sans GB W3\", \"Arial\";\n" +
                "        -webkit-font-smoothing: antialiased;\n" +
                "        -moz-osx-font-smoothing: grayscale;\n" +
                "    }\n" +
                "\n" +
                "    .greeting {\n" +
                "        font-size: 18px;\n" +
                "        color: #000000;\n" +
                "    }\n" +
                "\n" +
                "    .body {\n" +
                "        font-size: 18px;\n" +
                "        line-height: 38px;\n" +
                "        margin: 20px 0 40px;\n" +
                "    }\n" +
                "\n" +
                "    .body .normal {\n" +
                "        color: #000000;\n" +
                "    }\n" +
                "\n" +
                "    .body .code {\n" +
                "        font-size: 20px;\n" +
                "        color: #ED7D31;\n" +
                "    }\n" +
                "\n" +
                "    .body .tips {\n" +
                "        font-size: 14px;\n" +
                "        color: #A6A6A6;\n" +
                "    }\n" +
                "\n" +
                "    .footer {\n" +
                "        font-size: 14px;\n" +
                "        line-height: 24px;\n" +
                "        color: #A6A6A6;\n" +
                "    }\n" +
                "\n" +
                "    .footer .divider {\n" +
                "        height: 1px;\n" +
                "        width: 100%;\n" +
                "        max-width: 450px;\n" +
                "        background-color: #A6A6A6;\n" +
                "        margin: 16px 0px;\n" +
                "    }\n" +
                "</style>";

        String res = "<div class=\"email-wrapper\" style=\"font-family: 'Microsoft YaHei', 'Hiragino Sans GB','Hiragino Sans GB W3', 'Arial';  -webkit-font-smoothing: antialiased; -moz-osx-font-smoothing: grayscale;\">\n" +
                "<meta charset=\"utf-8\">\n" +
                greeting + body + footer + "</div>\n" + style;
        log.debug("res = " + res);
        return res;
    }

}
