package pl.michal_baniowski.coutmywall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.michal_baniowski.coutmywall.entity.BuildingMaterial;
import pl.michal_baniowski.coutmywall.entity.CompositeMaterial;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CompositeMaterialRepository extends JpaRepository<CompositeMaterial, Long> {
    List<CompositeMaterial> findAllByBuildingMaterial(BuildingMaterial buildingMaterial);
}
