package objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
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

    @Positive
    @Column(name = "number_of_treasures")
    private float numberOfTreasures; //Значение поля должно быть больше 0

    public DragonCave(float numberOfTreasures) {
        this.numberOfTreasures = numberOfTreasures;
    }
}
