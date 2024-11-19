package objects;

public class Dragon {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private DragonCave cave; //Поле не может быть null
    private Person killer; //Поле может быть null
    private long age; //Значение поля должно быть больше 0
    private String description; //Поле может быть null
    private Long wingspan; //Значение поля должно быть больше 0, Поле не может быть null
    private DragonCharacter character; //Поле может быть null
    private DragonHead head;
}
