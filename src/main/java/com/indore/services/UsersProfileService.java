package com.indore.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indore.api.UserProfile;
import com.indore.api.UserRegistration;
import com.indore.api.UserSearchResult;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.indore.GalaxyApp.USERS_INDEX_NAME;
import static com.indore.GalaxyApp.USERS_PROFILE_INDEX_NAME;

public class UsersProfileService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final RestHighLevelClient client;

    public UsersProfileService(RestHighLevelClient client) {
        this.client = client;
    }

    /**
     * Add userProfile document to its index.
     *
     * @param userProfile userProfile document is JSON format. Cannot be {@code null}.
     */
    public boolean add(UserProfile userProfile) throws IOException {
        if (isUserExist(userProfile.getUserId())) {
            return false;
        }

        userProfile.setCreatedDate(System.currentTimeMillis());
        ObjectMapper Obj = new ObjectMapper();
        final String userStr = Obj.writeValueAsString(userProfile);
        final IndexRequest indexRequest = new IndexRequest(USERS_INDEX_NAME)
                .id(userProfile.getUserId())
                .source(userStr, XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        log.info("Index response for userid {} is {}", userProfile.getUserId(), indexResponse.getResult());
        return true;
    }

    private boolean isUserExist(String email, String userId, Long mobile) throws IOException {
        if(email == null | userId == null | mobile ==null)
            throw new IllegalArgumentException("email or userid or mobile can't be null");

        SearchRequest searchRequest = new SearchRequest(USERS_INDEX_NAME);
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.minimumShouldMatch(1);
        MatchQueryBuilder emailMatchQueryBuilder = new MatchQueryBuilder("emailId", email);
        MatchQueryBuilder userIdMatchQueryBuilder = new MatchQueryBuilder("userId", userId);
        MatchQueryBuilder mobileMatchQueryBuilder = new MatchQueryBuilder("mobileNumber", mobile);
        boolQueryBuilder.should(emailMatchQueryBuilder);
        boolQueryBuilder.should(userIdMatchQueryBuilder);
        boolQueryBuilder.should(mobileMatchQueryBuilder);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        log.info("Search json {} for user exist", searchSourceBuilder.toString());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        List<UserSearchResult> userSearchResults = getUserSearchResults(searchResponse);
        return (userSearchResults != null && userSearchResults.size() > 0);


    }
    /**
     * get a userProfile document by its id from elasticsearch.
     *
     * @param id unique id of user document. Cannot be {@code null}.
     * @return profile of the user.
     * @throws IOException
     */

    public String get(String id) throws IOException {
        if (id.isEmpty()) {
            throw new IllegalArgumentException("arguments can't be null");
        }
        GetRequest getRequest = new GetRequest(USERS_PROFILE_INDEX_NAME, id);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        return getResponse.getSourceAsString();
    }
}




