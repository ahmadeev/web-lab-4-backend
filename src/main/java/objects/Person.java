package objects;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Location location; //Поле не может быть null
    private java.time.LocalDate birthday; //Поле не может быть null
    private Integer height; //Поле может быть null, Значение поля должно быть больше 0
}
