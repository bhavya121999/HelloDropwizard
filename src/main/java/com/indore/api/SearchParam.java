package com.indore.api;

import java.util.Set;

/**
 * Base params for search.
 *
 * @author Amit Khandelwal
 */

public class SearchParam {
	protected final String searchTerm;
	protected final Integer from;
	protected final Integer size;
	protected final Set<String> returnFields;
	// TODO:- add a sort fields.
	//protected final List<Pair<String, SortDirection>> sortFields;

    protected SearchParam(Builder builder){
    	this.searchTerm = builder.searchTerm;
    	this.from = builder.from;
    	this.size = builder.size;
    	this.returnFields = builder.returnFields;
	}

	public static class Builder<B extends Builder<B>> {
    	// Mandatory fields
		private final String searchTerm;

		// Optional fields with default values;
		private Set<String> returnFields;
		private Integer from=0;
		private Integer size=20;

		public Builder(String searchTerm) {
			this.searchTerm = searchTerm;
		}

		public Builder<B> from(Integer from){
			this.from = from;
			return this;
		}

		public Builder<B> size(Integer size){
			this.size = size;
			return this;
		}

		public Builder<B> returnFields(Set<String> returnFields) {
			this.returnFields = returnFields;
			return this;
		}

		public SearchParam build(){
			return new SearchParam(this);
		}
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public Integer getFrom() {
		return from;
	}

	public Integer getSize() {
		return size;
	}

	public Set<String> getReturnFields() {
		return returnFields;
	}
}
