package com.sevensemesterproject.infoJam.request;

import java.io.Serializable;

public class TrackUserRequest implements Serializable {

	private String location;
	private Double lalitude;
	private Double longitude;

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
