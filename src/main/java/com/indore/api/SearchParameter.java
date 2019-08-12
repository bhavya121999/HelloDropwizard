package com.indore.api;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Parameters for user search.
 *
 * @author Amit Khandelwal
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchParameter {
    private String searchTerm;

    public SearchParameter() {
    }

    /**
     * @param searchTerm search term which needs to be searched.
     */
    public SearchParameter(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    /**
     * @return matching documents corresponding to searchTerm in user index.
     */

    public String getSearchTerm() {
        return searchTerm;
    }

    /**
     * @param searchTerm search term which needs to be searched.
     */

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}
