package com.indore;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.apache.http.HttpHost;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.indore.resources.ImageResource;
import com.indore.resources.UserResource;
import com.indore.resources.UsersProfileResource;
import com.indore.services.UserService;
import com.indore.services.UsersProfileService;
import com.indore.utils.JsonUtil;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class GalaxyApp extends Application<GalaxyConfiguration> {

    public static final String USERS_INDEX_NAME = "users";
    public static final String USERS_PROFILE_INDEX_NAME = "usersprofile";

    public static final Set<String> INDICES = Sets.newHashSet(USERS_INDEX_NAME, USERS_PROFILE_INDEX_NAME);

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
                    final Environment environment) throws IOException, URISyntaxException {

        addCors(environment);

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost(configuration.getElasticsearchConfig().getHost(),
                        configuration.getElasticsearchConfig().getPort(),
                        "http")));

        createIndex(restHighLevelClient);
        UserService userService = new UserService(restHighLevelClient);
        UsersProfileService userProfileService = new UsersProfileService(restHighLevelClient);
        ImageResource imageResourceService = new ImageResource(configuration.getAwsConfig().getAccesskey(),
                configuration.getAwsConfig().getSecretaccesskey(),configuration.getAwsConfig().getBucketname(),
                configuration.getAwsConfig().getClientregion());

        // URL mapping
        environment.jersey().register(new UserResource(userService));
        environment.jersey().register(new UsersProfileResource(userProfileService));
        environment.jersey().register(new ImageResource(configuration.getAwsConfig().getAccesskey(), configuration.getAwsConfig().getSecretaccesskey(),
                configuration.getAwsConfig().getBucketname(),configuration.getAwsConfig().getClientregion() ));
        environment.jersey().register(MultiPartFeature.class);
    }


    private void addCors(Environment environment) {
        // Reference links for CORS related changes
        // http://jitterted.co.technology/tidbits/2014/04/04/handling-cors-in-dropwizard-and-jetty/
        // https://stackoverflow.com/questions/25775364/enabling-cors-in-dropwizard-not-working

        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        //configuring CORS parameter
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE");
        //Add URL mapping => set URL mapping of resources here we are setting all resources to be cross origin capable
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

    private void createIndex(RestHighLevelClient client) throws IOException, URISyntaxException {
        for (String indexName : INDICES) {
            if (!isIndexExist(client, indexName)) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonUtil jsonUtil = new JsonUtil();
                String indexString = jsonUtil.getJson(indexName + ".mapping");
                //String indexString = objectMapper.writeValueAsString(indexJson);
                CreateIndexRequest request = new CreateIndexRequest(indexName);
                request.source(indexString, XContentType.JSON);
                client.indices().create(request, RequestOptions.DEFAULT);
            }
        }
    }

    /**
     * Get a index for a community
     * @param indexName community name for which index needs to be checked
     * @return response object
     */
    private boolean isIndexExist(RestHighLevelClient client, String indexName) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexName);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        if (exists) {
            //log.error("Index name {} doesn't exist", indexName);
            return true;
        }
        return false;
    }


}
