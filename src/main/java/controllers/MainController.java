package controllers;

import dto.DragonDTO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objects.*;
import services.MainService;
import jakarta.inject.Inject;

import java.time.LocalDate;
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDragon(@PathParam("id") long id) {
        System.out.println("Trying to get dragon №" + id);
        Dragon dragon = mainService.getDragonById(id);
        if (dragon != null) return Response.ok().entity("{\"status\":\"success\",\"details\":\"\", \"data\":" + dragon.toJson() + "}").build();
        return Response.status(Response.Status.NOT_FOUND).entity("{\"status\":\"error\",\"details\":\"Entity not found.\",\"data\":{}}").build();
    }

    // @QueryParam в случае '/dragons?page=1&size=10, @PathParam для штук типа '/dragon/{id}'
    @GET
    @Path("/dragons")
    @Transactional
    public Response getItems(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("10") int size) {
        List<Dragon> dragons = mainService.getDragons(page, size);

        return Response.ok().entity("{\"status\":\"success\",\"details\":\"\", \"data\":" + dragons + "}").build();
    }

    @POST
    @Path("/dragon")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDragon(DragonDTO dragonDTO) {
        System.out.println("Trying to create dragon");

        Dragon dragon = mainService.createEntityFromDTO(dragonDTO);
        mainService.createDragon(dragon);

        System.out.println("Successfully created dragon");
        return Response.ok().entity("{\"status\":\"success\",\"details\":\"Successfully created dragon.\",\"data\":{}}").build();
    }

    @DELETE
    @Path("/dragon/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDragon(@PathParam("id") long id) {
        System.out.println("Trying to delete dragon №" + id);
        boolean isDeleted = mainService.deleteDragonById(id);
        if (isDeleted) {
            return Response.noContent().entity("{\"status\":\"success\",\"details\":\"\"}").build(); // Статус 204, если удаление успешно
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"status\":\"error\",\"details\":\"Entity not found.\",\"data\":{}}").build(); // Статус 404, если дракон не найден
        }
    }
}
