package com.jerry.webappdemojpa.user.dto;

import com.jerry.webappdemojpa.user.UserEntity;
import lombok.Data;

@Data
public class UserInfoDTO {
    private Long uid;
    private String uname;
    private String email;
    private String phone;
    private String institution;
    private String wechat;
    private String wechatNickname;

    public UserInfoDTO() {
        super();
    }

    public UserInfoDTO(UserEntity u) {
        super();
        this.uid = u.getId();
        this.uname = u.getUname();
        this.email = u.getEmail();
        this.phone = u.getPhone();
        this.institution = u.getInstitution();
        this.wechat = u.getWechat();
        this.wechatNickname = u.getWechatNickname();
    }

}
