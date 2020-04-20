package com.galaxy.resources;


import com.codahale.metrics.annotation.Timed;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    private final RestHighLevelClient client;

    public EmployeeResource(RestHighLevelClient client) {
        this.client = client;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public String saveToEs() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("employee");
        Map<String, Object> name = new HashMap<>();
        name.put("type", "text");
        Map<String, Object> address = new HashMap<>();
        address.put("type", "text");
        // address.put("index", false);

        Map<String, Object> properties = new HashMap<>();
        properties.put("name", name);
        properties.put("address", address);
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);
        request.mapping(mapping);
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.index().toString());
        return "";
    }

    @POST
    @Path("/search")
    @Timed
    public SearchResponse searchEmployeePrefix() throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder qb = QueryBuilders.boolQuery();

        PrefixQueryBuilder namePQBuilder = QueryBuilders.prefixQuery("address", "usa");
        PrefixQueryBuilder addressPQBuilder = QueryBuilders.prefixQuery("address", "usa");
        qb.should(namePQBuilder);
        qb.should(addressPQBuilder);
        sourceBuilder.query(qb);

        SearchRequest searchRequest = new SearchRequest("employee").source(sourceBuilder);
        System.out.println("Search JSON query \n" + searchRequest.source().toString());
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("Search JSON query \n" + searchRequest.source().toString());
        return searchResponse;
    }
}
