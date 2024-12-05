package objects;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "shot")
@Getter @Setter
@NoArgsConstructor
public class Shot {
    @Transient
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Поле x не должно быть равно 'null'")
    @Column(name = "x", nullable = false)
    private double x;

    @NotNull(message = "Поле y не должно быть равно 'null'")
    @Column(name = "y", nullable = false)
    private double y;

    @NotNull(message = "Поле x не должно быть равно 'null'")
    @Column(name = "r", nullable = false)
    private double r;

    @NotNull(message = "Поле hit не должно быть равно 'null'")
    @Column(name = "hit", nullable = false)
    private boolean isHit;

    @NotNull(message = "Поле curr_time не должно быть пустым")
    @Column(name = "curr_time", nullable = false)
    private String currentTime;

    @NotNull(message = "Поле script_time не должно быть пустым")
    @Column(name = "script_time", nullable = false)
    private long scriptTime;

    @NotNull(message = "Поле owner_id не должно быть пустым")
    @Column(name = "owner_id", nullable = false)
    private long ownerId;

    public Shot(double x, double y, double r, long scriptTime, long ownerId) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.scriptTime = scriptTime;
        this.ownerId = ownerId;
    }

    public Shot(double x, double y, double r, long scriptTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.scriptTime = scriptTime;
    }

    public Shot(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @PrePersist
    protected void onCreate() {
        currentTime = LocalTime.now().format(formatter);
    }

    public String toJson() {
        try (Jsonb jsonb = JsonbBuilder.create()) {
            return jsonb.toJson(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
