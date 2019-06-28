package pl.michal_baniowski.coutmywall.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.michal_baniowski.coutmywall.entity.BuildingMaterial;
import pl.michal_baniowski.coutmywall.entity.MaterialCategory;
import pl.michal_baniowski.coutmywall.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public class BuildingMaterialRepositoryApi {
    @Autowired
    private BuildingMaterialRepository buildingMaterialRepository;

    public List<BuildingMaterial> findAll() {
        return buildingMaterialRepository.findAllByAuthorNull();
    }
    public List<BuildingMaterial> findAllByCategories(List<MaterialCategory> categories){
        return buildingMaterialRepository.findAllByCategoriesInAndAuthorNull(categories);
    }
    public List<BuildingMaterial> findAllByName(String name) {
        return buildingMaterialRepository.findAllByNameLikeIgnoreCaseAndAuthorNull(name);
    }
    public List<BuildingMaterial> findAll(User user) {
        return buildingMaterialRepository.findAllByAuthorNullOrAuthor(user);
    }
    public List<BuildingMaterial> findAllByCategories(List<MaterialCategory> categories, User author) {
        return buildingMaterialRepository.findAllByCategoriesInAndAuthorNullOrCategoriesInAndAuthor(categories, categories, author);
    }
    public List<BuildingMaterial> findAllByName(String name, User author) {
        return buildingMaterialRepository.findAllByNameLike(name, author);
    }
    public Optional<BuildingMaterial> findById(Long id) {
        return buildingMaterialRepository.findById(id);
    }
    public BuildingMaterial saveMaterial(BuildingMaterial buildingMaterial) {
        return buildingMaterialRepository.save(buildingMaterial);
    }
    public void deleteMaterial (Long materialId) {
        buildingMaterialRepository.deleteById(materialId);
    }
    public boolean checkIfPresent(Long materialId) {
        return buildingMaterialRepository.existsById(materialId);
    }
}
