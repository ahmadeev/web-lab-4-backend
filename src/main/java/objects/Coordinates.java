package objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "coordinates")
@Getter @Setter
@NoArgsConstructor
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Поле x не должно быть пустым")
    @Min(-596)
    @Column(name = "x")
    private long x; //Значение поля должно быть больше -596

    @NotNull(message = "Поле y не должно быть пустым")
    @Column(name = "y")
    private int y;

    public Coordinates(long x, int y) {
        this.x = x;
        this.y = y;
    }
}