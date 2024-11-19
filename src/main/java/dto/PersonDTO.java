package dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import objects.Color;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    @NotNull
    @NotEmpty
    private String name; //Поле не может быть null, Строка не может быть пустой
    @NotNull
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле может быть null
    @NotNull
    private LocationDTO location; //Поле не может быть null
    @NotNull
    private java.time.LocalDate birthday; //Поле не может быть null
    @Positive
    private Integer height; //Поле может быть null, Значение поля должно быть больше 0
}
