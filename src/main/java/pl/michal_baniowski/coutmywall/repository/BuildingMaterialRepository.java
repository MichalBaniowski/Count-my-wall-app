package pl.michal_baniowski.coutmywall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.michal_baniowski.coutmywall.entity.BuildingMaterial;
import pl.michal_baniowski.coutmywall.entity.MaterialCategory;
import pl.michal_baniowski.coutmywall.entity.auth.User;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BuildingMaterialRepository extends JpaRepository<BuildingMaterial, Long> {
    List<BuildingMaterial> findAllByAuthorNull();
    List<BuildingMaterial> findAllByCategoriesInAndAuthorNull(List<MaterialCategory> categories);
    @Query("SELECT m FROM BuildingMaterial m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%',:name, '%')) AND m.author = NULL")
    List<BuildingMaterial> findAllByNameLikeIgnoreCaseAndAuthorNull(@Param("name") String name);
    List<BuildingMaterial> findAllByAuthorNullOrAuthor(User user);
    List<BuildingMaterial> findAllByCategoriesInAndAuthorNullOrCategoriesInAndAuthor(List<MaterialCategory> categories1, List<MaterialCategory> categories2, User author);
    @Query("SELECT m FROM BuildingMaterial m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%',:name, '%')) AND (m.author = NULL OR m.author = :author)")
    List<BuildingMaterial> findAllByNameLike(@Param("name")String name, @Param("author") User author);
}
