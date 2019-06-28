package pl.michal_baniowski.coutmywall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.michal_baniowski.coutmywall.dto.CompositeTypeDto;
import pl.michal_baniowski.coutmywall.service.CompositeTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/composite-types")
public class CompositeTypeController {
    @Autowired
    private CompositeTypeService compositeTypeService;

    @GetMapping("")
    List<CompositeTypeDto> getAllCompositeTypes(){
        return compositeTypeService.getAllCompositeTypeDto();
    }
    @GetMapping("/{typeId}")
    CompositeTypeDto getCompositeTypeById(@PathVariable Long typeId) {
        return compositeTypeService.getCompositeTypeDtoById(typeId);
    }
}
