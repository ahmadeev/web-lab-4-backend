package controllers;

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

    @PostConstruct
    private void init() {
        System.out.println("MainController initialized");
    }

    @Inject
    private MainService mainService;

    @GET
    @Path("/dragon/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDragon(@PathParam("id") long id) {
        System.out.println("Trying to get dragon №" + id);
        return Response.ok(mainService.getDragonById(id)).build();
    }

    @POST
    @Path("/dragon")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDragon() {
        System.out.println("Trying to create dragon");
        Dragon dragon = new Dragon(
                "dragon",
                new Coordinates((long) 600, (int) 700),
                java.time.ZonedDateTime.now(),
                new DragonCave((float) 5),
                new Person(
                        "killer",
                        Color.WHITE,
                        Color.BLACK,
                        new Location(
                                1, 2, 3
                        ),
                        LocalDate.now(),
                        (Integer) 192
                ),
                (long) 52,
                null,
                (long) 75,
                DragonCharacter.CHAOTIC_EVIL,
                new DragonHead(
                        (float) 2, (Double) 32.5
                )
        );

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
