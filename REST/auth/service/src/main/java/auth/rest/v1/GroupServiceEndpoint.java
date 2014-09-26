package auth.rest.v1;

import auth.logic.IGroupManager;
import auth.rest.v1.pojo.Group;
import common.pojo.SortDirection;
import common.pojo.StatusEntity;
import common.rest.AbstractService;

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

@Path("/groups/")
@Produces(MediaType.APPLICATION_JSON)
public class GroupServiceEndpoint extends AbstractService {

    private IGroupManager groupManager;

    @GET
    public Response getGroups(@QueryParam("page") final Integer page,
                               @QueryParam("size") final Integer size,
                               @QueryParam("sort_dir") final SortDirection sortDirection,
                               @QueryParam("sorted_by") final String sortedBy,
                               @QueryParam("code") final String idFilter,
                               @QueryParam("name") final String nameFilter,
                               @QueryParam("updated_by") final String updatedByFilter,
                               @QueryParam("created_by") final String createdByFilter,
                               @Context final UriInfo uriInfo,
                               @Context final HttpHeaders headers) {
        return respondWith(page == null && size == null ?
                        groupManager.getAllGroups() :
                        groupManager.getGroups(page, size, sortDirection, sortedBy,
                                new Group(idFilter, nameFilter, updatedByFilter, createdByFilter)),
                uriInfo, headers).build();
    }

    @GET
    @Path("/{code}/")
    public Response getGroup(@PathParam("code") final String code,
                              @Context final UriInfo uriInfo,
                              @Context final HttpHeaders headers) {
        return respondWith(groupManager.getGroup(code), uriInfo, headers).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerGroup(final Group group,
                                   @Context final UriInfo uriInfo,
                                   @Context final HttpHeaders headers) {
        groupManager.createGroup(group);
        return Response.ok(new StatusEntity("200", uriInfo.getAbsolutePath() + "/" + group.getCode())).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateGroup(final Group group,
                                 @Context final UriInfo uriInfo,
                                 @Context final HttpHeaders headers) {
        groupManager.updateGroup(group);
        return Response.ok(new StatusEntity("200", uriInfo.getAbsolutePath() + "/" + group.getCode())).build();
    }

    @DELETE
    @Path("/{code}/")
    public Response deleteGroup(@PathParam("code") final String code,
                                 @Context final UriInfo uriInfo,
                                 @Context final HttpHeaders headers) {
        groupManager.deleteGroup(code);
        return Response.ok(new StatusEntity("200",
                "Group with code='" + code + "' has been successfully removed")).build();
    }

    public void setGroupManager(final IGroupManager groupManager) {
        this.groupManager = groupManager;
    }
}
