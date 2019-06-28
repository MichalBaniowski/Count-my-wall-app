package pl.michal_baniowski.coutmywall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.michal_baniowski.coutmywall.dto.CompositeDto;
import pl.michal_baniowski.coutmywall.service.CompositeService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/{userId}/composites")
public class UserCompositeController {
    @Autowired
    private CompositeService compositeService;

    @GetMapping("")
    public List<CompositeDto> getAllUsersAndDefault(@RequestParam (required = false) String name, @PathVariable Long userId) {
        if(name != null && (!name.isEmpty())) {
            return compositeService.getAllCompositesByName(name, userId);
        }
        return compositeService.getAllDefaultAndUsersComposites(userId);
    }
    @GetMapping("/composite-type/{typeId}")
    public List<CompositeDto> getAllCompositesByType(@PathVariable Long userId, @PathVariable Long typeId) {
        return compositeService.getAllCompositesByType(typeId, userId);
    }
    @PostMapping("")
    public void saveNewCompositeDto(@Valid @RequestBody CompositeDto compositeDto) {
        compositeService.saveComposite(compositeDto);
    }

    @GetMapping("/{compositeId}")
    public CompositeDto findCompositeById(@PathVariable Long compositeId, @PathVariable Long userId) {
        return compositeService.getCompositeById(compositeId, userId);
    }

    @PutMapping("/{compositeId}")
    public void saveComposite(@Valid @RequestBody CompositeDto compositeDto, @PathVariable Long compositeId, @PathVariable Long userId) {
        compositeService.updateComposite(compositeDto, compositeId, userId);
    }

    @DeleteMapping("/{compositeId}")
    public void deleteComposite (@PathVariable Long compositeId, @PathVariable Long userId) {
        compositeService.deleteComposite(compositeId, userId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
