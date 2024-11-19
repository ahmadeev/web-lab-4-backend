package objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dragon_cave")
@Getter @Setter
@NoArgsConstructor
public class DragonCave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Поле number_of_treasures не должно быть пустым")
    @Min(0)
    @Column(name = "number_of_treasures")
    private float numberOfTreasures; //Значение поля должно быть больше 0

    public DragonCave(float numberOfTreasures) {
        this.numberOfTreasures = numberOfTreasures;
    }
}
