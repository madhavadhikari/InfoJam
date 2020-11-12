package com.sevensemesterproject.infoJam.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.sevensemesterproject.infoJam.util.SeenStatus;

@Entity
public class NotificationReceivers implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	private Long receiverId;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private SeenStatus seenStatus;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "notification_id")
	private Notification notification;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "report_id")
	private Report report;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}
	public Notification getNotification() {
		return notification;
	}
	public void setNotification(Notification notification) {
		this.notification = notification;
	}
	public SeenStatus getSeenStatus() {
		return seenStatus;
	}
	public void setSeenStatus(SeenStatus seenStatus) {
		this.seenStatus = seenStatus;
	}
	public Report getReport() {
		return report;
	}
	public void setReport(Report report) {
		this.report = report;
	}	
}
