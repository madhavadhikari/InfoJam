package com.sevensemesterproject.infoJam.request;

import java.io.Serializable;
import java.sql.Date;

public class UserRegisterRequest implements Serializable{

	private String fullName;
	private String email;
	private String confirmPassword;
	private String password;

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
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
}
