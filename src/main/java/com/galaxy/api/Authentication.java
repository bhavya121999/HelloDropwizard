package com.galaxy.api;

import com.google.common.base.MoreObjects;

/**
 * <File Description>.
 *
 * @author Amit Khandelwal
 */
public class Authentication {
	private String password;
	private String userId;
	private String emailId;
	private long mobileNumber;

	public Authentication(String password, String userId, String emailId, long mobileNumber) {
		this.password = password;
		this.userId = userId;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("userId", userId)
				.add("emailId", emailId)
				.add("mobileNumber", mobileNumber)
				.toString();
	}
}
