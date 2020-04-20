package com.galaxy.api;

import java.util.Set;

/**
 * Base Abstract params for search.
 * This class in't intended to be used directly hence making it abstract.
 *
 * @author Amit Khandelwal
 */

public abstract class SearchParam {
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

	public static  abstract class Builder<B extends Builder<B>> {
    	// Mandatory fields
		protected final String searchTerm;

		// Optional fields with default values;
		protected Set<String> returnFields;
		protected Integer from=0;
		protected Integer size=20;

		public Builder(String searchTerm) {
			this.searchTerm = searchTerm;
		}

		public Builder<B> setFrom(Integer from){
			this.from = from;
			return this;
		}

		public Builder<B> setSize(Integer size){
			this.size = size;
			return this;
		}

		public Builder<B> setReturnFields(Set<String> returnFields) {
			this.returnFields = returnFields;
			return this;
		}

		public abstract SearchParam build();
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
