package objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "location")
@Getter @Setter
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "x")
    private int x;

    @NotNull(message = "Поле y не должно быть пустым")
    @Column(name = "y")
    private Integer y; //Поле не может быть null

    @Column(name = "z")
    private int z;

    public Location(int x, Integer y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
