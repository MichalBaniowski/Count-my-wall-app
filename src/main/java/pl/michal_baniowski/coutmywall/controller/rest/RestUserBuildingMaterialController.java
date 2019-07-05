package pl.michal_baniowski.coutmywall.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.michal_baniowski.coutmywall.dto.BuildingMaterialDto;
import pl.michal_baniowski.coutmywall.exception.handler.ErrorMapProvider;
import pl.michal_baniowski.coutmywall.service.BuildingMaterialService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/building-materials")
public class RestUserBuildingMaterialController {

    private BuildingMaterialService materialService;
    private ErrorMapProvider errorMapProvider;
    @Autowired
    public RestUserBuildingMaterialController(BuildingMaterialService materialService, ErrorMapProvider errorMapProvider) {
        this.materialService = materialService;
        this.errorMapProvider = errorMapProvider;
    }

    @GetMapping("")
    public List<BuildingMaterialDto> getBuildingMaterials(Principal principal, @RequestParam(required = false) String materialName) {
        if(materialName != null && (!materialName.isEmpty())) {
            return materialService.getAllMaterialsByName(materialName, principal.getName());
        }
        return materialService.getAllDefaultAndUsersMaterials(principal.getName());
    }
    @GetMapping("/category/{categoryName}")
    public List<BuildingMaterialDto> getBuildingMaterialsByCategory(Principal principal, @PathVariable String categoryName){
        return materialService.getAllDefaultAndUsersMaterialsByCategory(principal.getName(), categoryName);
    }
    @GetMapping("/{materialId}")
    public BuildingMaterialDto getBuildingMaterialById(@PathVariable Long materialId, Principal principal) {
        return materialService.getMaterialByMaterialId(principal.getName(), materialId);
    }
    @PutMapping("/{materialId}")
    public void updateBuildingMaterial(@Valid @RequestBody BuildingMaterialDto buildingMaterialDto, @PathVariable Long materialId) {
        materialService.updateMaterial(buildingMaterialDto, materialId);
    }
    @PostMapping("")
    public void saveNewBuildingMaterial(@Valid @RequestBody BuildingMaterialDto buildingMaterialDto) {
        materialService.saveMaterial(buildingMaterialDto);
    }
    @DeleteMapping("/{materialId}")
    public void deleteMaterial(Principal principal, @PathVariable Long materialId) {
        materialService.deleteMaterial(materialId, principal.getName());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return errorMapProvider.getErrorsMap(ex);
    }
}
