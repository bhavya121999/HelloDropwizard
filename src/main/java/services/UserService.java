package services;

import static com.javaeeee.HelloDropwizardApplication.USER_INDEX_NAME;

import java.io.IOException;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import com.fasterxml.jackson.databind.JsonNode;

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
}
