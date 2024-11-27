package responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DragonResponseEntity extends ResponseEntity {
    private ResponseStatus status;
    private String details;
    private Object data;
}
