package com.sevensemesterproject.infoJam.request;

import java.io.Serializable;

import com.sevensemesterproject.infoJam.util.JamStatus;

public class ReportRegisterRequest implements Serializable{
	
	private JamStatus jamstatus;
	
	public JamStatus getJamstatus() {
		return jamstatus;
	}
	public void setJamstatus(JamStatus jamstatus) {
		this.jamstatus = jamstatus;
	}
}
