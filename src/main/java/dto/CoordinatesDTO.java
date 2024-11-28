package dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatesDTO {
    @DecimalMin(value = "-596", inclusive = false)
    private long x; //Значение поля должно быть больше -596
    private int y;
}
