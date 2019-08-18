package pl.michal_baniowski.coutmywall.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.michal_baniowski.coutmywall.entity.auth.User;
import pl.michal_baniowski.coutmywall.exception.exception.RegistrationArgumentNotValidException;
import pl.michal_baniowski.coutmywall.exception.handler.ErrorMapProvider;
import pl.michal_baniowski.coutmywall.service.auth.UserService;

import javax.validation.Valid;
import java.util.Map;

@RestController

public class RestUserRegistrationController {

    private UserService userService;
    private ErrorMapProvider errorMapProvider;

    @Autowired
    public RestUserRegistrationController(UserService userService, ErrorMapProvider errorMapProvider) {
        this.userService = userService;
        this.errorMapProvider = errorMapProvider;
    }

    @PostMapping("/api/register")
    public boolean register(@Valid User user, BindingResult bindingResult) {
        if(userService.existByUsername(user.getUsername())) {
            FieldError fieldError = new FieldError("user", "username", "Username exists");
            bindingResult.addError(fieldError);
        }
        if (userService.existByEmail(user.getEmail())) {
            FieldError fieldError = new FieldError("user", "email", "Email exists");
            bindingResult.addError(fieldError);
        }
        if(bindingResult.hasErrors()) {
            throw new RegistrationArgumentNotValidException(bindingResult);
        }
        return userService.saveUser(user);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RegistrationArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            RegistrationArgumentNotValidException ex) {
        return errorMapProvider.getErrorsMap(ex);
    }


}
