package dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DragonCaveDTO {
    @Positive
    private float numberOfTreasures; //Значение поля должно быть больше 0
}
