package pl.Korty.Korty.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static pl.Korty.Korty.model.enums.ApplicationUserRole.ADMIN;
import static pl.Korty.Korty.model.enums.ApplicationUserRole.GUEST;

@Repository("fake")
public class FakeApplicationUserDaoService implements  ApplicationUserDao{

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers(){ //todo: get users from db
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        "admin",
                        passwordEncoder.encode("admin123"),
                        ADMIN.getGrantedAuthorities(),
                        1L,
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "gregvader",
                        passwordEncoder.encode("gregvader"),
                        GUEST.getGrantedAuthorities(),
                        1L,
                        true,
                        true,
                        true,
                        true
                )
        );


        return applicationUsers;
    }
}
