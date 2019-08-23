package pl.michal_baniowski.coutmywall;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.michal_baniowski.coutmywall.service.auth.ApplicationUserDetailService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ApplicationUserDetailService applicationUserDetailService() {
        return new ApplicationUserDetailService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(applicationUserDetailService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers("/api/user/**").authenticated()
                .anyRequest().permitAll().and()
                .csrf().disable()
                .formLogin().disable();
    }
}
