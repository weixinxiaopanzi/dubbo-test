package org.dubbo.demo.domain;

import java.io.Serializable;

/**
 * test user
 *
 */
public class User implements Serializable{
	
	private static final long serialVersionUID = 6181663006645511805L;
	
	private Long id;
	private String userName;
	private String password;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
