package pl.michal_baniowski.coutmywall.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INSUFFICIENT_STORAGE)
public class FailedRepositoryOperationException extends RuntimeException {
    public FailedRepositoryOperationException(String message) {
        super(message);
    }
}
