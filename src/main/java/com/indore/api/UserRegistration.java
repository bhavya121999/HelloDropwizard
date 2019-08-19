package com.indore.api;

import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.base.MoreObjects;

/**
 * User registration API.
 *
 * @author Amit Khandelwal
 */

public class UserRegistration {
	@NotEmpty
	private String userId;
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	@NotEmpty
	private String emailId;
	
	private long mobileNumber;
	@NotEmpty
	private String password;

	private long createdDate;

	public UserRegistration() {
	}

	public UserRegistration(String userId, String firstName, String lastName, String emailId, long mobileNumber,
			String password, long createdDate) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.createdDate = createdDate;
	}

	public String getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public long getCreatedDate() {
		return createdDate;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}

	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("userId", userId)
				.add("firstName", firstName)
				.add("lastName", lastName)
				.add("emailId", emailId)
				.add("mobileNumber", mobileNumber)
				.add("createdDate", createdDate)
				.toString();
	}
}
