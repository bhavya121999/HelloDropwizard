package com.indore.api;

/**
 * UserSearchResult for a user search query.
 *
 * @author Amit Khandelwal
 */
public final class UserSearchResult extends SearchResult {
	private final String userId;
	private final String firstName;
	private final String lastName;
	private final String emailId;
	private final float score;

	public UserSearchResult(Builder builder) {
		super(builder);
		this.userId = builder.userId;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.emailId = builder.emailId;
		this.score = builder.score;
	}

	public static class Builder extends SearchResult.Builder<Builder> {
		// Mandatory fields in user search results.
		private final String userId;
		private final String firstName;
		private final String lastName;
		private final float score;

		// Optional fields
		private String emailId;

		public Builder(float score, String userId, String firstName, String lastName) {
			super(score);
			this.userId = userId;
			this.firstName = firstName;
			this.lastName = lastName;
			this.score = score;
		}

		public Builder emailId(String emailId) {
			this.emailId = emailId;
			return this;
		}

		@Override
		public UserSearchResult build() {
			return new UserSearchResult(this);
		}
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

	@Override
	public float getScore() {
		return score;
	}
}
