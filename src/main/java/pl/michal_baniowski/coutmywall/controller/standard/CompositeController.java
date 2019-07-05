package pl.michal_baniowski.coutmywall.controller.standard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.michal_baniowski.coutmywall.dto.BuildingMaterialDto;
import pl.michal_baniowski.coutmywall.dto.CompositeDto;
import pl.michal_baniowski.coutmywall.dto.CompositeTypeDto;
import pl.michal_baniowski.coutmywall.service.BuildingMaterialService;
import pl.michal_baniowski.coutmywall.service.CompositeService;
import pl.michal_baniowski.coutmywall.service.CompositeTypeService;
import pl.michal_baniowski.coutmywall.validation.CompositeCalculateValidationGroup;

import java.util.List;

@Controller
@RequestMapping("/composities")
public class CompositeController {

    private CompositeService compositeService;
    private CompositeTypeService compositeTypeService;
    private BuildingMaterialService materialService;
    @Autowired
    public CompositeController(CompositeService compositeService, CompositeTypeService compositeTypeService, BuildingMaterialService materialService) {
        this.compositeService = compositeService;
        this.compositeTypeService = compositeTypeService;
        this.materialService = materialService;
    }

    @ModelAttribute("compositeTypes")
    public List<CompositeTypeDto> getAllCompositeTypes() {
        return compositeTypeService.getAllCompositeTypeDto();
    }
    @ModelAttribute("materials")
    public List<BuildingMaterialDto> getAllDefaultBuildingMaterials() {
        return materialService.getAllDefaultBuildingMaterials();
    }

    @GetMapping("")
    public String getDefaultComposites (@RequestParam(required = false) String name, Model model) {
        if(name != null && (!name.isEmpty())) {
            model.addAttribute("composites", compositeService.getAllDefaultCompositesByName(name));
        } else {
            model.addAttribute("composites", compositeService.getAllDefaultComposites());
        }
        return "composites";
    }

    @GetMapping("/composite-type/{typeId}")
    public String  getDefaultCompositesByType(@PathVariable Long typeId, Model model) {
        model.addAttribute("composites", compositeService.getDefaultCompositesByType(typeId));
        return "composites";
    }

    @GetMapping("/calculate")
    public String getCompositeForm(Model model) {
        model.addAttribute("composite", new CompositeDto());
        return "composite_form";
    }

    @PostMapping("calculate")
    public String calculateComposite(@Validated(CompositeCalculateValidationGroup.class) CompositeDto compositeDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "composite_form";
        }
        model.addAttribute("composite", compositeService.calculateProperties(compositeDto));
        return "composite_form";
    }

}
