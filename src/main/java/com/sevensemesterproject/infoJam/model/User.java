package com.sevensemesterproject.infoJam.model;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.sevensemesterproject.infoJam.util.Gender;
import com.sevensemesterproject.infoJam.util.UserRole;

@Entity
@Table(name = "user")
public class User extends AbstractEntity{
	
	@NotNull(message = "FullName cannot be NULL")
	private String fullName;
	
	@NotNull(message = "Email cannot be NULL")
	private String email;
	
	private String address;
	private String phone;
	private Date dob;
	private String profilePicture;
	private String username;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	public String getFullName() {
		return fullName;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
}
