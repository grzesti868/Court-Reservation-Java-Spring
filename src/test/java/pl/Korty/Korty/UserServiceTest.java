package pl.Korty.Korty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.Korty.Korty.model.repositories.UserRepository;
import pl.Korty.Korty.model.services.UserService;

@SpringBootTest
@ActiveProfiles("h2")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;



}
