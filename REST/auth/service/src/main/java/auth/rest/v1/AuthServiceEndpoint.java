package auth.rest.v1;

import auth.logic.IAuthenticationManager;
import auth.rest.v1.pojo.Credentials;
import auth.rest.v1.pojo.DiscoveryInfo;
import common.rest.AbstractService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
public class AuthServiceEndpoint extends AbstractService {

    private IAuthenticationManager authenticationManager;

    private DiscoveryInfo discoveryInfo;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("email") String email,
                          @FormParam("password") String password,
                          @Context final UriInfo uriInfo,
                          @Context final HttpHeaders headers) {
        return respondWith(authenticationManager.login(new Credentials(email, password)), uriInfo, headers).build();
    }

    /*@POST
    @Path("/register")
    public Response register(final Credentials credentials,
                             @Context final UriInfo uriInfo,
                             @Context final HttpHeaders headers) {
        return respondWith(authenticationManager.createUser(new User(credentials.getEmail(), credentials.getPassword())),
                uriInfo, headers).build();
    }*/

    @GET
    @RolesAllowed("profile")
    @Path("/profile/{email}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response profile(@PathParam("email") final String email,
                            @Context final UriInfo uriInfo,
                            @Context final HttpHeaders headers) {
        return respondWith(authenticationManager.getProfile(email), uriInfo, headers).build();
    }

    @GET
    @Path("/certs")
    public Response jwks(@Context final UriInfo uriInfo,
                         @Context final HttpHeaders headers) {
        return respondWith(null, uriInfo, headers).build();
    }

    @GET
    @Path("/.well-known/openid-configuration")
    public Response configuration(final Credentials credentials,
                                  @Context final UriInfo uriInfo,
                                  @Context final HttpHeaders headers) {
        return respondWith(discoveryInfo, uriInfo, headers).build();
    }

    public void setAuthenticationManager(final IAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setDiscoveryInfo(DiscoveryInfo discoveryInfo) {
        this.discoveryInfo = discoveryInfo;
    }
}
