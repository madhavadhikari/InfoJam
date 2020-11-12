package com.sevensemesterproject.infoJam.response;

import com.sevensemesterproject.infoJam.dto.AbstractDto;
import com.sevensemesterproject.infoJam.util.JamStatus;

public class NotificationResponse extends AbstractDto{

	private String location;
	private JamStatus jamStatus;
	private Long reportId;

	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public JamStatus getJamStatus() {
		return jamStatus;
	}
	public void setJamStatus(JamStatus jamStatus) {
		this.jamStatus = jamStatus;
	}
}
