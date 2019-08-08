package com.indore.services;

import static com.indore.GalaxyApp.USER_INDEX_NAME;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
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
 * User service to deal with user's documents in elasticsearch.
 *
 * @author Amit Khandelwal
 */
public class UserService {
	private final RestHighLevelClient client;
	public UserService(RestHighLevelClient client) {
		this.client = client;
	}

	/**
	 * Add user document to its index.
	 * @param id id of user document. Cannot be {@code null}.
	 * @param user user document is JSON format. Cannot be {@code null}.
	 */
	public void add(String id, JsonNode user) {
		if (id == null || user == null) {
			throw new IllegalArgumentException("arguments can't be null");
		}

		String userStr = user.toString();
		IndexRequest indexRequest = new IndexRequest(USER_INDEX_NAME)
				.id(id)
				.source(userStr, XContentType.JSON);
		client.indexAsync(indexRequest, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
			@Override
			public void onResponse(IndexResponse indexResponse) {
				//log.debug("Index Response for user id {} is {} ", id, indexResponse);
			}

			@Override
			public void onFailure(Exception e) {
				//log.error("Message document with id {} is failed to index and cause is {}", id, e);
			}
		});
	}

	/**
	 * get a user document by its id from elasticsearch.
	 * @param id unique id of user document. Cannot be {@code null}.
	 * @return user document.
	 * @throws IOException
	 */
	public String get(String id) throws IOException {
		if (id.isEmpty()) {
			throw new IllegalArgumentException("arguments can't be null");
		}
		GetRequest getRequest = new GetRequest(USER_INDEX_NAME, id);
		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		return getResponse.getSourceAsString();
	}

	/**
	 * Search for a term in user index.
	 * @param searchTerm search term which needs to be searched.
	 * @return matching documents in user index.
	 * @throws IOException
	 */
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
	public void delete(String id) throws IOException{
		DeleteRequest deleterequest = new DeleteRequest(USER_INDEX_NAME, id);
		//ActionListener<DeleteResponse> listener = new ActionListener<DeleteResponse>();
		client.deleteAsync(deleterequest, RequestOptions.DEFAULT, new ActionListener<DeleteResponse>(){
			@Override
			public void onResponse(DeleteResponse deleteResponse) {
				//log.debug("Delete Response for user id {} is {} ", id, deleteResponse);

			}

			@Override
			public void onFailure(Exception e) {
				//log.error("Message document with id {} is failed to delete and cause is {}", id, e);
			}
		});





	}
}
