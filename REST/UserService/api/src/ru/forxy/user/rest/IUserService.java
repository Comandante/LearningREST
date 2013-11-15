package ru.forxy.user.rest;

import ru.forxy.user.rest.pojo.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/users/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IUserService {

    @GET
    @Path("/{page}/")
    Response getUsers(@PathParam("page") Integer page,
                      @Context final UriInfo uriInfo,
                      @Context final HttpHeaders headers);

    @GET
    @Path("/{page}/{size}/")
    Response getUsers(@PathParam("page") Integer page,
                      @PathParam("size") Integer size,
                      @Context final UriInfo uriInfo,
                      @Context final HttpHeaders headers);

    @GET
    Response getUser(@QueryParam("") User requestedUser,
                     @Context final UriInfo uriInfo,
                     @Context final HttpHeaders headers);

    @POST
    @Path("/login")
    Response login(User loginUser,
                   @Context final UriInfo uriInfo,
                   @Context final HttpHeaders headers);

    @POST
    Response updateUser(User user,
                        @Context final UriInfo uriInfo,
                        @Context final HttpHeaders headers);

    @PUT
    Response createUser(User user,
                        @Context final UriInfo uriInfo,
                        @Context final HttpHeaders headers);

    @DELETE
    Response deleteUser(@QueryParam("email") String email,
                        @Context final UriInfo uriInfo,
                        @Context final HttpHeaders headers);
}