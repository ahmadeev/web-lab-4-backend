package auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "app_users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Поле name не должно быть пустым")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Поле password не должно быть пустым")
    @Column(name = "password")
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
