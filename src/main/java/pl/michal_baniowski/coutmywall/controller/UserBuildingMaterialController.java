package pl.michal_baniowski.coutmywall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.michal_baniowski.coutmywall.dto.BuildingMaterialDto;
import pl.michal_baniowski.coutmywall.service.BuildingMaterialService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/{userId}/building-materials")
public class UserBuildingMaterialController {
    @Autowired
    private BuildingMaterialService materialService;
    @GetMapping("")
    public List<BuildingMaterialDto> getBuildingMaterials(@PathVariable Long userId, @RequestParam(required = false) String materialName) {
        if(materialName != null && (!materialName.isEmpty())) {
            return materialService.getAllMaterialsByName(materialName, userId);
        }
        return materialService.getAllDefaultAndUsersMaterials(userId);
    }
    @GetMapping("/category/{categoryName}")
    public List<BuildingMaterialDto> getBuildingMaterialsByCategory(@PathVariable Long userId, @PathVariable String categoryName){
        return materialService.getAllDefaultAndUsersMaterialsByCategory(userId, categoryName);
    }
    @GetMapping("/{materialId}")
    public BuildingMaterialDto getBuildingMaterialById(@PathVariable Long materialId, @PathVariable Long userId) {
        return materialService.getMaterialById(userId, materialId);
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
    public void deleteMaterial(@PathVariable Long userId, @PathVariable Long materialId) {
        materialService.deleteMaterial(materialId, userId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
