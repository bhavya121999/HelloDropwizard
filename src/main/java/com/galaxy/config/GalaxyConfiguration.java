package com.galaxy.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class GalaxyConfiguration extends Configuration {
    private ElasticsearchConfig elasticsearchConfig;
    private AwsConfig awsConfig;

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

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
