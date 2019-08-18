package pl.michal_baniowski.coutmywall.entity.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
public class LoggedUser extends User {

    private pl.michal_baniowski.coutmywall.entity.auth.User user;

    public LoggedUser(String username, String password,
                      Collection<? extends GrantedAuthority> authorities,
                      pl.michal_baniowski.coutmywall.entity.auth.User user) {
        super(username, password, authorities);
        this.user = user;
    }
}
