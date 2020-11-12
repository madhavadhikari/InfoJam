package com.sevensemesterproject.infoJam.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.sevensemesterproject.infoJam.util.NotificationStatus;

@Entity
public class Notification extends AbstractEntity{

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "report_id")
	private Report report;;

	@Enumerated(EnumType.STRING)
	private NotificationStatus notificationStatus;
	
	public NotificationStatus getNotificationStatus() {
		return notificationStatus;
	}
	public void setNotificationStatus(NotificationStatus notificationStatus) {
		this.notificationStatus = notificationStatus;
	}
	public Report getReport() {
		return report;
	}
	public void setReport(Report report) {
		this.report = report;
	}	
}
