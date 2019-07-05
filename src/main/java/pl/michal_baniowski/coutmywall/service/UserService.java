package pl.michal_baniowski.coutmywall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.michal_baniowski.coutmywall.entity.User;
import pl.michal_baniowski.coutmywall.entity.UserRole;
import pl.michal_baniowski.coutmywall.repository.UserRepository;
import pl.michal_baniowski.coutmywall.repository.UserRoleRepository;

@Service
public class UserService {
    private static final String DEFAULT_ROLE = "ROLE_USER";
    private UserRepository userRepository;
    private UserRoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean addWithDefaultRole(User user) {
        UserRole userRole = roleRepository.findByRole(DEFAULT_ROLE);
        user.getRoles().add(userRole);
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        User savedUser = userRepository.save(user);
        return  savedUser.getId() != null;
    }
}
