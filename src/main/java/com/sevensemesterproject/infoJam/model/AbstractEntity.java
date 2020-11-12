package com.sevensemesterproject.infoJam.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sevensemesterproject.infoJam.util.Status;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id; 
	
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	protected Date modifiedDate;
	
	protected Long createdBy;
	
	protected Long modifiedBy;
	
	@Enumerated(EnumType.STRING)
	protected Status status;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status active) {
		this.status = active;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long loginId) {
		this.createdBy = loginId;
	}
	public Long getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
