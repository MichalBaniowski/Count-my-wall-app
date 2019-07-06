package pl.michal_baniowski.coutmywall.controller.standard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.michal_baniowski.coutmywall.dto.MaterialCategoryDto;
import pl.michal_baniowski.coutmywall.service.BuildingMaterialService;
import pl.michal_baniowski.coutmywall.service.MaterialCategoryService;

import java.util.List;

@Controller
@RequestMapping("/building-material")
public class BuildingMaterialController {

    private BuildingMaterialService materialService;
    private MaterialCategoryService categoryService;

    @Autowired
    public BuildingMaterialController(BuildingMaterialService materialService, MaterialCategoryService categoryService) {
        this.materialService = materialService;
        this.categoryService = categoryService;
    }

    @ModelAttribute("categories")
    public List<MaterialCategoryDto> getMaterialCategories() {
        return categoryService.getAllMaterialCategoriesDto();
    }

    @GetMapping("")
    public String getDefaultBuildingMaterials(@RequestParam(required = false) String materialName, Model model) {
        if(materialName != null && (!materialName.isEmpty())) {
            model.addAttribute("buildingMaterials", materialService.getAllDefaultMaterialsByName(materialName));
        } else {
            model.addAttribute("buildingMaterials", materialService.getAllDefaultBuildingMaterials());
        }
        return "building_materials";
    }

    @RequestMapping("/name")
    public String getMaterialNameForm() {
        return"material_name_search";
    }

    @GetMapping("/category")
    public String getDefaultBuildingMaterialsByCategory(@RequestParam(required = false) String categoryName, Model model) {
        if(categoryName != null){
            model.addAttribute("buildingMaterials", materialService.getAllDefaultMaterialsByCategory(categoryName));
            return "building_materials";
        } else {
            return "material_category_search";
        }

    }
}
