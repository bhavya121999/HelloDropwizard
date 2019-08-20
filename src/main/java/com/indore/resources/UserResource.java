package com.indore.resources;

import java.io.IOException;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.indore.api.SearchParameter;
import com.indore.api.UserRegistration;
import com.indore.services.UserRegisterationService;

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
			if (userCreated) {
				return Response.ok("user with id: " + userRegistration.getUserId()  +  " created successfully").build();
			} else {
				return Response.ok("User already exist").build();
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
	 * @param searchParameter Parameters that can be included in the search term.
	 * @return user document corresponding to search term.
	 */

	@POST
	@Path("/search")
	@Timed
	public Response searchUsers(SearchParameter searchParameter) {
		log.debug("Search term {}", searchParameter.getSearchTerm());
		try {
			return Response.ok(userRegisterationService.search(searchParameter.getSearchTerm())).build();
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
			userRegisterationService.delete(userId);
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
			if (userRegisterationService.authUser(emailId, mobileNumber, password)) {
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

}








