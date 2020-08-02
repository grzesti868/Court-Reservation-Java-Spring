package pl.Korty.Korty.model.services;

import org.springframework.stereotype.Service;
import pl.Korty.Korty.model.entities.AddressesEntity;
import pl.Korty.Korty.model.entities.UsersEntity;
import pl.Korty.Korty.model.repositories.AddressRepository;
import pl.Korty.Korty.model.repositories.UserRepository;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.model.responses.UserRestModel;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    public UserService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
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
        userToUpdate.setStatus(user.getStatus());
        //userToUpdate.setAddressesEntity(user.getAddressRestModel());
        //userToUpdate.setReservationsEntity(user.getReservationsEntity());

        return new UserRestModel(userRepository.save(userToUpdate));
    }

    public void deleteByID(final long id){
        userRepository.deleteById(id);
    }

    public UserRestModel getByLogin(final String login) {
        return new UserRestModel(userRepository.findByLogin(login));
    }


    private UsersEntity mapRestModel(final UserRestModel model) {
        UsersEntity userToAdd = new UsersEntity(model.getLogin(), model.getPassword(), model.getEmail(), model.getFirstname(), model.getLastname(), model.getSex(), model.getStatus());
        AddressesEntity addressOfUser = new AddressesEntity(
                model.getAddressRestModel().getStreet(),
                model.getAddressRestModel().getBuilding_num(),
                model.getAddressRestModel().getApartment_num(),
                model.getAddressRestModel().getCity(),
                model.getAddressRestModel().getPostal_code(),
                model.getAddressRestModel().getCountry());

        userToAdd.setAddressesEntity(addressOfUser);
        return userToAdd;
    }


}
