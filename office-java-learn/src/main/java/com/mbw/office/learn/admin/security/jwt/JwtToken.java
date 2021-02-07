package com.mbw.office.learn.admin.security.jwt;

import lombok.Data;

import java.util.Date;

/**
 * 	登录后的JWT的token基本信息
 * 
 * @author dinghq
 *
 */
@Data
public class JwtToken {
	private String token;
	private Date expireTime;
	private String username;

	public void setExpireTime(Date expireTime){
		this.expireTime = (expireTime != null ? (Date) expireTime.clone() : null);
	}

	public Date getExpireTime(){
		return expireTime != null ? expireTime : null;
	}
}
