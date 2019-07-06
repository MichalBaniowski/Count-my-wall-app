package pl.michal_baniowski.coutmywall.controller.standard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.michal_baniowski.coutmywall.dto.BuildingMaterialDto;
import pl.michal_baniowski.coutmywall.exception.exception.AccessDeniedException;
import pl.michal_baniowski.coutmywall.exception.exception.FailedRepositoryOperationException;
import pl.michal_baniowski.coutmywall.service.BuildingMaterialService;
import pl.michal_baniowski.coutmywall.service.MaterialCategoryService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/user/building-materials")
public class UserBuildingMaterialController {

    private BuildingMaterialService materialService;
    private MaterialCategoryService categoryService;
    @Autowired
    public UserBuildingMaterialController(BuildingMaterialService materialService, MaterialCategoryService categoryService) {
        this.materialService = materialService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String getBuildingMaterials(Principal principal, @RequestParam(required = false) String materialName, Model model) {
        if(materialName != null && (!materialName.isEmpty())) {
            model.addAttribute("materials", materialService.getAllMaterialsByName(materialName, principal.getName()));
        } else {
            model.addAttribute("materials", materialService.getAllDefaultAndUsersMaterials(principal.getName()));
        }
        return "building-materials";
    }

    @GetMapping("/category")
    public String getBuildingMaterialsByCategory(Principal principal,@RequestParam String categoryName, Model model){
        model.addAttribute("materials",materialService.getAllDefaultAndUsersMaterialsByCategory(principal.getName(), categoryName));
        return "building-materials";
    }

    @GetMapping("/{materialId}")
    public String getBuildingMaterialById(@PathVariable Long materialId, Principal principal, Model model) {
        model.addAttribute("materials",materialService.getMaterialByMaterialId(principal.getName(), materialId));
        return "building-materials";
    }

    @GetMapping("/create")
    public String getMaterialForm(Model model){
        model.addAttribute("material", new BuildingMaterialDto());
        return "material-form";
    }

    @PostMapping("/create")
    public String processMaterialForm(@Valid BuildingMaterialDto buildingMaterialDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "material-form";
        }
        try {
            materialService.saveMaterial(buildingMaterialDto);
            model.addAttribute("prompt", "materiał zapisano");
        } catch (FailedRepositoryOperationException e) {
            model.addAttribute("prompt", e.getMessage());
        }
        return "action-result";
    }

    @GetMapping("/edit/{materialId}")
    public String getMaterialToEditForm(@PathVariable Long materialId, Model model, Principal principal) {
        try {
            model.addAttribute("material", materialService.getMaterialByMaterialId(principal.getName(), materialId));
        } catch (AccessDeniedException e) {
            model.addAttribute("prompt", e.getMessage());
            return "action-result";
        }
       return "material-form";
    }

    @PostMapping("/edit/{materialId}")
    public String processMaterialToEditForm(@Valid BuildingMaterialDto buildingMaterialDto,
                                            BindingResult bindingResult,
                                            Model model,
                                            @PathVariable Long materialId) {
        if(bindingResult.hasErrors()) {
            return "material-form";
        }
        materialService.updateMaterial(buildingMaterialDto, materialId);
        model.addAttribute("prompt", "materiał zapisano");
        return "action-result";
    }

    @RequestMapping("/delete/{materialId}")
    public String deleteMaterial(Principal principal, @PathVariable Long materialId, Model model) {
        try {
            materialService.deleteMaterial(materialId, principal.getName());
            model.addAttribute("prompt", "materiał usunięty");
        } catch (RuntimeException e) {
            model.addAttribute("prompt", e.getMessage());
        }
        return "action-result";
    }


}
