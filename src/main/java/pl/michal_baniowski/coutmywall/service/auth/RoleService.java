package pl.michal_baniowski.coutmywall.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.michal_baniowski.coutmywall.entity.auth.Role;
import pl.michal_baniowski.coutmywall.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role findByRole(String role) {
        return roleRepository.findByName(role);
    }
}
