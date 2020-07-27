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

    public UserRestModel update(final Long id, final UserRestModel user) {

        UsersEntity userToUpdate = userRepository.findById(id).get();
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setFirstname(user.getFirstname());
        userToUpdate.setLastname(user.getLastname());
        userToUpdate.setLogin(user.getLogin());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setSex(user.getSex());
        userToUpdate.setId_address(user.getId_address());
        userToUpdate.setStatus(user.getStatus());
        return new UserRestModel(userRepository.save(userToUpdate));
    }

    public void deleteByID(final long id){
        userRepository.deleteById(id);
    }

    private UsersEntity mapRestModel(final UserRestModel model) {
       return new UsersEntity(model.getLogin(), model.getPassword(), model.getEmail(), model.getFirstname(), model.getLastname(), model.getSex(), model.getId_address(),  model.getStatus());
    }

    public UserRestModel getByLogin(final String login) {
        return new UserRestModel(userRepository.getByLogin(login));
    }

}
