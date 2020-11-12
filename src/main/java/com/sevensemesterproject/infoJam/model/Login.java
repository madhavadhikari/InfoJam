package com.sevensemesterproject.infoJam.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.sevensemesterproject.infoJam.util.LoginStatus;

@Entity
@Table(name ="user_login")
public class Login extends AbstractEntity{
	@NotNull
	private Long userId;
	
	@NotNull(message = "Password cannot be NULL")
	private String password;
	
	private String token;
	private Date tokenExpDateTime;
	
	@NotNull 
	@Enumerated(EnumType.STRING)
	private LoginStatus loginStatus;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	} 
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getTokenExpDateTime() {
		return tokenExpDateTime;
	}
	public void setTokenExpDateTime(Date tokenExpDate) {
		this.tokenExpDateTime = tokenExpDate;
	}
	public LoginStatus getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(LoginStatus loginStatus) {
		this.loginStatus = loginStatus;
	}
}
