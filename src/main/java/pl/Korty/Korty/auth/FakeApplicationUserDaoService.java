package pl.Korty.Korty.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import pl.Korty.Korty.model.enums.StatusEnum;
import pl.Korty.Korty.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.Korty.Korty.model.enums.ApplicationUserRole.ADMIN;
import static pl.Korty.Korty.model.enums.ApplicationUserRole.GUEST;

@Repository("fake")
public class FakeApplicationUserDaoService implements  ApplicationUserDao{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers(){
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        "admin",
                        passwordEncoder.encode("admin123"),
                        ADMIN.getGrantedAuthorities(),
                        UUID.randomUUID().getLeastSignificantBits(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "gregvader",
                        passwordEncoder.encode("gregvader"),
                        GUEST.getGrantedAuthorities(),
                        UUID.randomUUID().getLeastSignificantBits(),
                        true,
                        true,
                        true,
                        true
                )
        );

        List<ApplicationUser> users = userRepository.findAll().stream()
                .map(user -> new ApplicationUser(
                        user.getLogin(),
                        passwordEncoder.encode(passwordEncoder.encode(user.getPassword())),
                        GUEST.getGrantedAuthorities(),
                        user.getId(),
                        true,
                        true,
                        true,
                        user.getStatus().equals(StatusEnum.Active)))
                .collect(Collectors.toList());

        applicationUsers.addAll(users);

        return applicationUsers;
    }
}
