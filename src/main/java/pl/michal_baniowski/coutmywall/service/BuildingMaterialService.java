package pl.michal_baniowski.coutmywall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.michal_baniowski.coutmywall.dto.BuildingMaterialDto;
import pl.michal_baniowski.coutmywall.entity.BuildingMaterial;
import pl.michal_baniowski.coutmywall.entity.CompositeMaterial;
import pl.michal_baniowski.coutmywall.entity.MaterialCategory;
import pl.michal_baniowski.coutmywall.entity.auth.User;
import pl.michal_baniowski.coutmywall.exception.exception.AccessDeniedException;
import pl.michal_baniowski.coutmywall.exception.exception.FailedRepositoryOperationException;
import pl.michal_baniowski.coutmywall.exception.exception.NoEntityFound;
import pl.michal_baniowski.coutmywall.mapper.BuildingMaterialMapper;
import pl.michal_baniowski.coutmywall.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuildingMaterialService {
    private BuildingMaterialRepositoryApi materialRepository;
    private MaterialCategoryRepository categoryRepository;
    private CompositeMaterialRepository compositeMaterialRepository;
    private CompositeRepositoryApi compositeRepository;
    private UserRepository userRepository;
    private BuildingMaterialMapper mapper;

    @Autowired
    public BuildingMaterialService(BuildingMaterialRepositoryApi materialRepository,
                                   BuildingMaterialMapper mapper,
                                   MaterialCategoryRepository categoryRepository,
                                   UserRepository userRepository,
                                   CompositeMaterialRepository compositeMaterialRepository,
                                   CompositeRepositoryApi compositeRepository) {
        this.materialRepository = materialRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.compositeMaterialRepository = compositeMaterialRepository;
        this.compositeRepository = compositeRepository;
    }

    public List<BuildingMaterialDto> getAllDefaultBuildingMaterials() {
        return materialRepository.findAll().stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<BuildingMaterialDto> getAllDefaultMaterialsByCategory(String categoryName) {
        List<MaterialCategory> categories = categoryRepository.findByNameLike(categoryName);
        return materialRepository.findAllByCategories(categories).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<BuildingMaterialDto> getAllDefaultMaterialsByName(String name) {
        return materialRepository.findAllByName(name).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<BuildingMaterialDto> getAllMaterialsByName(String name, String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return materialRepository.findAllByName(name, user).stream()
                    .map(mapper::mapToDto)
                    .collect(Collectors.toList());
        }
        return getAllDefaultMaterialsByName(name);
    }

    public List<BuildingMaterialDto> getAllDefaultAndUsersMaterials(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return materialRepository.findAll(user).stream()
                    .map(mapper::mapToDto)
                    .collect(Collectors.toList());
        }
        return getAllDefaultBuildingMaterials();
    }

    public List<BuildingMaterialDto> getAllDefaultAndUsersMaterialsByCategory(String username, String categoryName) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            getAllDefaultMaterialsByCategory(categoryName);
        }
        List<MaterialCategory> categories = categoryRepository.findByNameLike(categoryName);
        User author = user;
        return materialRepository.findAllByCategories(categories, author).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    public void saveMaterial(BuildingMaterialDto buildingMaterialDto){
        BuildingMaterial buildingMaterial = mapper.mapToEntity(buildingMaterialDto);
        BuildingMaterial savedBuildingMaterial = materialRepository.saveMaterial(buildingMaterial);
        if (savedBuildingMaterial.getId() == null) {
            throw new FailedRepositoryOperationException("błąd zapisu");
        }
    }

    public void updateMaterial(BuildingMaterialDto buildingMaterialDto, Long materialId) {
        Optional<BuildingMaterial> optionalBuildingMaterial = materialRepository.findById(materialId);
        if(optionalBuildingMaterial.isPresent()){
            BuildingMaterial buildingMaterial = mapper.mapToEntity(buildingMaterialDto);
            BuildingMaterial foundBuildingMaterial = optionalBuildingMaterial.get();
            foundBuildingMaterial.setName(buildingMaterial.getName());
            foundBuildingMaterial.setAuthor(buildingMaterial.getAuthor());
            foundBuildingMaterial.setThermalConductivity(buildingMaterial.getThermalConductivity());
            foundBuildingMaterial.setSteamTransferCoefficient(buildingMaterial.getSteamTransferCoefficient());
            foundBuildingMaterial.setCategories(buildingMaterial.getCategories());
            materialRepository.saveMaterial(foundBuildingMaterial);
        } else {
            saveMaterial(buildingMaterialDto);
        }
    }

    public void deleteMaterial(Long materialId, String username) {
        Optional<BuildingMaterial> materialOptional = materialRepository.findById(materialId);
        if(!materialOptional.isPresent()) {
            throw new NoEntityFound("Nie ma takiego materiału w bazie");
        }
        User user = materialOptional.get().getAuthor();
        if(user == null || !user.getUsername().equals(username)) {
            throw new AccessDeniedException();
        }
        List<CompositeMaterial> compositeMaterials = compositeMaterialRepository.findAllByBuildingMaterial(materialOptional.get());
        compositeRepository.deleteByCompositeMaterials(compositeMaterials);
        compositeMaterialRepository.deleteAll(compositeMaterials);
        materialRepository.deleteMaterial(materialId);
        if (materialRepository.checkIfPresent(materialId)) {
            throw new FailedRepositoryOperationException("Nie udało się usunąć materiału");
        }
    }

    public BuildingMaterialDto getMaterialByMaterialId(String username, Long materialId) {
        Optional<BuildingMaterial> buildingMaterial = materialRepository.findById(materialId);
        if (!buildingMaterial.isPresent() || !buildingMaterial.get().getAuthor().getUsername().equals(username)){
            throw new AccessDeniedException();
        }
        return mapper.mapToDto(buildingMaterial.get());
    }
}
