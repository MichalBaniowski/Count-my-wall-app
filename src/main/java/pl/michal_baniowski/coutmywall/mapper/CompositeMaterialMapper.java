package pl.michal_baniowski.coutmywall.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.michal_baniowski.coutmywall.dto.CompositeMaterialDto;
import pl.michal_baniowski.coutmywall.entity.BuildingMaterial;
import pl.michal_baniowski.coutmywall.entity.CompositeMaterial;
import pl.michal_baniowski.coutmywall.repository.BuildingMaterialRepositoryApi;
import pl.michal_baniowski.coutmywall.service.CompositeMaterialService;

import java.util.Optional;

@Service
public class CompositeMaterialMapper implements DtoMapper<CompositeMaterial, CompositeMaterialDto> {

    private BuildingMaterialRepositoryApi materialRepository;
    private BuildingMaterialMapper buildingMaterialMapper;
    private CompositeMaterialService compositeMaterialService;

    @Autowired
    public CompositeMaterialMapper(BuildingMaterialRepositoryApi materialRepository,
                                   BuildingMaterialMapper buildingMaterialMapper,
                                   CompositeMaterialService compositeMaterialService) {
        this.materialRepository = materialRepository;
        this.buildingMaterialMapper = buildingMaterialMapper;
        this.compositeMaterialService = compositeMaterialService;
    }

    @Override
    public CompositeMaterial mapToEntity(CompositeMaterialDto dtoObject) {
        CompositeMaterial compositeMaterial = new CompositeMaterial();
        compositeMaterial.setId(dtoObject.getId());
        compositeMaterial.setThickness(dtoObject.getThickness());
        Optional<BuildingMaterial> buildingMaterial = materialRepository.findById(dtoObject.getBuildingMaterial().getId());
        if(buildingMaterial.isPresent()){
            compositeMaterial.setBuildingMaterial(buildingMaterial.get());
        }
        return compositeMaterial;
    }

    @Override
    public CompositeMaterialDto mapToDto(CompositeMaterial entityObject) {
        CompositeMaterialDto compositeMaterialDto = new CompositeMaterialDto();
        compositeMaterialDto.setId(entityObject.getId());
        compositeMaterialDto.setThickness(entityObject.getThickness());
        compositeMaterialDto.setBuildingMaterial(buildingMaterialMapper.mapToDto(entityObject.getBuildingMaterial()));
        compositeMaterialService.setMaterialHeatResistance(compositeMaterialDto);
        compositeMaterialService.setMaterialDiffusionResistance(compositeMaterialDto);
        return compositeMaterialDto;
    }
}
