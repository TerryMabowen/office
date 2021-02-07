/**
 * 
 */
package com.mbw.office.learn.admin.security.jwt;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author dinghq
 *
 */
public class PermissonGrantedAuthority implements GrantedAuthority {
	private static final long serialVersionUID = 2214878762063170625L;

	private String permission;
	
	public PermissonGrantedAuthority(String permission) {
		this.permission = permission;
	}
	
	@Override
	public String getAuthority() {
		return permission;
	}

}
