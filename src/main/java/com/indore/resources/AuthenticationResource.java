package com.indore.resources;

/**
 * <File Description>.
 *
 * @author Amit Khandelwal
 */

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indore.api.Authentication;
import com.indore.services.UserRegisterationService;
import com.indore.utils.JSONResponse;

/**
 * Auth resource.
 *
 * @author Amit Khandelwal
 */
@Path("/auth")
public class AuthenticationResource {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationResource.class);

	private final UserRegisterationService userRegisterationService;

	public AuthenticationResource(UserRegisterationService userRegisterationService) {
		this.userRegisterationService = userRegisterationService;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authUser(Authentication authentication) throws IOException {
		JSONResponse response = new JSONResponse();
		log.info("Login request for user: {}", authentication.toString());
		try {
			if (userRegisterationService.authUser(authentication.getEmailId(), authentication.getMobileNumber(),
					authentication.getPassword())) {
				response.setStatusCode(Response.Status.OK.getStatusCode());
				return Response.ok(response).build();
			} else {
				response.setStatusCode(Response.Status.NOT_FOUND.getStatusCode());
				return Response.ok(response).build();
				//TODO: Need to correct this later so as to show this user is not present.
			}

		} catch (IOException e) {
			response.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
			return Response.ok(response).build();
			//log.error("User does not exist and error is {}", e);
			//return Response.serverError().build();
		}
	}
}