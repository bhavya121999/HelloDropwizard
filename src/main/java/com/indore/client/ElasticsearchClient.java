package com.indore.client;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indore.config.ElasticsearchConfig;

/**
 * Exit point to all ES calls in app.
 *
 * @author Amit Khandelwal
 */
public class ElasticsearchClient {
	private static final Logger log = LoggerFactory.getLogger(ElasticsearchClient.class);
	private final RestHighLevelClient client;

	public ElasticsearchClient(ElasticsearchConfig elasticsearchConfig) {
		client = new RestHighLevelClient(RestClient.builder(new HttpHost(elasticsearchConfig.getHost(),
				elasticsearchConfig.getPort(), "http")));
	}

	/**
	 * Retrieves a document by id using the Get API.
	 * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html">Get API on elastic.co</a>
	 * @param getRequest the request
	 * @param requestOptions the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
	 * @return the response
	 */
	public GetResponse get(GetRequest getRequest, RequestOptions requestOptions) throws IOException {
		return client.get(getRequest, requestOptions);
	}

	/**
	 * Index a document using the Index API.
	 * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html">Index API on elastic.co</a>
	 * @param indexRequest the request
	 * @param requestOptions the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
	 * @return the response
	 */
	public IndexResponse index(IndexRequest indexRequest, RequestOptions requestOptions) throws IOException {
		IndexResponse indexResponse = client.index(indexRequest, requestOptions);
		log.info("Index response for request {} is {}", indexRequest, indexResponse.getResult());
		return indexResponse;
	}

	/**
	 * Executes a search request using the Search API.
	 * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-search.html">Search API on elastic.co</a>
	 * @param searchRequest the request
	 * @param requestOptions the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
	 * @return the response
	 */
	public SearchResponse search(SearchRequest searchRequest, RequestOptions requestOptions) throws IOException {
		return client.search(searchRequest, requestOptions);
	}

	public DeleteResponse delete(DeleteRequest deleteRequest, RequestOptions requestOptions) throws IOException {
		DeleteResponse deleteResponse = client.delete(deleteRequest, requestOptions);
		log.info("Index response for request {} is {}", deleteRequest, deleteResponse.getResult());
		return deleteResponse;
	}

}
