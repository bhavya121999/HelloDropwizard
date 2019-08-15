package com.indore.services;

import static com.indore.GalaxyApp.USERS_INDEX_NAME;

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
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.indore.api.UserRegistration;
import com.indore.api.UserSearchResult;

/**
 * User service to deal with user's documents in elasticsearch.
 *
 * @author Amit Khandelwal
 */
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

	private final RestHighLevelClient client;
	private final String EMAIL_ID = "emailId";
	private final String USER_ID = "userId";
	private final String MOBILE_NUMBER = "mobileNumber";

    public UserService(RestHighLevelClient client) {
        this.client = client;
    }

	/**
	 * Add user document to its index.
	 *
	 * @param userRegistration user document is JSON format. Cannot be {@code null}.
	 */
	public boolean add(UserRegistration userRegistration) throws IOException {
		if (isUserExist(userRegistration.getEmailId(), userRegistration.getUserId(), userRegistration.getMobileNumber())) {
			return false;
		}

		userRegistration.setCreatedDate(System.currentTimeMillis());
		ObjectMapper Obj = new ObjectMapper();
		final String userStr = Obj.writeValueAsString(userRegistration);
        final IndexRequest indexRequest = new IndexRequest(USERS_INDEX_NAME)
                .id(userRegistration.getUserId())
                .source(userStr, XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        log.info("Index response for userid {} is {}", userRegistration.getUserId(), indexResponse.getResult());
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
     * @param emailId      unique emailId of the user.
     * @param mobileNumber unique mobileNumber of the user.
     * @param password     unique password of the user.
     * @return if the user is authenticated or not.
     * @throws IOException
     */
    public boolean authUser(String emailId,
                            Long mobileNumber, String password) throws IOException {
        /**String email = USERS_INDEX_NAME.get("emailId").asText();

         long mobile = USERS_INDEX_NAME.get("mobileNumber").asLong();*/

        if (isAuthenticate(emailId, mobileNumber, password)) {
            return true;
        } else {
            return false;
        }

    }


    private boolean isAuthenticate(String email, Long mobile, String password) throws IOException {
        SearchRequest searchRequest = new SearchRequest(USERS_INDEX_NAME);
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.minimumShouldMatch(2);
        if (email != null) {
            MatchQueryBuilder emailMatchQueryBuilder = new MatchQueryBuilder("emailId", email);
            boolQueryBuilder.should(emailMatchQueryBuilder);
        }
        if (mobile != null) {
            MatchQueryBuilder mobileMatchQueryBuilder = new MatchQueryBuilder("mobileNumber", mobile);
            boolQueryBuilder.should(mobileMatchQueryBuilder);
        }

        MatchQueryBuilder passwordMatchQueryBuilder = new MatchQueryBuilder("password", password);
        boolQueryBuilder.should(passwordMatchQueryBuilder);


        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        log.info("Search json {} for user exist", searchSourceBuilder.toString());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        List<UserSearchResult> userSearchResults = getUserSearchResults(searchResponse);
        return (userSearchResults != null && userSearchResults.size() > 0);


    }


    /**
     * get a user document by its id from elasticsearch.
     *
     * @param id unique id of user document. Cannot be {@code null}.
     * @return user document.
     * @throws IOException
     */
    public String get(String id) throws IOException {
        if (id.isEmpty()) {
            throw new IllegalArgumentException("arguments can't be null");
        }
        GetRequest getRequest = new GetRequest(USERS_INDEX_NAME, id);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        return getResponse.getSourceAsString();
    }

    /**
     * Search for a term in user index.
     *
     * @param searchTerm search term which needs to be searched.
     * @return matching documents in user index.
     * @throws IOException
     */
    public List<UserSearchResult> search(String searchTerm) throws IOException {
        SearchRequest searchRequest = new SearchRequest(USERS_INDEX_NAME);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(searchTerm, "firstName", "lastName",
                "password", "emailId", "userId", "mobileNumber");
        multiMatchQueryBuilder.operator(Operator.AND);
        searchSourceBuilder.query(multiMatchQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        return getUserSearchResults(searchResponse);

    }

   private List<UserSearchResult> getUserSearchResults(SearchResponse searchResponse) {
        // TODO create a meaningful response object, in which below elasticsearch attributes can be embedded.
        RestStatus status = searchResponse.status();
        TimeValue took = searchResponse.getTook();
        Boolean terminatedEarly = searchResponse.isTerminatedEarly();
        boolean timedOut = searchResponse.isTimedOut();

        // Start fetching the documents matching the search results.
        //https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-search
        // .html#java-rest-high-search-response-search-hits
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
            String emailId = (String) sourceAsMap.get("emailId");
            String password = (String) sourceAsMap.get("password");
            String userId = (String) sourceAsMap.get("userId");


            UserSearchResult userSearchResult = new UserSearchResult(firstName, lastName, emailId, password, userId,
                    score);
            userSearchResults.add(userSearchResult);
        }

        return userSearchResults;
    }

    /**
     * delete a user document by its id from elasticsearch.
     *
     * @param userId unique id of user document. Cannot be {@code null}.
     * @throws IOException
     */
    public void delete(String userId) throws IOException {
        final DeleteRequest deleterequest = new DeleteRequest(USERS_INDEX_NAME, userId);
        client.deleteAsync(deleterequest, RequestOptions.DEFAULT, new ActionListener<DeleteResponse>() {
            @Override
            public void onResponse(DeleteResponse deleteResponse) {
                log.debug("Delete Response for user id {} is {} ", deleterequest.id(), deleteResponse);
            }

            @Override
            public void onFailure(Exception e) {
                log.error("User document with id {} is failed to delete and cause is {}", deleterequest.id(), e);
            }
        });
    }

// get all API
    /**public String getAll() throws IOException {
     if (USERS_INDEX_NAME.isEmpty()) {
     throw new IllegalArgumentException("arguments can't be null");
     }
     GetRequest getRequest = new GetRequest(USERS_INDEX_NAME);
     GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);

     return getResponse.getSourceAsString();
     }*/

}
