package controllers;

import dto.DragonDTO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objects.*;
import services.MainService;
import jakarta.inject.Inject;

import java.time.LocalDate;

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
        return Response.ok(mainService.getDragonById(id)).build();
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
        return Response.ok().build();
    }

    @DELETE
    @Path("/dragon/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDragon(@PathParam("id") long id) {
        System.out.println("Trying to delete dragon №" + id);
        boolean isDeleted = mainService.deleteDragonById(id);
        if (isDeleted) {
            return Response.noContent().build(); // Статус 204, если удаление успешно
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Dragon not found").build(); // Статус 404, если дракон не найден
        }
    }
}
