package auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;
import responses.ResponseStatus;

import java.util.Date;


@Named(value = "authController")
@ApplicationScoped
@Path("/auth")
public class AuthController {

    private final static String SECRET_KEY = "secret_key";

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

        if (userStored == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new AuthResponseEntity(ResponseStatus.ERROR,"User does not exist", null)
            ).build();
        }

        if (BCrypt.checkpw(userInput.getPassword(), userStored.getPassword())) {
            String token = JWT.create()
                    .withSubject(userInput.getName())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 60000)) // 1 час = 3_600_000
                    .sign(Algorithm.HMAC256(SECRET_KEY));

            System.out.println("User successfully signed in");
            return Response.ok(
                    new AuthResponseEntity(ResponseStatus.SUCCESS,"User successfully signed in", new TokenResponse(token))
            ).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new AuthResponseEntity(ResponseStatus.ERROR,"Password is incorrect", null)
            ).build();
        }
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
                    new AuthResponseEntity(ResponseStatus.ERROR,"User already exists", null)
            ).build();
        }

        authService.createUser(userInput);
        System.out.println("User successfully signed up");
        return Response.ok().entity(
                new AuthResponseEntity(ResponseStatus.SUCCESS,"User successfully signed up", null)
        ).build();
    }
}