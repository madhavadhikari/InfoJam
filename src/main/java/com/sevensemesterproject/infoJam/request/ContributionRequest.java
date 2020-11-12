package com.sevensemesterproject.infoJam.request;

import java.io.Serializable;

import com.sevensemesterproject.infoJam.util.ContributionEnum;

public class ContributionRequest implements Serializable{

	private ContributionEnum contributionEnum;

	public ContributionEnum getContributionEnum() {
		return contributionEnum;
	}
	public void setContributionEnum(ContributionEnum contributionEnum) {
		this.contributionEnum = contributionEnum;
	}	
}
