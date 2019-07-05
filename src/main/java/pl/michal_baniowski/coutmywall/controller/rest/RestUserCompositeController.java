package pl.michal_baniowski.coutmywall.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.michal_baniowski.coutmywall.dto.CompositeDto;
import pl.michal_baniowski.coutmywall.exception.handler.ErrorMapProvider;
import pl.michal_baniowski.coutmywall.service.CompositeService;
import pl.michal_baniowski.coutmywall.validation.CompleteEntityValidationGroup;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/composites")
public class RestUserCompositeController {

    private CompositeService compositeService;
    private ErrorMapProvider errorMapProvider;
    @Autowired
    public RestUserCompositeController(CompositeService compositeService, ErrorMapProvider errorMapProvider) {
        this.compositeService = compositeService;
        this.errorMapProvider = errorMapProvider;
    }

    @GetMapping("")
    public List<CompositeDto> getAllUsersAndDefault(@RequestParam (required = false) String name, Principal principal) {
        if(name != null && (!name.isEmpty())) {
            return compositeService.getAllCompositesByName(name, principal.getName());
        }
        return compositeService.getAllDefaultAndUsersComposites(principal.getName());
    }
    @GetMapping("/composite-type/{typeId}")
    public List<CompositeDto> getAllCompositesByType(Principal principal, @PathVariable Long typeId) {
        return compositeService.getAllCompositesByType(typeId, principal.getName());
    }
    @PostMapping("")
    public void saveNewCompositeDto(@Validated({CompleteEntityValidationGroup.class}) @RequestBody CompositeDto compositeDto) {
        compositeService.saveComposite(compositeDto);
    }

    @GetMapping("/{compositeId}")
    public CompositeDto findCompositeById(@PathVariable Long compositeId, Principal principal) {
        return compositeService.getCompositeById(compositeId, principal.getName());
    }

    @PutMapping("/{compositeId}")
    public void saveComposite(@Validated({CompleteEntityValidationGroup.class}) @RequestBody CompositeDto compositeDto, @PathVariable Long compositeId, Principal principal) {
        compositeService.updateComposite(compositeDto, compositeId, principal.getName());
    }

    @DeleteMapping("/{compositeId}")
    public void deleteComposite (@PathVariable Long compositeId, Principal principal) {
        compositeService.deleteComposite(compositeId, principal.getName());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return errorMapProvider.getErrorsMap(ex);
    }
}
