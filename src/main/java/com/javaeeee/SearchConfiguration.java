package com.javaeeee;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

/**
 * <File Description>.
 *
 * @author Amit Khandelwal
 */
public class SearchConfiguration extends Configuration {
	private ElasticsearchConfig elasticsearchConfig;

	@JsonProperty("elasticsearchConfiguration")
	public ElasticsearchConfig getElasticsearchConfig() {
		return elasticsearchConfig;
	}

	public void setElasticsearchConfig(ElasticsearchConfig elasticsearchConfig) {
		this.elasticsearchConfig = elasticsearchConfig;
	}
}
