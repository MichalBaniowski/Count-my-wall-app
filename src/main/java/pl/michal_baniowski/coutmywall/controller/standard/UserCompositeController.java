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
import pl.michal_baniowski.coutmywall.exception.exception.AccessDeniedException;
import pl.michal_baniowski.coutmywall.exception.exception.FailedRepositoryOperationException;
import pl.michal_baniowski.coutmywall.service.BuildingMaterialService;
import pl.michal_baniowski.coutmywall.service.CompositeService;
import pl.michal_baniowski.coutmywall.service.CompositeTypeService;
import pl.michal_baniowski.coutmywall.validation.CompleteEntityValidationGroup;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user/composites")
public class UserCompositeController {

    private CompositeService compositeService;
    private BuildingMaterialService materialService;
    private CompositeTypeService compositeTypeService;
    @Autowired
    public UserCompositeController(CompositeService compositeService, BuildingMaterialService materialService, CompositeTypeService compositeTypeService) {
        this.compositeService = compositeService;
        this.materialService = materialService;
        this.compositeTypeService = compositeTypeService;
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
    public String getUserComposites (@RequestParam(required = false) String name, Model model, Principal principal) {
        if(name != null && (!name.isEmpty())) {
            model.addAttribute("composites", compositeService.getAllCompositesByName(name, principal.getName()));
        } else {
            model.addAttribute("composites", compositeService.getAllDefaultAndUsersComposites(principal.getName()));
        }
        return "composites";
    }

    @GetMapping("/composite-type/{typeId}")
    public String getAllCompositesByType (Principal principal, @PathVariable Long typeId, Model model) {
        model.addAttribute("composites", compositeService.getAllCompositesByType(typeId, principal.getName()));
        return "composites";
    }

    @GetMapping("/{compositeId}")
    public String getCompositeById (Principal principal, @PathVariable Long compositeId, Model model) {
        try {
            model.addAttribute("composite", compositeService.getCompositeById(compositeId, principal.getName()));
            return "composite_details";
        } catch (AccessDeniedException e) {
            model.addAttribute("prompt", e.getMessage());
        }
        return "action-result";
    }

    @PostMapping("/save")
    public String getCompositeForm (@Validated(CompleteEntityValidationGroup.class) CompositeDto compositeDto,
                                    BindingResult bindingResult,
                                    Model model) {
       if(bindingResult.hasErrors()) {
           return "composite_form";
       }
       try {
           compositeService.saveComposite(compositeDto);
           model.addAttribute("prompt", "zapisano przegrodę");
       } catch (FailedRepositoryOperationException e) {
           model.addAttribute("prompt", e.getMessage());
       }
       return "action-result";
    }

    @GetMapping("/edit/{compositeId}")
    public String getCompositeToEditForm(@PathVariable Long compositeId, Principal principal, Model model) {
        try{
            model.addAttribute("composite", compositeService.getCompositeById(compositeId, principal.getName()));
        } catch (AccessDeniedException e) {
            model.addAttribute("prompt", e.getMessage());
            return "action-result";
        }
        return "composite_form";
    }

    @PostMapping("/edit/{compositeId}")
    public String processCompositeToEditForm(@Validated (CompleteEntityValidationGroup.class) CompositeDto compositeDto,
                                             BindingResult bindingResult,
                                             Principal principal,
                                             Model model,
                                             @PathVariable Long compositeId) {
        if(bindingResult.hasErrors()) {
            return "composite_form";
        }
        try {
            compositeService.updateComposite(compositeDto, compositeId, principal.getName());
            model.addAttribute("prompt", "przegroda zapisana");
        } catch (AccessDeniedException e) {
            model.addAttribute("prompt", e.getMessage());
        }
        return "action-result";
    }

    @RequestMapping("/delete/{compositeId}")
    public String deleteMaterial(Principal principal, @PathVariable Long compositeId, Model model) {
        try {
            compositeService.deleteComposite(compositeId, principal.getName());
            model.addAttribute("prompt", "przegrodę usunięto");
        } catch (RuntimeException e) {
            model.addAttribute("prompt", e.getMessage());
        }
        return "action-result";
    }
}
