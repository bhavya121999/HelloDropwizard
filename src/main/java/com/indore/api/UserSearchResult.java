package com.indore.api;

/**
 * @author Amit Khandelwal
 */
public final class UserSearchResult {
	private final String firstName;
	private final String lastName;
	private final String emailId;
	private final float score;
	private final String userId;

	public UserSearchResult(String firstName, String lastName, String emailId, String password, String userId,
            float score) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.score = score;
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public float getScore() {
		return score;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getUserId() {
		return userId;
	}
}
