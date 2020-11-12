package com.sevensemesterproject.infoJam.response;

import com.sevensemesterproject.infoJam.dto.AbstractDto;
import com.sevensemesterproject.infoJam.util.ContributionEnum;
import com.sevensemesterproject.infoJam.util.ContributionType;

public class ContributionResponse extends AbstractDto{
	
	private ContributionEnum contributionEnum;
	private Long contributor;
	private ContributionType contributionType;
	
	public ContributionType getContributionType() {
		return contributionType;
	}
	public void setContributionType(ContributionType contributionType) {
		this.contributionType = contributionType;
	}
	public ContributionEnum getContributionEnum() {
		return contributionEnum;
	}
	public void setContributionEnum(ContributionEnum contributionEnum) {
		this.contributionEnum = contributionEnum;
	}
	public Long getContributor() {
		return contributor;
	}
	public void setContributor(Long contributor) {
		this.contributor = contributor;
	}
}
