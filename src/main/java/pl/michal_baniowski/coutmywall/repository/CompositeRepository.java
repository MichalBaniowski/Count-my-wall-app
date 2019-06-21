package pl.michal_baniowski.coutmywall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.michal_baniowski.coutmywall.entity.Composite;
import pl.michal_baniowski.coutmywall.entity.CompositeType;

import java.util.List;

@Repository
public interface CompositeRepository extends JpaRepository<Composite, Long> {
    List<Composite> findAllByName(String name);
    List<Composite> findAllByCompositeType(CompositeType compositeType);
}
