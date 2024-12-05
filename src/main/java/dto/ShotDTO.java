package dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.ValidDoubleList;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShotDTO {
    @NotNull(message = "Поле 'x' не должно быть null")
    @Size(min = 1, message = "Список 'x' должен содержать хотя бы одно значение")
    @ValidDoubleList(message = "Список 'x' должен содержать значения в диапазоне от -2 до 2")
    private List<Double> x;

    @NotNull(message = "Поле 'y' не должно быть null")
    @DecimalMin(value = "-5")
    @DecimalMax(value = "3")
    private BigDecimal y;

    @NotNull(message = "Поле 'r' не должно быть null")
    @Size(min = 1, message = "Список 'r' должен содержать хотя бы одно значение")
    @ValidDoubleList(message = "Список 'r' должен содержать значения в диапазоне от -2 до 2")
    private List<Double> r;
}
