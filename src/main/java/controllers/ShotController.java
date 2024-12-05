package controllers;

import auth.AuthService;
import dto.ShotDTO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import objects.*;
import responses.ResponseEntity;
import responses.ResponseStatus;
import jakarta.inject.Inject;
import services.ShotService;

import java.util.List;

@Named(value = "shotController")
@ApplicationScoped
@Path("/user")
public class ShotController {

    @Inject
    private ShotService shotService;

    @Inject
    private AuthService authService;

    @PostConstruct
    private void init() {
        System.out.println("ShotController initialized");
    }

    @POST
    @Path("/shot")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUserShot(@Valid ShotDTO shotDTO, @Context SecurityContext securityContext) {
        System.out.println("Trying to create shot");

        String username = securityContext.getUserPrincipal().getName();
        System.out.println(username);

        long userId = authService.getUserByName(username).getId();
        System.out.println(userId);

        List<Shot> shot = shotService.createEntityFromDTO(shotDTO);
        shotService.createUserShot(shot, userId);

        System.out.println("Successfully created shot");
        return Response.ok().entity(
                new ResponseEntity(ResponseStatus.SUCCESS,"Successfully created shot", null)
        ).build();
    }

    @GET
    @Path("/shot/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShot(@PathParam("id") long id) {
        System.out.println("Trying to get shot #" + id);
        Shot shot = shotService.getShotById(id);
        if (shot != null) return Response.ok().entity(
                new ResponseEntity(ResponseStatus.SUCCESS, "", shot)
        ).build();
        return Response.status(Response.Status.NOT_FOUND).entity(
                new ResponseEntity(ResponseStatus.ERROR, "Shot not found", null)
        ).build();
    }

    // @QueryParam в случае '/shots?page=1&size=10, @PathParam для штук типа '/shot/{id}'
    @GET
    @Path("/shots/all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShots(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size
    ) {
        List<Shot> shots = shotService.getShots(page, size);

        return Response.ok().entity(
                new ResponseEntity(ResponseStatus.SUCCESS, "", shots)
        ).build();
    }

    // было бы круто по всем правилам REST добавить имя пользователя в строку запроса
    @GET
    @Path("/shots")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserShots(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @Context SecurityContext securityContext
    ) {
        String username = securityContext.getUserPrincipal().getName();
        System.out.println(username);

        long userId = authService.getUserByName(username).getId();
        System.out.println(userId);

        List<Shot> shots = shotService.getUserShots(page, size, userId);

        return Response.ok().entity(
                new ResponseEntity(ResponseStatus.SUCCESS, "", shots)
        ).build();
    }

    // подумать. пока было трудно
    @PUT
    @Path("/shot/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUserShot(@PathParam("id") long id, @Valid ShotDTO shotDTO, @Context SecurityContext securityContext) {
        String username = securityContext.getUserPrincipal().getName();
        System.out.println(username);

        long userId = authService.getUserByName(username).getId();
        System.out.println(userId);

        boolean isUpdated = shotService.updateUserShotById(id, userId, shotDTO);

        if (isUpdated) {
            return Response.ok().entity(
                    new ResponseEntity(ResponseStatus.SUCCESS,"Successfully updated dragon", null)
            ).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).entity(
                new ResponseEntity(ResponseStatus.ERROR,"Shot was not updated", null)
        ).build();
    }

    @DELETE
    @Path("/shot/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUserShot(@PathParam("id") long id, @Context SecurityContext securityContext) {
        System.out.println("Trying to delete shot #" + id);

        String username = securityContext.getUserPrincipal().getName();
        System.out.println(username);

        long userId = authService.getUserByName(username).getId();
        System.out.println(userId);

        boolean isDeleted = shotService.deleteUserShotById(id, userId);
        if (isDeleted) {
            return Response.noContent().entity(
                    new ResponseEntity(ResponseStatus.SUCCESS, "Successfully deleted shot", null)
            ).build(); // Статус 204, если удаление успешно
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    new ResponseEntity(ResponseStatus.ERROR, "Shot not found", null)
            ).build(); // Статус 404, если дракон не найден
        }
    }

    @DELETE
    @Path("/shots")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUserShots(@Context SecurityContext securityContext) {
        String username = securityContext.getUserPrincipal().getName();
        System.out.println(username);

        long userId = authService.getUserByName(username).getId();
        System.out.println(userId);

        int rowsDeleted = shotService.deleteUserShots(userId);;

        if (rowsDeleted > 0) {
            //  можно использовать noContent(), но тогда не будет тела ответа
            return Response.ok().entity(
                    new ResponseEntity(ResponseStatus.SUCCESS, "Successfully deleted %d shots".formatted(rowsDeleted), null)
            ).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    new ResponseEntity(ResponseStatus.ERROR, "Shots belong to user not found", null)
            ).build();
        }
    }

    @GET
    @Path("/shots-count")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShotsCount(@Context SecurityContext securityContext) {
        System.out.println("Trying to get shots count");

        String username = securityContext.getUserPrincipal().getName();
        System.out.println(username);
        long userId = authService.getUserByName(username).getId();
        System.out.println(userId);

        long count = shotService.getShotsCount(userId);
        if (count > 0) return Response.ok().entity(
                new ResponseEntity(ResponseStatus.SUCCESS, "", count)
        ).build();
        return Response.status(Response.Status.NOT_FOUND).entity(
                new ResponseEntity(ResponseStatus.ERROR, "Shots not found", null)
        ).build();
    }
}
