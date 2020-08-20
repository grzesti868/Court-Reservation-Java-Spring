package pl.Korty.Korty.model.services;

import org.springframework.stereotype.Service;
import pl.Korty.Korty.model.entities.AddressesEntity;
import pl.Korty.Korty.model.entities.UsersEntity;
import pl.Korty.Korty.model.repositories.AddressRepository;
import pl.Korty.Korty.model.repositories.UserRepository;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.model.responses.UserRestModel;

import java.util.List;
import java.util.Optional;
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
        Optional<UserRestModel> newUser = Optional.ofNullable(user);
        if(newUser.isPresent()){
            if(!userRepository.existsByLogin(newUser.get().getLogin()))
            {
                Optional<AddressRestModel> hasAddress = Optional.ofNullable(newUser.get().getAddressRestModel());
                if(hasAddress.isPresent())
                    return userRepository.save(mapRestModel(user)).getId();
                else
                    return -1L;
            }
            else
                return -2L;
        }
        else
            return -3L;
    }

    public UserRestModel update(final String login, final UserRestModel user) {

        Optional<UserRestModel> updateUser = Optional.ofNullable(user);

        if(updateUser.isPresent() && userRepository.existsByLogin(login))
        {
            Optional<UsersEntity> userToUpdate =Optional.ofNullable(userRepository.findByLogin(login));
            if(userToUpdate.isPresent())
            {
                if(!userRepository.existsByLogin(updateUser.get().getLogin()))
                    userToUpdate.get().setLogin(updateUser.get().getLogin());

                userToUpdate.get().setEmail(updateUser.get().getEmail());
                userToUpdate.get().setFirstname(updateUser.get().getFirstname());
                userToUpdate.get().setLastname(updateUser.get().getLastname());
                userToUpdate.get().setPassword(updateUser.get().getPassword());
                userToUpdate.get().setSex(updateUser.get().getSex());
                userToUpdate.get().setStatus(updateUser.get().getStatus());

                Optional<AddressRestModel> updateAddress = Optional.ofNullable(updateUser.get().getAddressRestModel());
                AddressesEntity updateUserAddress;
                if(updateAddress.isPresent())
                {
                    updateUserAddress = userToUpdate.get().getAddressesEntity();
                    updateUserAddress.setStreet(updateAddress.get().getStreet());
                    updateUserAddress.setBuilding_num(updateAddress.get().getBuilding_num());
                    updateUserAddress.setApartment_num(updateAddress.get().getApartment_num());
                    updateUserAddress.setCity(updateAddress.get().getCity());
                    updateUserAddress.setPostal_code(updateAddress.get().getPostal_code());
                    updateUserAddress.setCountry(updateAddress.get().getCountry());
                }
                else
                {
                    updateUserAddress = userToUpdate.get().getAddressesEntity();
                }

                return new UserRestModel(userRepository.save(userToUpdate.get()));

            } else
                return null;


        } else
            return null;
    }

    public Boolean deleteByLogin(final String login){
        if(userRepository.existsByLogin(login)){
            userRepository.deleteByLogin(login);
            return Boolean.TRUE;
        }
        else
            return Boolean.FALSE;
    }

    public UserRestModel getByLogin(final String login) {

        Optional<UsersEntity> user = Optional.ofNullable(userRepository.findByLogin(login));
        if(user.isPresent())
        return new UserRestModel(user.get());
        return null;
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
