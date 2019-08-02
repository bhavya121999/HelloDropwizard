package com.indore.api;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * <File Description>.
 *
 * @author Amit Khandelwal
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public  class SearchParameter {
	private  String searchTerm;

	public SearchParameter() {
	}

	public SearchParameter(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
}
