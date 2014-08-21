package ru.forxy.auth.rest.v1;

import ru.forxy.auth.logic.IUserManager;
import ru.forxy.auth.rest.v1.pojo.Credentials;
import ru.forxy.auth.rest.v1.pojo.User;
import ru.forxy.common.rest.AbstractService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Provides server-side authentication procedure
 */
@Path("/auth/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthServiceEndpoint extends AbstractService {

    private IUserManager userManager;

    @POST
    @Path("/login")
    public Response login(final Credentials credentials,
                          @Context final UriInfo uriInfo,
                          @Context final HttpHeaders headers) {
        return respondWith(userManager.login(credentials), uriInfo, headers).build();
    }

    @POST
    @Path("/register")
    public Response register(final Credentials credentials,
                             @Context final UriInfo uriInfo,
                             @Context final HttpHeaders headers) {
        return respondWith(userManager.createUser(new User(credentials.getEmail(), credentials.getPassword())),
                uriInfo, headers).build();
    }

    public void setUserManager(final IUserManager userManager) {
        this.userManager = userManager;
    }
}