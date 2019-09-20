package com.indore.api;

import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;

/**
 * Base search result.
 *
 * @author Amit Khandelwal
 */
public class SearchResult {
	protected final float score;

	public SearchResult(Builder builder){
		this.score = builder.score;
	}

	public static class Builder<B extends Builder<B>> {
		private final float score;
		// optional field.
		private long numberOfHits;
		private RestStatus status;
		private TimeValue took;
		private Boolean terminatedEarly;
		private boolean timedOut;

		public Builder(float score) {
			this.score = score;
		}

		public Builder numberOfHits(long numberOfHits) {
			this.numberOfHits = numberOfHits;
			return this;
		}

		public Builder status(RestStatus status) {
			this.status = status;
			return this;
		}

		public Builder took(TimeValue took) {
			this.took = took;
			return this;
		}

		public Builder terminatedEarly(Boolean terminatedEarly) {
			this.terminatedEarly = terminatedEarly;
			return this;
		}

		public Builder timedOut(boolean timedOut) {
			this.timedOut = timedOut;
			return this;
		}

		public SearchResult build(){
			return new SearchResult(this);
		}
	}

	public float getScore() {
		return score;
	}
}
