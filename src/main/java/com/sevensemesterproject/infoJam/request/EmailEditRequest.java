package com.sevensemesterproject.infoJam.request;

public class EmailEditRequest {
	
	private String oldEmail;
	private String newEmail;
	private String confirmNewEmail;
	
	public String getOldEmail() {
		return oldEmail;
	}
	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}
	public String getNewEmail() {
		return newEmail;
	}
	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}
	public String getConfirmNewEmail() {
		return confirmNewEmail;
	}
	public void setConfirmNewEmail(String confirmNewEmail) {
		this.confirmNewEmail = confirmNewEmail;
	}
}
