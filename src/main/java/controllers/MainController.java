package controllers;

import dto.DragonDTO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objects.*;
import responses.DragonResponseEntity;
import responses.ResponseStatus;
import services.MainService;
import jakarta.inject.Inject;

import java.util.List;

@Named(value = "mainController")
@ApplicationScoped
@Path("/user")
public class MainController {

    @Inject
    private MainService mainService;

    @PostConstruct
    private void init() {
        System.out.println("MainController initialized");
    }

    @GET
    @Path("/dragon/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDragon(@PathParam("id") long id) {
        System.out.println("Trying to get dragon №" + id);
        Dragon dragon = mainService.getDragonById(id);
        if (dragon != null) return Response.ok().entity(
                new DragonResponseEntity(ResponseStatus.SUCCESS, "", dragon)
        ).build();
        return Response.status(Response.Status.NOT_FOUND).entity(
                new DragonResponseEntity(ResponseStatus.ERROR, "Dragon not found", null)
        ).build();
    }

    // @QueryParam в случае '/dragons?page=1&size=10, @PathParam для штук типа '/dragon/{id}'
    @GET
    @Path("/dragons")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDragons(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("10") int size) {
        List<Dragon> dragons = mainService.getDragons(page, size);

        return Response.ok().entity(
                new DragonResponseEntity(ResponseStatus.SUCCESS, "", dragons)
        ).build();
    }

    @POST
    @Path("/dragon")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDragon(@Valid DragonDTO dragonDTO) {
        System.out.println("Trying to create dragon");

        // ЗАГЛУШКА
        long userId = 1;

        Dragon dragon = mainService.createEntityFromDTO(dragonDTO);
        mainService.createDragon(dragon, userId);

        System.out.println("Successfully created dragon");
        return Response.ok().entity(
                new DragonResponseEntity(ResponseStatus.SUCCESS,"Successfully created dragon", null)
        ).build();
    }

    // подумать. пока было трудно
    @PUT
    @Path("/dragon/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDragon(@PathParam("id") long id, @Valid DragonDTO dragonDTO) {
        // ЗАГЛУШКА
        long userId = 1;

        boolean isUpdated = mainService.updateDragonById(id, userId, dragonDTO);

        if (isUpdated) {
            return Response.ok().entity(
                    new DragonResponseEntity(ResponseStatus.SUCCESS,"Successfully updated dragon", null)
            ).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).entity(
                new DragonResponseEntity(ResponseStatus.ERROR,"Dragon was not updated", null)
        ).build();
    }

    @DELETE
    @Path("/dragon/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDragon(@PathParam("id") long id) {
        System.out.println("Trying to delete dragon #" + id);

        // ЗАГЛУШКА
        long userId = 1;

        boolean isDeleted = mainService.deleteDragonById(id, userId);
        if (isDeleted) {
            return Response.noContent().entity(
                    new DragonResponseEntity(ResponseStatus.SUCCESS, "Successfully deleted dragon", null)
            ).build(); // Статус 204, если удаление успешно
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    new DragonResponseEntity(ResponseStatus.ERROR, "Dragon not found", null)
            ).build(); // Статус 404, если дракон не найден
        }
    }

    @DELETE
    @Path("/dragons")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDragons() {
        // ЗАГЛУШКА
        long userId = 1;

        int rowsDeleted = mainService.deleteDragons(userId);;

        if (rowsDeleted > 0) {
            //  можно использовать noContent(), но тогда не будет тела ответа
            return Response.ok().entity(
                    new DragonResponseEntity(ResponseStatus.SUCCESS, "Successfully deleted %d dragons".formatted(rowsDeleted), null)
            ).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    new DragonResponseEntity(ResponseStatus.ERROR, "Dragons belong to user not found", null)
            ).build();
        }
    }
}
