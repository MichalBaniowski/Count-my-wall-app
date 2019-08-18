package pl.michal_baniowski.coutmywall.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.michal_baniowski.coutmywall.entity.auth.LoggedUser;
import pl.michal_baniowski.coutmywall.entity.auth.Role;
import pl.michal_baniowski.coutmywall.entity.auth.User;

import java.util.HashSet;
import java.util.Set;

public class ApplicationUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for(Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new LoggedUser(user.getUsername(), user.getPassword(), grantedAuthorities, user);
    }
}
