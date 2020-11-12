package com.sevensemesterproject.infoJam.request;

import java.io.Serializable;
import java.sql.Date;

import com.sevensemesterproject.infoJam.util.Gender;

public class UserEditRequest implements Serializable{
	
	private String fullName;		
	private String address;
	private String phone;
	private Gender gender;
	private Date dob;
	
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
}
