package pl.michal_baniowski.coutmywall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.michal_baniowski.coutmywall.entity.auth.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUsername(String userName);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
