package com.galaxy.services;

import static com.galaxy.GalaxyApp.USERS_INDEX_NAME;
import static com.galaxy.GalaxyApp.USERS_PROFILE_INDEX_NAME;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxy.api.UserProfile;
import com.galaxy.api.UserSearchResult;
import com.galaxy.client.ElasticsearchClient;

public class UsersProfileService {
	private static final Logger log = LoggerFactory.getLogger(UserRegisterationService.class);

	private final ElasticsearchClient esClient;

	public UsersProfileService(ElasticsearchClient esClient) {
		this.esClient = esClient;
	}

	/**
	 * Add userProfile document to its index.
	 *
	 * @param userProfile userProfile document is JSON format. Cannot be {@code null}.
	 */
	public boolean add(UserProfile userProfile) throws IOException {
		if (!isUserIdExist(userProfile.getUserId())) {
			return false;
		}

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

	private boolean isUserIdExist(String userId) throws IOException {
		if (userId == null)
			throw new IllegalArgumentException("Userid can't be null");

		SearchRequest searchRequest = new SearchRequest(USERS_INDEX_NAME);
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		boolQueryBuilder.minimumShouldMatch(1);
		MatchQueryBuilder userIdMatchQueryBuilder = new MatchQueryBuilder("userId", userId);
		boolQueryBuilder.should(userIdMatchQueryBuilder);

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(boolQueryBuilder);
		log.info("Search json {} for user exist", searchSourceBuilder.toString());
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
		List<UserSearchResult> userSearchResults = getUserSearchResults(searchResponse);
		return (userSearchResults != null && userSearchResults.size() > 0);
	}

	private List<UserSearchResult> getUserSearchResults(SearchResponse searchResponse) {
		// TODO create a meaningful response object, in which below elasticsearch attributes can be embedded.
		// TODO: - check exist query instead of search query as we don't need score here.
		RestStatus status = searchResponse.status();
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		List<UserSearchResult> userSearchResults = new ArrayList<>();
		for (SearchHit hit : searchHits) {
			// do something with the SearchHit
			float score = hit.getScore();

			Map<String, Object> sourceAsMap = hit.getSourceAsMap();
			String userId = (String) sourceAsMap.get("userId");

			UserSearchResult userSearchResult = new UserSearchResult.Builder(0.0f, userId, null, null).build();
			userSearchResults.add(userSearchResult);
		}

		return userSearchResults;
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
		GetResponse getResponse = esClient.get(getRequest, RequestOptions.DEFAULT);
		return getResponse.getSourceAsString();
	}
}





