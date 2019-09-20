package com.indore.api;

import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;

/**
 * Base search results builder class.
 *
 * @author Amit Khandelwal
 */
public abstract class SearchResult {
	protected final float score;

	public SearchResult(Builder builder) {
		this.score = builder.score;
	}

	public abstract static class Builder<B extends Builder<B>> {
		protected final float score;
		// optional field.
		protected long numberOfHits;
		protected RestStatus status;
		protected TimeValue took;
		protected Boolean terminatedEarly;
		protected boolean timedOut;

		public Builder(float score) {
			this.score = score;
		}

		public Builder setNumberOfHits(long numberOfHits) {
			this.numberOfHits = numberOfHits;
			return this;
		}

		public Builder setStatus(RestStatus status) {
			this.status = status;
			return this;
		}

		public Builder setTook(TimeValue took) {
			this.took = took;
			return this;
		}

		public Builder isTerminatedEarly(Boolean terminatedEarly) {
			this.terminatedEarly = terminatedEarly;
			return this;
		}

		public Builder isTimedOut(boolean timedOut) {
			this.timedOut = timedOut;
			return this;
		}

		public abstract SearchResult build();
	}

	public float getScore() {
		return score;
	}
}
