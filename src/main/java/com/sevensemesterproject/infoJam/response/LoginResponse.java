package com.sevensemesterproject.infoJam.response;

import java.io.Serializable;
import com.sevensemesterproject.infoJam.util.LoginStatus;

public class LoginResponse implements Serializable {
	
	private Long userId;
	private Long loginId;
	private String fullName;	
	private String email;	
	private String token;
	private String username;
	private java.util.Date createdDate;
	private LoginStatus loginStatus;
	private String profilePicture;
	
	public Long getUserId() {
		return userId;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getLoginId() {
		return loginId;
	}
	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public java.util.Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	public LoginStatus getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(LoginStatus loginStatus) {
		this.loginStatus = loginStatus;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}	
}
