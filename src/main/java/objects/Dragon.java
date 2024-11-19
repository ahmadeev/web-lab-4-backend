package objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Table(name = "dragon")
@Getter @Setter
@NoArgsConstructor
public class Dragon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotNull(message = "Поле name не должно быть пустым")
    @Column(name = "name")
    private String name; //Поле не может быть null, Строка не может быть пустой

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "coordinates_id", referencedColumnName = "id")
    @NotNull(message = "Поле класса Coordinates не должно быть пустым")
    private Coordinates coordinates; //Поле не может быть null

    @NotNull(message = "Поле creationDate не должно быть пустым")
    @Column(name = "creation-date")
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "dragon_cave_id", referencedColumnName = "id")
    @NotNull(message = "Поле класса DragonCave не должно быть пустым")
    private DragonCave cave; //Поле не может быть null

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person killer; //Поле может быть null

    @Positive
    @Column(name = "age")
    private long age; //Значение поля должно быть больше 0

    @Column(name = "description")
    private String description; //Поле может быть null

    @NotNull(message = "Поле wingspan не должно быть пустым")
    @Positive
    @Column(name = "wingspan")
    private Long wingspan; //Значение поля должно быть больше 0, Поле не может быть null

    @Enumerated(EnumType.STRING)
    @Column(name = "dragon_type")
    private DragonCharacter character; //Поле может быть null

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "dragon_head_id", referencedColumnName = "id")
    private DragonHead head;

    public Dragon(String name, Coordinates coordinates, ZonedDateTime creationDate, DragonCave cave, Person killer, long age, String description, Long wingspan, DragonCharacter character, DragonHead head) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.cave = cave;
        this.killer = killer;
        this.age = age;
        this.description = description;
        this.wingspan = wingspan;
        this.character = character;
        this.head = head;
    }

    @PrePersist
    protected void onCreate() {
        creationDate = ZonedDateTime.now(); // Установка текущей даты при создании
    }
}
