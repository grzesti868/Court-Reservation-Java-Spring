package pl.Korty.Korty.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static pl.Korty.Korty.model.enums.ApplicationUserRole.ADMIN;
import static pl.Korty.Korty.model.enums.ApplicationUserRole.GUEST;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig  extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/reservations","/users")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        final UserDetails admin =
                User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin123"))
                        //.roles(ADMIN.name()) //ROLE_ADMIN
                        .authorities(ADMIN.getGratedAuthorities())
                        .build();

        final UserDetails guest =
                User.builder()
                        .username("gregvader")
                        .password(passwordEncoder.encode("gregvader"))
                        //.roles(GUEST.name()) //ROLE_GUEST
                        .authorities(GUEST.getGratedAuthorities())
                        .build();

        return new InMemoryUserDetailsManager(admin,guest);
    }
}
