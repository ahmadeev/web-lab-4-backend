package dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShotDTO {
    @NotNull
    private double x;
    @NotNull
    private double y;
    @NotNull
    private double r;
}
