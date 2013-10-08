package ru.forxy.photo;

import ru.forxy.photo.pojo.Photo;

import javax.ws.rs.*;
import java.util.List;

@Path("/")
public interface IPhotoService {

    @GET
    @Path("/photos")
    @Produces("application/json")
    List<Photo> getPhotos();

    @GET
    @Path("/photos/{url}")
    @Consumes("application/json")
    @Produces("application/json")
    Photo addPhoto(@PathParam("url") String url);

    @POST
    @Path("/photos")
    @Consumes("application/json")
    void addPhoto(Photo photo);

    @DELETE
    @Path("/photos/{url}")
    void deletePhoto(@PathParam("url") String url);
}