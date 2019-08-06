package com.indore.resources;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
 * <File Description>.
 *
 * @author Amit Khandelwal
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
	private static final Logger log = LoggerFactory.getLogger(UserResource.class);

	private final UserService userService;

	public UserResource(UserService userService) {
		this.userService = userService;
	}

	@POST
	@Path("{id}")
	@Timed
	public Response createUser(@PathParam("id") @NotEmpty String id, JsonNode user){
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
	public Response getUser(@PathParam("id") @NotEmpty String id){
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
	public Response searchUsers(@NotEmpty SearchParameter searchParameter){
		log.debug("Search term {}", searchParameter.getSearchTerm());
		try {
			return Response.ok(userService.search(searchParameter.getSearchTerm())).build();
		} catch (IOException e) {
			log.error("Error getting search results for search term {} ", searchParameter.getSearchTerm(), e);
			return Response.serverError().build();
		}
	}
}
