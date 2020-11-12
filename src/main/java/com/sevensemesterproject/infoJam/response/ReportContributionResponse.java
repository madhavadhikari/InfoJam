package com.sevensemesterproject.infoJam.response;

import com.sevensemesterproject.infoJam.util.ContributionType;

public class ReportContributionResponse {

	private ContributionType contributionType;
	private Long reportId;
	private String createdBy;

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public ContributionType getContributionType() {
		return contributionType;
	}
	public void setContributionType(ContributionType contributionType) {
		this.contributionType = contributionType;
	}
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}	
}
