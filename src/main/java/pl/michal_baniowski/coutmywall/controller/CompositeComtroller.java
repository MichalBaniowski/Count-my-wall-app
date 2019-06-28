package pl.michal_baniowski.coutmywall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.michal_baniowski.coutmywall.dto.CompositeDto;
import pl.michal_baniowski.coutmywall.service.CompositeService;

import java.util.List;

@RestController
@RequestMapping("/api/composites")
public class CompositeComtroller {
    @Autowired
    private CompositeService compositeService;

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
    CompositeDto calculateComposite(@RequestBody CompositeDto compositeDto) {
        return compositeService.calculateProperties(compositeDto);
    }
}
