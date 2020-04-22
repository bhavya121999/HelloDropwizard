package com.galaxy.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxy.api.UserProfile;
import com.galaxy.client.ElasticsearchClient;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.galaxy.GalaxyApp.USERS_PROFILE_INDEX_NAME;

public class NestedService {
    private static final Logger log = LoggerFactory.getLogger(NestedService.class);

    private final ElasticsearchClient esClient;

    public NestedService(ElasticsearchClient esClient) {
        this.esClient = esClient;
    }

    /**
     * Add userProfile document to its index.
     *
     * @param userProfile userProfile document is JSON format. Cannot be {@code null}.
     */
    public boolean add(UserProfile userProfile) throws IOException {
        /*if (!isUserIdExist(userProfile.getUserId())) {
            return false;
        }*/

        // TODO:- Need to handle creation date and other mutable properties in user profile builder.
        ObjectMapper Obj = new ObjectMapper();
        final String userStr = Obj.writeValueAsString(userProfile);
        final IndexRequest indexRequest = new IndexRequest(USERS_PROFILE_INDEX_NAME)
                .id(userProfile.getUserId())
                .source(userStr, XContentType.JSON);
        IndexResponse indexResponse = esClient.index(indexRequest, RequestOptions.DEFAULT);
        log.info("Index response for userid {} is {}", userProfile.getUserId(), indexResponse.getResult());
        return true;
    }
}
