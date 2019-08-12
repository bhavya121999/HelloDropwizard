package com.indore.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.JsonNode;
import com.indore.services.UsersProfileService;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


/**
 * <File Description>.
 *
 *
 */
@Path("/usersprofile")
@Produces(MediaType.APPLICATION_JSON)
public class UsersProfileResource {
    private static final Logger log = LoggerFactory.getLogger(UsersProfileResource.class);

    private final UsersProfileService usersProfileService;

    public UsersProfileResource(UsersProfileService usersProfileService) {
        this.usersProfileService = usersProfileService;

}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public Response createUserProfile(JsonNode userProfile) {

        try {
            log.debug("User doc is {}", userProfile);
            usersProfileService.add(userProfile);
            return Response.ok().build();
        } catch (Exception e) {
            log.error("Error indexing profile doc {} and error is", userProfile, e);
            return Response.serverError().build();
        }

    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public Response getUser(@PathParam("id") @NotEmpty String id) {
        log.debug("Get request for user id {}", id);
        try {
            return Response.ok(usersProfileService.get(id)).build();
        } catch (IOException e) {
            log.error("Error getting userProfile doc {} and error is {}", id, e);
            return Response.serverError().build();
        }
    }
}
