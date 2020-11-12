package com.sevensemesterproject.infoJam.response;

import com.sevensemesterproject.infoJam.dto.AbstractDto;
import com.sevensemesterproject.infoJam.util.TrackStatus;

public class TrackUserResponse extends AbstractDto {

	private Long loginId;
	private String location;
	private Double lalitude;
	private Double longitude;
	private String email;
	private String username;
	private TrackStatus trackStatus;

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
	public TrackStatus getTrackStatus() {
		return trackStatus;
	}
	public void setTrackStatus(TrackStatus trackStatus) {
		this.trackStatus = trackStatus;
	}
}
