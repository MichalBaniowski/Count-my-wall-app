package pl.michal_baniowski.coutmywall.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.michal_baniowski.coutmywall.dto.CompositeDto;
import pl.michal_baniowski.coutmywall.exception.handler.ErrorMapProvider;
import pl.michal_baniowski.coutmywall.service.CompositeService;
import pl.michal_baniowski.coutmywall.validation.CompositeCalculateValidationGroup;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/composites")
public class RestCompositeComtroller {

    private CompositeService compositeService;
    private ErrorMapProvider errorMapProvider;

    @Autowired
    public RestCompositeComtroller(CompositeService compositeService, ErrorMapProvider errorMapProvider) {
        this.compositeService = compositeService;
        this.errorMapProvider = errorMapProvider;
    }

    @GetMapping("")
    List<CompositeDto> getDefaultComposites(@RequestParam (required = false) String name) {
        if(name != null && (!name.isEmpty())) {
            return compositeService.getAllDefaultCompositesByName(name);
        }
        return compositeService.getAllDefaultComposites();
    }
    @GetMapping("/composite-type/{typeId}")
    List<CompositeDto> getDefaultCompositesByType(@PathVariable Long typeId) {
        return compositeService.getDefaultCompositesByType(typeId);
    }
    @PostMapping("")
    CompositeDto calculateComposite(@Validated({CompositeCalculateValidationGroup.class}) @RequestBody CompositeDto compositeDto) {
        return compositeService.calculateProperties(compositeDto);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return errorMapProvider.getErrorsMap(ex);
    }
}
