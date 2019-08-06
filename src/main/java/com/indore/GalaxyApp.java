package com.indore;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.EnumSet;

import org.apache.http.HttpHost;
import org.eclipse.jetty.servlets.CrossOriginFilter;
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

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

public class GalaxyApp extends Application<GalaxyConfiguration> {

    public static final String USER_INDEX_NAME = "users";

    public static void main(final String[] args) throws Exception {
        new GalaxyApp().run(args);
    }

    @Override
    public String getName() {
        return "Welcome to my galaxy App";
    }

    @Override
    public void initialize(final Bootstrap<GalaxyConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final GalaxyConfiguration configuration,
                    final Environment environment) throws IOException {
        // Reference links for CORS related changes
        // http://jitterted.co.technology/tidbits/2014/04/04/handling-cors-in-dropwizard-and-jetty/
        // https://stackoverflow.com/questions/25775364/enabling-cors-in-dropwizard-not-working

        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS",CrossOriginFilter.class);
                //configuring CORS parameter
                //specifies which origin are allowed to access your resource
               cors .setInitParameter("allowedOrigins", "*");
               //specifies list of header that are allowed from client to server
                cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
                cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE");
                //Add URL mapping => set URL mapping of resources here we are setting all resources to be cross origin capable
                cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class),true,"/*");


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
