package pl.michal_baniowski.coutmywall.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoEntityFound extends RuntimeException {
    public NoEntityFound(String message) {
        super(message);
    }
}
