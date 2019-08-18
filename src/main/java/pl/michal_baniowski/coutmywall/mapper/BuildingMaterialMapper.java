package pl.michal_baniowski.coutmywall.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.michal_baniowski.coutmywall.dto.BuildingMaterialDto;
import pl.michal_baniowski.coutmywall.entity.BuildingMaterial;
import pl.michal_baniowski.coutmywall.entity.MaterialCategory;
import pl.michal_baniowski.coutmywall.entity.auth.User;
import pl.michal_baniowski.coutmywall.repository.MaterialCategoryRepository;
import pl.michal_baniowski.coutmywall.repository.UserRepository;

import java.util.stream.Collectors;

@Service
public class BuildingMaterialMapper implements DtoMapper<BuildingMaterial, BuildingMaterialDto>  {

    private MaterialCategoryRepository categoryRepository;
    private UserRepository userRepository;
    @Autowired
    public BuildingMaterialMapper(MaterialCategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BuildingMaterial mapToEntity(BuildingMaterialDto dtoObject) {
        BuildingMaterial buildingMaterial = new BuildingMaterial();
        buildingMaterial.setId(dtoObject.getId());
        buildingMaterial.setName(dtoObject.getName());
        buildingMaterial.setCategories(dtoObject.getMaterialCategoryNames().stream()
                .map(categoryRepository::findByName)
                .collect(Collectors.toList()));
        if(dtoObject.getAuthorName() != null){
            User user = userRepository.findByUsername(dtoObject.getAuthorName());
            buildingMaterial.setAuthor(user);
        }
        buildingMaterial.setSteamTransferCoefficient(dtoObject.getSteamTransferCoefficient());
        buildingMaterial.setThermalConductivity(dtoObject.getThermalConductivity());
        return buildingMaterial;
    }

    @Override
    public BuildingMaterialDto mapToDto(BuildingMaterial entityObject) {
        BuildingMaterialDto buildingMaterialDto = new BuildingMaterialDto();
        buildingMaterialDto.setId(entityObject.getId());
        buildingMaterialDto.setName(entityObject.getName());
        buildingMaterialDto.setMaterialCategoryNames(entityObject.getCategories().stream()
                .map(MaterialCategory::getName)
                .collect(Collectors.toList()));
        if(entityObject.getAuthor() != null){
            buildingMaterialDto.setAuthorName(entityObject.getAuthor().getUsername());
        }
        buildingMaterialDto.setSteamTransferCoefficient(entityObject.getSteamTransferCoefficient());
        buildingMaterialDto.setThermalConductivity(entityObject.getThermalConductivity());
        return buildingMaterialDto;
    }
}
