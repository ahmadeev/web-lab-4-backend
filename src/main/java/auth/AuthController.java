package auth;

import dto.DragonDTO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objects.Dragon;
import responses.DragonResponseEntity;
import responses.ResponseStatus;
import services.MainService;

@Named(value = "authController")
@ApplicationScoped
@Path("/auth")
public class AuthController {

    @Inject
    private AuthService authService;

    @PostConstruct
    private void init() {
        System.out.println("AuthController initialized");
    }

    @POST
    @Path("/sign-in")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signIn(@Valid UserDTO userDTO) {
        System.out.println("User is trying to sign in");

        User userInput = authService.createEntityFromDTO(userDTO);
        User userStored = authService.getUserByName(userInput.getName());

        if (userInput.getPassword().equals(userStored.getPassword())) {
            System.out.println("User successfully signed in");
            return Response.ok().entity(
                    new DragonResponseEntity(ResponseStatus.SUCCESS,"User successfully signed in", null)
            ).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(
                new DragonResponseEntity(ResponseStatus.ERROR,"Error during sign in attempt", null)
        ).build();
    }

    @POST
    @Path("/sign-up")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signUp(@Valid UserDTO userDTO) {
        System.out.println("User is trying to sign up");

        User userInput = authService.createEntityFromDTO(userDTO);
        User userStored = authService.getUserByName(userInput.getName());

        if (userStored != null) {
            System.out.println("User already exists");
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new DragonResponseEntity(ResponseStatus.ERROR,"Error during sign up attempt", null)
            ).build();
        }

        authService.createUser(userInput);
        System.out.println("User successfully signed up");
        return Response.ok().entity(
                new DragonResponseEntity(ResponseStatus.SUCCESS,"User successfully signed up", null)
        ).build();
    }
}