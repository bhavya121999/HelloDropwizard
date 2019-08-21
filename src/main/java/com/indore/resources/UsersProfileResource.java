package com.indore.resources;

import java.io.IOException;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.indore.utils.JSONResponse;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.indore.api.UserProfile;
import com.indore.services.UsersProfileService;


/**
 * Users Profile resource to create an entity for indexing the document and getting doc by id. .
 */
@Path("/usersprofile")
@Produces(MediaType.APPLICATION_JSON)
public class UsersProfileResource {
    private static final Logger log = LoggerFactory.getLogger(UsersProfileResource.class);

    private final UsersProfileService usersProfileService;

    public UsersProfileResource(UsersProfileService usersProfileService) {
        this.usersProfileService = usersProfileService;

    }

    /**
     * The index API adds or updates a JSON document in a specific index, making it searchable.
     *
     * @param userProfile users profile document is in JSON format. Cannot be {@code null}.
     * @return users profile is indexed.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public Response createUserProfile(@Valid  UserProfile userProfile) {
        JSONResponse response = new JSONResponse();
        try {
            log.debug("User profile doc is {}", userProfile);
            usersProfileService.add(userProfile);
            response.setStatusCode(Response.Status.OK.getStatusCode());
            return Response.ok(response).build();
        } catch (Exception e) {
            log.error("Error indexing profile doc {} and error is", userProfile, e);
            response.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            return Response.ok(response).build();
        }

    }

    /**
     * The get API allows to get a JSON document of users profile from the index based on its id.
     *
     * @param id unique id of users profile document. Cannot be {@code null}.
     * @return users profile document.
     */
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
