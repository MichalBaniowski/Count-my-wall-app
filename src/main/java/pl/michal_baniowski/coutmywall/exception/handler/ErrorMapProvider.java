package pl.michal_baniowski.coutmywall.exception.handler;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import pl.michal_baniowski.coutmywall.exception.exception.RegistrationArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@Component
public class ErrorMapProvider {

    public Map<String, String> getErrorsMap(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        return getMapFromBindingResult(bindingResult);
    }

    public Map<String, String> getErrorsMap(RegistrationArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        return getMapFromBindingResult(bindingResult);
    }

    private Map<String, String> getMapFromBindingResult(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
