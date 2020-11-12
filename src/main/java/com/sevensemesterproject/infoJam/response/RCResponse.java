package com.sevensemesterproject.infoJam.response;

import java.util.List;

public class RCResponse {
	
	private String createdDate;
	List<ReportContributionResponse> rcr;
	
	public List<ReportContributionResponse> getRcr() {
		return rcr;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public void setRcr(List<ReportContributionResponse> rcr) {
		this.rcr = rcr;
	}	
}
