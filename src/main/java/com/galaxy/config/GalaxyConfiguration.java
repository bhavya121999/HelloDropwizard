package com.galaxy.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class GalaxyConfiguration extends Configuration {
    private ElasticsearchConfig elasticsearchConfig;
    private AwsConfig awsConfig;

    @JsonProperty("elasticsearchConfiguration")
    public ElasticsearchConfig getElasticsearchConfig() {
        return elasticsearchConfig;
    }

    public void setElasticsearchConfig(ElasticsearchConfig elasticsearchConfig) {
        this.elasticsearchConfig = elasticsearchConfig;
    }

    @JsonProperty("awsConfig")
    public AwsConfig getAwsConfig() {
        return awsConfig;
    }

    public void setAwsConfig(AwsConfig awsConfig) {
        this.awsConfig = awsConfig;
    }

}
