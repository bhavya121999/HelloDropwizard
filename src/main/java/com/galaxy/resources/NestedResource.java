package com.galaxy.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxy.api.NestedImages;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/nestedimages")
@Produces(MediaType.APPLICATION_JSON)
public class NestedResource {
    private final RestHighLevelClient client;

    public NestedResource(RestHighLevelClient client) {
        this.client = client;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public String saveToEs(NestedImages nestedImages) throws IOException {
        ObjectMapper Obj = new ObjectMapper();
        final String images = Obj.writeValueAsString(nestedImages);
        final IndexRequest indexRequest = new IndexRequest("nestedimage")
                .source(images, XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        //log.info("Index response for userid {} is {}", userProfile.getUserId(), indexResponse.getResult());
        return "";
    }
}
