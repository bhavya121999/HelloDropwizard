package com.indore.resources;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.JsonNode;

import com.indore.api.SearchParameter;
import com.indore.services.UserService;

/**
 * <User resource to create an entity for indexing the document, search request, getting doc by id. >.
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


	@POST
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Timed
	public Response createUser(@PathParam("id") String id, @NotNull JsonNode user){
		try {
			log.debug("User id {} doc is {}", id, user);
			userService.add(id,user);
			URI uri = new URI(id);
			return Response.created(uri).build();
		}catch (Exception e){
			log.error("Error indexing doc {} and error is", user, e);
			return Response.serverError().build();
		}
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Timed
	public Response getUser(@PathParam("id") String id){
		log.debug("Get request for message id {}", id);
		try {
			return Response.ok(userService.get(id)).build();
		} catch (IOException e) {
			log.error("Error getting message doc {} and error is {}", id, e);
			return Response.serverError().build();
		}
	}

	@POST
	@Path("/search")
	@Timed
	public Response searchUsers(@NotNull SearchParameter searchParameter){
		log.debug("Search term {}", searchParameter.getSearchTerm());
		try {
			return Response.ok(userService.search(searchParameter.getSearchTerm())).build();
		} catch (IOException e) {
			log.error("Error getting search results for search term {} ", searchParameter.getSearchTerm(), e);
			return Response.serverError().build();
		}
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Timed
	public Response deleteUser(@PathParam("id") String id){
		log.debug("Delete request for message id {}", id);
		try {
			userService.delete(id);
			URI uri = new URI(id);
			return Response.created(uri).build();
		} catch (IOException | URISyntaxException e) {
			log.error("Error getting message doc {} and error is {}", id, e);
			return Response.serverError().build();
		}
	}


}
