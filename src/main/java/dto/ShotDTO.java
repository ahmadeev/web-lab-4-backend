package dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShotDTO {
    @NotNull
    private List<Double> x;
    @NotNull
    private double y;
    @NotNull
    private List<Double> r;
}
