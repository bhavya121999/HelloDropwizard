package com.indore.resources;

import java.io.IOException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.JsonNode;
import com.indore.api.SearchParameter;
import com.indore.services.UserService;

/**
 * User resource to create an entity for indexing the document, search request, getting doc by id. .
 *
 * @author Amit Khandelwal
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private static final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * The index API adds or updates a JSON document in a specific index, making it searchable.
     *
     * @param user user document is JSON format. Cannot be {@code null}.
     * @return user document is indexed.
     */


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public Response createUser(JsonNode user) {
        try {
            log.debug("User doc is {}", user);
            userService.add(user);
            return Response.ok().build();
        } catch (Exception e) {
            log.error("Error indexing doc {} and error is", user, e);
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
            return Response.ok(userService.get(id)).build();
        } catch (IOException e) {
            log.error("Error getting user doc {} and error is {}", id, e);
            return Response.serverError().build();
        }
    }

    /**
     * This API allows to search user document based on search term.
     *
     * @param searchParameter Parameters that can be included in the search term.
     * @return user document corresponding to search term.
     */

    @POST
    @Path("/search")
    @Timed
    public Response searchUsers(SearchParameter searchParameter) {
        log.debug("Search term {}", searchParameter.getSearchTerm());
        try {
            return Response.ok(userService.search(searchParameter.getSearchTerm())).build();
        } catch (IOException e) {
            log.error("Error getting search results for search term {} ", searchParameter.getSearchTerm(), e);
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
            userService.delete(userId);
            return Response.ok().build();
        } catch (IOException e) {
            log.error("Error deleting user document {} and error is {}", userId, e);
            return Response.serverError().build();
        }
    }

    /**
     * This API is used to determine the authentication of the user based on emailId,mobileNumber and password.
     *
     * @param password     unique password of user document.Cannot be {@code null}.
     * @param emailId      unique emailId of user document.
     * @param mobileNumber unique mobileNumber of user document.
     * @return user document
     * @throws IOException
     */
    @GET
    @Path("/auth/password/{password}")
    public Response authUser(@PathParam("password") String password, @QueryParam("emailId") String emailId,
                             @QueryParam("mobileNumber") Long mobileNumber) throws IOException {
        String testeMAIL = emailId;
        Long mbl = mobileNumber;
        String pass = password;
        log.debug("Authenticate request for user");
        try {
            if (userService.authUser(emailId, mobileNumber, password)) {
                return Response.ok().build();
            } else {
                return Response.noContent().build();
                //TODO: Need to correct this later so as to show this user is not present.
            }

        } catch (IOException e) {
            log.error("User does not exist and error is {}", e);
            return Response.serverError().build();
        }

    }

    /** public Response getAllUser(){
     log.debug("Get record of all users");
     try {
     return Response.ok(userService.getAll()).build();
     } catch (IOException e) {
     log.error("Error getting user doc and error is {}", e);
     return Response.serverError().build();
     }

     }*/

}








