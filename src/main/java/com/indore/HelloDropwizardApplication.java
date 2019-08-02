package com.indore;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indore.resources.UserResource;
import com.indore.services.UserService;
import com.indore.utils.JsonUtil;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class HelloDropwizardApplication extends Application<HelloDropwizardConfiguration> {

    public static final String USER_INDEX_NAME = "users";

    public static void main(final String[] args) throws Exception {
        new HelloDropwizardApplication().run(args);
    }

    @Override
    public String getName() {
        return "HelloDropwizard";
    }

    @Override
    public void initialize(final Bootstrap<HelloDropwizardConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final HelloDropwizardConfiguration configuration,
                    final Environment environment) throws IOException {
       RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
               RestClient.builder(new HttpHost(configuration.getElasticsearchConfig().getHost(),
                       configuration.getElasticsearchConfig().getPort(),
                       "http")));

       createIndex(restHighLevelClient);
        UserService userService = new UserService(restHighLevelClient);

        // URL mapping
        environment.jersey().register(new UserResource(userService));
    }

    private void createIndex(RestHighLevelClient client) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonUtil jsonUtil = new JsonUtil();
        JsonNode indexJson = jsonUtil.getJson("users.mapping");
        String indexString = objectMapper.writeValueAsString(indexJson);
        CreateIndexRequest request = new CreateIndexRequest(USER_INDEX_NAME);
        request.source(indexString, XContentType.JSON);
        try {
            client.indices().create(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            //log.error("Error creating Index {}", INDEX_NAME, e);
            e.printStackTrace();
        }

    }


}
