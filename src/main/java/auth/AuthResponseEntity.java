package auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import responses.ResponseEntity;
import responses.ResponseStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseEntity extends ResponseEntity {
    private ResponseStatus status;
    private String details;
    private Object data;
}
