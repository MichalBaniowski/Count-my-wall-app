package pl.michal_baniowski.coutmywall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.michal_baniowski.coutmywall.entity.Composite;
import pl.michal_baniowski.coutmywall.entity.CompositeMaterial;
import pl.michal_baniowski.coutmywall.entity.CompositeType;
import pl.michal_baniowski.coutmywall.entity.auth.User;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CompositeRepository extends JpaRepository<Composite, Long> {
    List<Composite> findAllByAuthorNull();
    List<Composite> findAllByCompositeTypeAndAuthorNull(CompositeType compositeType);
    @Query("SELECT c FROM Composite c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%',:name,'%')) AND c.author = NULL")
    List<Composite> findAllByNameLikeIgnoreCaseAndAuthorNull(@Param("name") String name);
    List<Composite> findAllByAuthorNullOrAuthor(User user);
    List<Composite> findAllByCompositeTypeAndAuthorNullOrCompositeTypeAndAuthor(CompositeType compositeType1, CompositeType compositeType2, User user);
    @Query("SELECT c FROM Composite c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%',:name,'%')) AND (c.author = NULL OR c.author = :author)")
    List<Composite> findAllByNameLike(@Param("name")String name, @Param("author") User author);
    void deleteByCompositeMaterialsIn(List<CompositeMaterial> compositeMaterials);
}
