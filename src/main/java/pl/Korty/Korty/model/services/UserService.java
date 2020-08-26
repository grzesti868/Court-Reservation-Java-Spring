package pl.Korty.Korty.model.services;

import org.springframework.stereotype.Service;
import pl.Korty.Korty.exception.ApiNotFoundException;
import pl.Korty.Korty.exception.ApiRequestException;
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

        if(newUser.isEmpty())
            throw new ApiRequestException("New user can not be empty.");

        if(userRepository.existsByLogin(newUser.get().getLogin()))
            throw new ApiRequestException("User's login already exists.");

        Optional<AddressRestModel> hasAddress = Optional.ofNullable(newUser.get().getAddressRestModel());

        if(hasAddress.isEmpty())
            throw new ApiRequestException("User's address can not be empty.");

        return userRepository.save(mapRestModel(newUser.get())).getId();

    }

    public UserRestModel update(final String login, final UserRestModel user) {

        Optional<UserRestModel> updateUser = Optional.ofNullable(Optional.ofNullable(user)
                .orElseThrow(() -> new ApiRequestException("User can not be empty.")));

        Optional<UsersEntity> userToUpdate = Optional.ofNullable(Optional.ofNullable(userRepository.findByLogin(login))
                .orElseThrow(() -> new ApiRequestException("User to update does not exists")));

        if(userRepository.existsByLogin(login))
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

    }

    public void deleteByLogin(final String login) {
        if (userRepository.existsByLogin(login))
            userRepository.deleteByLogin(login);
        else
            throw new ApiNotFoundException(String.format("User by login: %s does not exists", login));
    }

    public UserRestModel getByLogin(final String login) {

        Optional<UsersEntity> user = Optional.ofNullable(Optional.ofNullable(userRepository.findByLogin(login))
                .orElseThrow(() -> new ApiNotFoundException(String.format("User %s was not found.", login))));

        return new UserRestModel(user.get());
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
