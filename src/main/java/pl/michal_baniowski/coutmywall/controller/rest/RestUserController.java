package pl.michal_baniowski.coutmywall.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.michal_baniowski.coutmywall.entity.User;
import pl.michal_baniowski.coutmywall.exception.handler.ErrorMapProvider;
import pl.michal_baniowski.coutmywall.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class RestUserController {

    private UserService userService;
    private ErrorMapProvider errorMapProvider;
    @Autowired
    public RestUserController(UserService userService, ErrorMapProvider errorMapProvider) {
        this.userService = userService;
        this.errorMapProvider = errorMapProvider;
    }

    @PostMapping("/api/register")
    public boolean addUser(@Valid User user) {
        return userService.addWithDefaultRole(user);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return errorMapProvider.getErrorsMap(ex);
    }
}
