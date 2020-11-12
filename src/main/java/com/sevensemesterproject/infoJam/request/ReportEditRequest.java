package com.sevensemesterproject.infoJam.request;

import java.io.Serializable;

import com.sevensemesterproject.infoJam.util.JamStatus;

public class ReportEditRequest implements Serializable {

	private long id;
	private String location;
	private JamStatus jamstatus;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public JamStatus getJamstatus() {
		return jamstatus;
	}
	public void setJamstatus(JamStatus jamstatus) {
		this.jamstatus = jamstatus;
	}	
}
