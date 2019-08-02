package com.indore.services;

import static com.indore.HelloDropwizardApplication.USER_INDEX_NAME;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.indore.api.UserSearchResult;

/**
 * User service to deal with user's documents.
 *
 * @author Amit Khandelwal
 */
public class UserService {
	private final RestHighLevelClient client;
	public UserService(RestHighLevelClient client) {
		this.client = client;
	}

	public void add(String id, JsonNode user) {
		String userStr = user.toString();
		IndexRequest indexRequest = new IndexRequest(USER_INDEX_NAME)
				.id(id)
				.source(userStr, XContentType.JSON);
		client.indexAsync(indexRequest , RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
			@Override
			public void onResponse(IndexResponse indexResponse) {
				//log.debug("Index Response for user id {} is {} ", id, indexResponse);
			}
			@Override
			public void onFailure(Exception e) {
				//log.error("Message document with id {} is failed to index and cause is {}", id, e);
			}
		} );
	}

	public String get(String id) throws IOException {
		GetRequest getRequest = new GetRequest(USER_INDEX_NAME, id);
		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		return getResponse.getSourceAsString();
	}

	public List<UserSearchResult> search(String searchTerm) throws IOException {
		SearchRequest searchRequest = new SearchRequest("users");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(searchTerm, "firstName","lastName",
				"address", "landmark", "userId");
		multiMatchQueryBuilder.operator(Operator.AND);
		searchSourceBuilder.query(multiMatchQueryBuilder);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		// TODO create a meaningful response object, in which below elasticsearch attributes can be embedded.
		RestStatus status = searchResponse.status();
		TimeValue took = searchResponse.getTook();
		Boolean terminatedEarly = searchResponse.isTerminatedEarly();
		boolean timedOut = searchResponse.isTimedOut();

		// Start fetching the documents matching the search results.
		//https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-search.html#java-rest-high-search-response-search-hits
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		List<UserSearchResult> userSearchResults = new ArrayList<>();
		for (SearchHit hit : searchHits) {
			// do something with the SearchHit
			String index = hit.getIndex();
			String id = hit.getId();
			float score = hit.getScore();
			//String sourceAsString = hit.getSourceAsString();
			Map<String, Object> sourceAsMap = hit.getSourceAsMap();
			String firstName = (String) sourceAsMap.get("firstName");
			String lastName = (String) sourceAsMap.get("lastName");
			UserSearchResult userSearchResult = new UserSearchResult(firstName, lastName, score);
			userSearchResults.add(userSearchResult);
		}

		return userSearchResults;

	}
}
