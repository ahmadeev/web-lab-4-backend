package objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dragon_head")
@Getter @Setter
@NoArgsConstructor
public class DragonHead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Поле eyes_count не должно быть пустым")
    @Column(name = "eyes_count")
    private float eyesCount;

    @NotNull(message = "Поле tooth_count не должно быть пустым")
    @Column(name = "tooth_count")
    private Double toothCount; //Поле не может быть null

    public DragonHead(float eyesCount, Double toothCount) {
        this.eyesCount = eyesCount;
        this.toothCount = toothCount;
    }
}
