package com.javaeeee;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.File;
import java.io.IOException;

public class HelloDropwizardApplication extends Application<HelloDropwizardConfiguration> {

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
                    final Environment environment) {
        // TODO: implement application


    }

    private JsonNode getJson(String fileName) {
        JsonFactory jsonFactory = new JsonFactory();
        File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
        try {
            JsonParser jp = jsonFactory.createParser(file);
            jp.setCodec(new ObjectMapper());
            return jp.readValueAsTree();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public <CreateIndexRequest> void createIndex(String communityName, JsonNode settings) throws IOException {
        for (String indexName : INDEX_NAMES) {
            JsonNode indexJson = jsonUtil.getJson(indexName + MAPPING_SUFFIX);
            String indexString = objectMapper.writeValueAsString(indexJson);
            CreateIndexRequest request = new CreateIndexRequest(communityName.toLowerCase());
            request.source(indexString, XContentType.JSON);
            try {
                client.indices().create(request, RequestOptions.DEFAULT);
            } catch (IOException e) {
                log.error("Error creating Index {}", indexName, e);
                throw new IOException(e);
            }
        }
    }


}
