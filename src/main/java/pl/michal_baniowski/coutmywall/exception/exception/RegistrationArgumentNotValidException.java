package pl.michal_baniowski.coutmywall.exception.exception;

import lombok.Data;
import org.springframework.validation.BindingResult;

@Data
public class RegistrationArgumentNotValidException extends RuntimeException {

    private BindingResult bindingResult;

    public RegistrationArgumentNotValidException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
}
