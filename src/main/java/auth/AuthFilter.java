package auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Priority;
import jakarta.ws.rs.container.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider  // Аннотация, чтобы зарегистрировать фильтр как ресурс
@Priority(2)  // Фильтр с наибольшим приоритетом
public class AuthFilter implements ContainerRequestFilter {

    private final static String SECRET_KEY = "secret_key";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();

        if (path.startsWith("api/user") || path.startsWith("api/admin")) {
            // Извлекаем токен из заголовка Authorization
            String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

            // Проверяем, есть ли токен в заголовке и начинается ли он с "Bearer"
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                requestContext.abortWith(
                        Response.status(Response.Status.UNAUTHORIZED)
                                .entity("Authorization token is missing or malformed")
                                .build()
                );
                return;
            }

            // Извлекаем сам токен (без "Bearer " префикса)
            String token = authHeader.substring(7);

            try {
                // Проверяем и декодируем токен
                DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                        .build()
                        .verify(token);

                // Если токен валидный, извлекаем информацию, например, имя пользователя
                String username = decodedJWT.getSubject();
                // Мы можем установить информацию о пользователе в контекст запроса
                requestContext.setProperty("username", username);

            } catch (Exception e) {
                // В случае ошибки токен невалиден или истёк
                requestContext.abortWith(
                        Response.status(Response.Status.UNAUTHORIZED)
                                .entity("Invalid or expired token")
                                .build()
                );
            }
        }
    }
}

