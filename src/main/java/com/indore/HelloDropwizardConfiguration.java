package com.indore;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class HelloDropwizardConfiguration extends Configuration {
    private ElasticsearchConfig elasticsearchConfig;

    @JsonProperty("elasticsearchConfiguration")
    public ElasticsearchConfig getElasticsearchConfig() {
        return elasticsearchConfig;
    }

    public void setElasticsearchConfig(ElasticsearchConfig elasticsearchConfig) {
        this.elasticsearchConfig = elasticsearchConfig;
    }
}
