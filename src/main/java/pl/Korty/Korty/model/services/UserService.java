package pl.Korty.Korty.model.services;

import org.springframework.stereotype.Service;
import pl.Korty.Korty.model.entities.UsersEntity;
import pl.Korty.Korty.model.enums.SexEnum;
import pl.Korty.Korty.model.enums.StatusEnum;
import pl.Korty.Korty.model.repositories.UserRepository;
import pl.Korty.Korty.model.responses.UserRestModel;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserRestModel> getAll(){
        return userRepository.findAll().stream()
                .map(UserRestModel::new)
                .collect(Collectors.toList());
    }

    public Long add(final UserRestModel user) {
        return userRepository.save(mapRestModel(user)).getId();
    }

    private UsersEntity mapRestModel(final UserRestModel model) {
       return new UsersEntity(model.getLogin(), model.getPassword(), model.getEmail(), model.getFirstname(), model.getLastname(), model.getSex(), model.getId_address(),  model.getStatus());
    }

    public UserRestModel getByLogin(final String login) {
        return new UserRestModel(userRepository.getByLogin(login));
    }

}
