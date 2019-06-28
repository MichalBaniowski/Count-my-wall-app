package pl.michal_baniowski.coutmywall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.michal_baniowski.coutmywall.entity.MaterialCategory;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface MaterialCategoryRepository extends JpaRepository<MaterialCategory, Long> {
    @Query("SELECT m FROM MaterialCategory m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%',:name, '%'))")
    List<MaterialCategory> findByNameLike(@Param("name")String name);
    MaterialCategory findByName(String name);
}
