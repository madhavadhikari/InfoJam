package com.sevensemesterproject.infoJam.response;

import java.util.Date;

import com.sevensemesterproject.infoJam.util.ContributionType;

public class NewGetAllMyReportAndContributionResponse {

	private String createdDate;
	private Long reportId;
	private ContributionType contributionType;
	
	public Long getReportId() {
		return reportId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public ContributionType getContributionType() {
		return contributionType;
	}
	public void setContributionType(ContributionType contributionType) {
		this.contributionType = contributionType;
	}	
}
