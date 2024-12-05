package auth;

import lombok.Getter;

import java.security.Principal;

public class UserPrincipal implements Principal {
    private String username;
    @Getter
    private Long userId;  // Хранение ID пользователя

    public UserPrincipal(String username, Long userId) {
        this.username = username;
        this.userId = userId;
    }

    @Override
    public String getName() {
        return username;  // Возвращаем имя пользователя
    }
}
