package com.jerry.webappdemojpa.qcloudDB;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class UserEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2011006106003447869L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
	private long id;
	
	@Column(name = "u_name", nullable = false)
	private String uname;

	@Column(name = "password")
	private String password;

	@Column(name = "phone")
//	@Convert(converter = ValueAttributeConverter.class)
	private String phone;
	
	@Column(name = "email")
//	@Convert(converter = ValueAttributeConverter.class)
	private String email;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "wechat")
//	@Convert(converter = ValueAttributeConverter.class)
	private String wechat;
	
	@Column(name = "wechat_nickname")
	private String wechatNickname;
	
	@Column(name = "institution")
	private String institution;
	
	@Column(name = "u_status", nullable = false)
	private String ustatus;

	@Column(name = "register_time", nullable = false, length=3)
	private Date registerTime;

	@Column(name = "sign_up_time", nullable = false, length=3)
	private Date signUpTime;
	
	@Column(name = "latest_login_time", nullable = false, length=3)
	private Date latestLoginTime;
	
	@Column(name = "latest_modification_time", nullable = false, length=3)
	private Date latestModificationTime;

	@Column(name = "is_virtual", columnDefinition="char(1) NOT NULL DEFAULT 'N'")
	private String isVirtual; // is a virtual user for jhub or not

	@Column(name = "version")
	private int version;
	
	public UserEntity() {
		
	}

	public UserEntity(String uname, String password, String phone, String email, String status) {
		this(uname, password, phone, email, false, status);
	}

	public UserEntity(String uname, String password, String phone, String email, boolean isVirtual, String status) {
		this.uname = uname;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.isVirtual = isVirtual? "Y":"N";
		this.registerTime = new Date();
		this.signUpTime = new Date();
		this.latestLoginTime = new Date();
		this.latestModificationTime = new Date();
		this.ustatus = status;
		this.version = 1;
	}

	public UserEntity(String uname, String wechatOpenId) {
		this.uname = uname;
		this.wechat = wechatOpenId;
		this.isVirtual = "N";
		this.registerTime = new Date();
		this.signUpTime = new Date();
		this.latestLoginTime = new Date();
		this.latestModificationTime = new Date();
		ustatus = "ACTIVE";
		this.version = 1;
	}

	public boolean getIsVirtual() { return isVirtual.equals("Y"); }

	public void setIsVirtual(boolean isVirtual) {
		this.isVirtual = isVirtual? "Y":"N";
	}

	@PreUpdate
    private void doPreUpdate() {
		latestModificationTime = new Date();
    }
}
