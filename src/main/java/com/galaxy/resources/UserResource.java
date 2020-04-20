package com.galaxy.resources;

import com.codahale.metrics.annotation.Timed;
import com.galaxy.api.SearchParam;
import com.galaxy.api.UserRegistration;
import com.galaxy.services.UserRegisterationService;
import com.galaxy.utils.JSONResponse;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * User resource to create an entity for indexing the document, search request, getting doc by id. .
 *
 * @author Amit Khandelwal
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private static final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserRegisterationService userRegisterationService;

    public UserResource(UserRegisterationService userRegisterationService) {
        this.userRegisterationService = userRegisterationService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public Response createUser(@Valid UserRegistration userRegistration) {
        try {
            log.debug("User registration doc is {}", userRegistration.toString());
            boolean userCreated = userRegisterationService.register(userRegistration);
            JSONResponse response = new JSONResponse();
            if (userCreated) {
                //return javax.ws.rs.core.Response.ok("user with id: " + userRegistration.getUserId() + "created successfully").build();
                response.setStatusCode(Response.Status.OK.getStatusCode());
                response.setMsg("user with id: " + userRegistration.getUserId() + "created successfully");
                return Response.ok(response).build();

            } else {
                //return javax.ws.rs.core.Response.ok("User already exist").build();
                response.setStatusCode(Response.Status.OK.getStatusCode());
                response.setMsg("User already exist");
                return Response.ok(response).build();
            }
        } catch (Exception e) {
            log.error("Error indexing doc {} and error is", userRegistration, e);
            return Response.serverError().build();
        }
    }

    /**
     * The get API allows to get a JSON document from the index based on its id.
     *
     * @param id unique id of user document. Cannot be {@code null}.
     * @return user document.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public Response getUser(@PathParam("id") @NotEmpty String id) {
        log.debug("Get request for user id {}", id);
        try {

            return Response.ok(userRegisterationService.get(id)).build();
        } catch (IOException e) {
            log.error("Error getting user doc {} and error is {}", id, e);
            return Response.serverError().build();
        }
    }

    /**
     * This API allows to search user document based on search term.
     *
     * @param searchParam Parameters that can be included in the search term.
     * @return user document corresponding to search term.
     */
    @POST
    @Path("/search")
    @Timed
    public Response searchUsers(SearchParam searchParam) {
        log.debug("Search term {}", searchParam.getSearchTerm());
        try {
            return Response.ok(userRegisterationService.search(searchParam.getSearchTerm())).build();
        } catch (IOException e) {
            log.error("Error getting search results for search term {} ", searchParam.getSearchTerm(), e);
            return Response.serverError().build();
        }
    }

    /**
     * The delete API allows to delete a JSON document from a specific index based on its id.
     *
     * @param userId unique id of user document. Cannot be {@code null}.
     * @return user document is deleted.
     */

    @DELETE
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public Response deleteUser(@PathParam("userId") String userId) {
        log.debug("Delete request for message id {}", userId);
        try {
            userRegisterationService.delete(userId);
            return Response.ok().build();
        } catch (IOException e) {
            log.error("Error deleting user document {} and error is {}", userId, e);
            return Response.serverError().build();
        }
    }

    /**
     * This API is used to list all the documents indexed in an index
     *
     * @return All the documents indexed
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public Response getAllUser() {
        log.debug("Get record of all users");
        try {
            return Response.ok(userRegisterationService.getAll()).build();
        } catch (IOException e) {
            log.error("Error getting user doc and error is {}", e);
            return Response.serverError().build();
        }
    }

    /**
     * This API allows to search user document based on search term.
     *
     * @param searchParam Parameters that can be included in the search term.
     * @return user document corresponding to search term.
     */
    @POST
    @Path("/query")
    @Timed
    public Response So60628247(SearchParam searchParam) {
        log.debug("Search term {}", searchParam.getSearchTerm());
        try {
            return Response.ok(userRegisterationService.search(searchParam.getSearchTerm())).build();
        } catch (IOException e) {
            log.error("Error getting search results for search term {} ", searchParam.getSearchTerm(), e);
            return Response.serverError().build();
        }
    }

}








