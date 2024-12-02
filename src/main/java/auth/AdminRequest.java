package auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "app_admin_requests")
@Getter
@Setter
@NoArgsConstructor
public class AdminRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Поле name не должно быть пустым")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Поле password не должно быть пустым")
    @Column(name = "password")
    private String password;

    @NotNull(message = "Поле role не должно быть пустым")
    @Column(name = "role")
    private Roles role;

    public AdminRequest(String name, String password, Roles role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }
}

