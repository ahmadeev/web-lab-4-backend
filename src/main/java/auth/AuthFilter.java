package auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Priority;
import jakarta.ws.rs.container.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.security.Principal;

@Provider  // аннотация, чтобы зарегистрировать фильтр как ресурс
@Priority(2)  // фильтр с наибольшим приоритетом
public class AuthFilter implements ContainerRequestFilter {

    private final static String SECRET_KEY = "secret_key";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // не учитывается начало эндпоинта api (одинаков для всего приложения)
        String path = requestContext.getUriInfo().getPath();
        System.out.println(path);

        if (path.startsWith("/user") || path.startsWith("/admin")) {
            // извлекаем токен из заголовка Authorization
            System.out.println("Verifying token");
            String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

            // проверяем, есть ли токен в заголовке и начинается ли он с "Bearer"
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.out.println("Request denied");
                requestContext.abortWith(
                        Response.status(Response.Status.UNAUTHORIZED)
                                .entity("Authorization token is missing or malformed")
                                .build()
                );
                return;
            }

            // извлекаем сам токен (без "Bearer " префикса)
            String token = authHeader.substring(7);

            try {
                // декодируем и проверяем токен
                DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                        .build()
                        .verify(token);

                System.out.println("Valid token");

                // если токен валидный, извлекаем информацию, например, имя пользователя
                String username = decodedJWT.getSubject();

                // Устанавливаем кастомный SecurityContext
                requestContext.setSecurityContext(new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        return () -> username; // Principal возвращает имя пользователя
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        // Если нужны роли, извлеките их из токена и реализуйте проверку
                        return false;
                    }

                    @Override
                    public boolean isSecure() {
                        return requestContext.getUriInfo().getAbsolutePath().toString().startsWith("https");
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return "Bearer";
                    }
                });

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Invalid token");

                requestContext.abortWith(
                        Response.status(Response.Status.UNAUTHORIZED)
                                .entity("Invalid or expired token")
                                .build()
                );
            }
        } else {
            System.out.println("Token does not have to be verified");
        }
    }
}

