package com.galaxy;

import com.galaxy.client.ElasticsearchClient;
import com.galaxy.client.S3Client;
import com.galaxy.config.GalaxyConfiguration;
import com.galaxy.resources.*;
import com.galaxy.services.ImageService;
import com.galaxy.services.UserRegisterationService;
import com.galaxy.services.UsersProfileService;
import com.galaxy.utils.JsonUtil;
import com.google.common.collect.Sets;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.http.HttpHost;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.Set;

public class GalaxyApp extends Application<GalaxyConfiguration> {
    private static final Logger log = LoggerFactory.getLogger(GalaxyApp.class);

    public static final String USERS_INDEX_NAME = "users";
    public static final String USERS_PROFILE_INDEX_NAME = "usersprofile";

    public static final Set<String> INDICES = Sets.newHashSet("nestedimage");

    public static void main(final String[] args) throws Exception {
        new GalaxyApp().run(args);
    }

    @Override
    public String getName() {
        return "Welcome to my galaxy App";
    }

    @Override
    public void initialize(final Bootstrap<GalaxyConfiguration> bootstrap) {
        log.info("Galaxy app initializing...........");

    }

    @Override
    public void run(final GalaxyConfiguration configuration,
                    final Environment environment) throws IOException, URISyntaxException {

        addCors(environment);

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost(configuration.getElasticsearchConfig().getHost(),
                        configuration.getElasticsearchConfig().getPort(),
                        "http")));
        // TODO :- need to move creating indices logic to a utility.
        createIndex(restHighLevelClient);

        // Clients
        ElasticsearchClient elasticsearchClient = new ElasticsearchClient(configuration.getElasticsearchConfig());
        S3Client s3Client = new S3Client(configuration.getAwsConfig());


        // Services. Add any new service which you add
        UserRegisterationService userRegisterationService = new UserRegisterationService(elasticsearchClient);
        UsersProfileService userProfileService = new UsersProfileService(elasticsearchClient);
        ImageService imageService = new ImageService(s3Client);


        // URL mapping, Add any new resource which you add
        environment.jersey().register(new UserResource(userRegisterationService));
        environment.jersey().register(new UsersProfileResource(userProfileService));
        environment.jersey().register(new ImageResource(imageService));
        environment.jersey().register(new AuthenticationResource(userRegisterationService));

        environment.jersey().register(MultiPartFeature.class);

        // SO mapping
        environment.jersey().register(new EmployeeResource(restHighLevelClient));
        environment.jersey().register(new NestedResource(restHighLevelClient));

        log.info("Galaxy app started successfully.....");
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
                JsonUtil jsonUtil = new JsonUtil();
                String indexString = jsonUtil.getStringFromFile(indexName + ".mapping");
                CreateIndexRequest request = new CreateIndexRequest(indexName);
                request.source(indexString, XContentType.JSON);
                client.indices().create(request, RequestOptions.DEFAULT);
            }
        }
    }

    /**
     * check index exists
     *
     * @param indexName Index name which needs to be checked
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
