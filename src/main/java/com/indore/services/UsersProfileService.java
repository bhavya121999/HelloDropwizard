package com.indore.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.indore.GalaxyApp.USERS_INDEX_NAME;
import static com.indore.GalaxyApp.USERS_PROFILE_INDEX_NAME ;

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
    public void add(JsonNode userProfile) throws IOException {
        String userId = userProfile.get("userId").asText();
        String address = userProfile.get("address").asText();
        String currentCity = userProfile.get("currentCity").asText();
        String homeTown= userProfile.get("homeTown").asText();
        String landmark = userProfile.get("landmark").asText();
        String pincode = userProfile.get("pincode").asText();
        String education = userProfile.get("education").asText();
        String highSchool = userProfile.get("highSchool").asText();
        String college = userProfile.get("college").asText();
        String socialLink = userProfile.get("socialLink").asText();
        String language = userProfile.get("language").asText();
        String aboutYou = userProfile.get("aboutYou").asText();
        String otherNames = userProfile.get("otherNames").asText();
        String hobbies = userProfile.get("hobbies").asText();
        String professionalSkills = userProfile.get("professionalSkills").asText();
        String musicArtist = userProfile.get("musicArtist").asText();
        String bookAuthor = userProfile.get("bookAuthor").asText();
        String programmes = userProfile.get("programmes").asText();
        String sportsTeam = userProfile.get("sportsTeam").asText();
        String sportsPeople = userProfile.get("sportsPeople").asText();
        String favouriteQuotes = userProfile.get("favouriteQuotes").asText();
        String lifeEvents= userProfile.get("lifeEvents").asText();
        String userStr = userProfile.toString();
        final IndexRequest indexRequest = new IndexRequest(USERS_PROFILE_INDEX_NAME )
                .id(userId)
                .source(userStr, XContentType.JSON);
        client.indexAsync(indexRequest, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                log.debug("IndexProfile Response for user id {} is {} ", indexRequest.id(), indexResponse);
            }

            @Override
            public void onFailure(Exception e) {
                log.error("User document with id {} is failed to index and cause is {}", indexRequest.id(), e);
            }
        });

    }

    public String get(String id) throws IOException {
        if (id.isEmpty()) {
            throw new IllegalArgumentException("arguments can't be null");
        }
        GetRequest getRequest = new GetRequest(USERS_PROFILE_INDEX_NAME, id);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        return getResponse.getSourceAsString();
    }
}




