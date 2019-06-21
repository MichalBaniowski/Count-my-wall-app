package pl.michal_baniowski.coutmywall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.michal_baniowski.coutmywall.entity.CompositeType;

@Repository
public interface CompositeTypeRepository extends JpaRepository<CompositeType, Long> {
}
