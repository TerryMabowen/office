/**
 * 
 */
package com.mbw.office.learn.admin.context;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dinghq
 * 
 */
@Data
public class LoginResult implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2357203635372573006L;
	
	private String token;
	private String username;
    private Date expireTime;

	public void setExpireTime(Date expireTime){
		this.expireTime = (expireTime != null ? (Date) expireTime.clone() : null);
	}

	public Date getExpireTime(){
		return expireTime != null ? expireTime : null;
	}

}
