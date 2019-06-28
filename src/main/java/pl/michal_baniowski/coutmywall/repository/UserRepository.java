package pl.michal_baniowski.coutmywall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.michal_baniowski.coutmywall.entity.User;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User>findByEmail(String email);
    Optional<User>findByUsername(String userName);
}
