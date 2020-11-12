package com.sevensemesterproject.infoJam.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.sevensemesterproject.infoJam.util.JamStatus;
import com.sevensemesterproject.infoJam.util.ReportStatus;

@Entity
@Table(name = "report")
public class Report extends AbstractEntity{

	@NotNull(message = "Location needed")
	private String location;
	
	private Double lalitude;
	
	private Double longitude;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private JamStatus jamStatus;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private ReportStatus reportStatus;
	
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
	public JamStatus getJamStatus() {
		return jamStatus;
	}
	public void setJamStatus(JamStatus jamStatus) {
		this.jamStatus = jamStatus;
	}
	public ReportStatus getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(ReportStatus reportStatus) {
		this.reportStatus = reportStatus;
	}
}
