package com.sevensemesterproject.infoJam.response;

import com.sevensemesterproject.infoJam.util.JamStatus;
import com.sevensemesterproject.infoJam.util.ReportStatus;

public class ReportResponse{

	private Long id;
	private Long reporterId;
	private String location;
	private Double lalitude;
	private Double longitude;
	private ReportStatus reportStatus;
	private JamStatus jamStatus;
	private String reporter;
	private Long contributiuonCount;
	private String createdDate;
	private String createdTime;
	
	public String getLocation() {
		return location;
	}
	public Long getReporterId() {
		return reporterId;
	}
	public void setReporterId(Long reporterId) {
		this.reporterId = reporterId;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public Long getContributiuonCount() {
		return contributiuonCount;
	}
	public void setContributiuonCount(Long contributiuonCount) {
		this.contributiuonCount = contributiuonCount;
	}
	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReporter() {
		return reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public ReportStatus getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(ReportStatus reportStatus) {
		this.reportStatus = reportStatus;
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
	public JamStatus getJamStatus() {
		return jamStatus;
	}
	public void setJamStatus(JamStatus jamStatus) {
		this.jamStatus = jamStatus;
	}	
}
