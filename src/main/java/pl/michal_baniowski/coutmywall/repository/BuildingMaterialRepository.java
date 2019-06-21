package pl.michal_baniowski.coutmywall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.michal_baniowski.coutmywall.entity.BuildingMaterial;
import pl.michal_baniowski.coutmywall.entity.MaterialCategory;

import java.util.List;

@Repository
public interface BuildingMaterialRepository extends JpaRepository<BuildingMaterial, Long> {
    List<BuildingMaterial> findAllByName(String name);
    List<BuildingMaterial> findAllByCategories(MaterialCategory category);
}
