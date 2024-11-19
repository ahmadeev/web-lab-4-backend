package controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objects.*;
import services.MainService;
import jakarta.inject.Inject;

import java.time.LocalDate;

@Path("/user")
public class MainController {
    @Inject
    private MainService mainService;

    @GET
    @Path("/dragon/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDragon(@PathParam("id") long id) {
        System.out.println("Попытка получить дракона №" + id);
        return Response.ok(mainService.getDragonById(id)).build();
    }

    @POST
    @Path("/create-dragon")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setDragon() {
        System.out.println("Попытка создать дракона");
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
        System.out.println("Успешная попытка создать дракона");
        return Response.ok().build();
    }
}
