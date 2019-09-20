package com.indore.api;

/**
 * Defines the user search param.
 *
 * @author Amit Khandelwal
 */
public class UserSearchParam extends SearchParam {
	private final Boolean includeDeleted;
	private Boolean isPrefixSearch;

	public UserSearchParam(Builder builder) {
		super(builder);
		includeDeleted = builder.includeDeleted;
		isPrefixSearch = builder.isPrefixSearch;
	}

	public static class Builder extends SearchParam.Builder<Builder> {
		// Mandatory field
		private Boolean includeDeleted;

		// Optional fields.
		private boolean isPrefixSearch;

		public Builder(String searchTerm, Boolean includeDeleted) {
			super(searchTerm);
			this.includeDeleted = includeDeleted;
		}

		private Builder isPrefixSearch(boolean isPrefixSearch) {
			this.isPrefixSearch = isPrefixSearch;
			return this;
		}

		public UserSearchParam build() {
			return new UserSearchParam(this);
		}
	}

	public Boolean isIncludeDeleted() {
		return includeDeleted;
	}

	public Boolean isPrefixSearch() {
		return isPrefixSearch;
	}
}
