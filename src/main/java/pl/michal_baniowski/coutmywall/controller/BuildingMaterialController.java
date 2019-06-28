package pl.michal_baniowski.coutmywall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.michal_baniowski.coutmywall.dto.BuildingMaterialDto;
import pl.michal_baniowski.coutmywall.service.BuildingMaterialService;

import java.util.List;

@RestController
@RequestMapping("/api/building-materials")
public class BuildingMaterialController {

    @Autowired
    private BuildingMaterialService materialService;
    @GetMapping("")
    public List<BuildingMaterialDto> getDefaultBuildingMaterials(@RequestParam(required = false) String materialName){
        if(materialName != null && (!materialName.isEmpty())){
            return materialService.getAllDefaultMaterialsByName(materialName);
        }
        return materialService.getAllDefaultBuildingMaterials();
    }
    @GetMapping("/category/{categoryName}")
    public List<BuildingMaterialDto> getDefaultBuildingMaterialsByCategory(@PathVariable String categoryName){
        return materialService.getAllDefaultMaterialsByCategory(categoryName);
    }

}
