package com.sevensemesterproject.infoJam.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.sevensemesterproject.infoJam.util.TrackStatus;

@Entity
@Table(name = "trackUser")
public class TrackUser extends AbstractEntity{

	private Long loginId;
	private String location;
	private Double lalitude;
	private Double longitude;
	
	@NotNull(message = "Email cannot be NULL")
	private String email;
	
	private String username;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TrackStatus trackStatus;
	
	public TrackStatus getTrackStatus() {
		return trackStatus;
	}
	public void setTrackStatus(TrackStatus trackStatus) {
		this.trackStatus = trackStatus;
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
	public Long getLoginId() {
		return loginId;
	}
	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Double getLalitude() {
		return lalitude;
	}
	public void setLalitude(Double lalitude) {
		this.lalitude = lalitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
