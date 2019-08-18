package pl.michal_baniowski.coutmywall.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.michal_baniowski.coutmywall.entity.auth.Role;
import pl.michal_baniowski.coutmywall.entity.auth.User;
import pl.michal_baniowski.coutmywall.repository.RoleRepository;
import pl.michal_baniowski.coutmywall.repository.UserRepository;

@Service
public class UserService {

    private final String DEFAULT_USER_ROLE = "ROLE_USER";
    private final String ADMIN_ROLE = "ROLE_ADMIN";
    private final String SUPER_ADMIN_ROLE = "ROLE_SUPER_ADMIN";

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean saveUser(User user, String roleName) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        User savedUser = userRepository.save(user);
        return savedUser.getId() != null;
    }

    public boolean saveUser(User user) {
        return saveUser(user, DEFAULT_USER_ROLE);
    }

    public  boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
