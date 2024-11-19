package dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DragonHeadDTO {
    private float eyesCount;
    @NotNull
    private Double toothCount; //Поле не может быть null
}
