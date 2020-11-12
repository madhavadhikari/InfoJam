package com.sevensemesterproject.infoJam.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.sevensemesterproject.infoJam.util.ContributionEnum;
import com.sevensemesterproject.infoJam.util.ContributionType;

@Entity
@Table(name = "contribution")
public class Contribution extends AbstractEntity{
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="report_id")
	private Report  report;
	
	@Enumerated(EnumType.STRING)
	private ContributionEnum contributionEnum;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private ContributionType contributionType;

	public Report getReport() {
		return report;
	}
	public void setReport(Report report) {
		this.report = report;
	}
	public ContributionEnum getContributionEnum() {
		return contributionEnum;
	}
	public void setContributionEnum(ContributionEnum contributionEnum) {
		this.contributionEnum = contributionEnum;
	}
	public ContributionType getContributionType() {
		return contributionType;
	}
	public void setContributionType(ContributionType contributionType) {
		this.contributionType = contributionType;
	}
}
