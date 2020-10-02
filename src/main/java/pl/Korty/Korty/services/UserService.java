package pl.Korty.Korty.services;

import org.springframework.stereotype.Service;
import pl.Korty.Korty.exception.ApiNotFoundException;
import pl.Korty.Korty.exception.ApiRequestException;
import pl.Korty.Korty.model.entities.AddressesEntity;
import pl.Korty.Korty.model.entities.UsersEntity;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.model.responses.UserRestModel;
import pl.Korty.Korty.repositories.ReservationRepository;
import pl.Korty.Korty.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final ReservationRepository reservationRepository;
    public UserService(UserRepository userRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
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

        if(userRepository.existsByUsername(newUser.get().getUsername()))
            throw new ApiRequestException("User's username already exists.");

        Optional<AddressRestModel> hasAddress = Optional.ofNullable(newUser.get().getAddressRestModel());

        if(hasAddress.isEmpty())
            throw new ApiRequestException("User's address can not be empty.");

        return userRepository.save(mapRestModel(newUser.get())).getId();

    }

    public UserRestModel update(final String username, final UserRestModel user) {

        Optional<UserRestModel> updateUser = Optional.ofNullable(Optional.ofNullable(user)
                .orElseThrow(() -> new ApiRequestException("User can not be empty.")));

        Optional<UsersEntity> userToUpdate = Optional.ofNullable(Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new ApiRequestException("User to update does not exists")));

        if(userRepository.existsByUsername(username))
            userToUpdate.get().setUsername(updateUser.get().getUsername());

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

    public void deleteByUsername(final String username) {
        if (userRepository.existsByUsername(username))
            userRepository.deleteByUsername(username);
        else
            throw new ApiNotFoundException(String.format("User by username: %s does not exists", username));
    }

    public UserRestModel getByUsername(final String username) {

        Optional<UsersEntity> user = Optional.ofNullable(Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new ApiNotFoundException(String.format("User %s was not found.", username))));

        return new UserRestModel(user.get());
    }


    private UsersEntity mapRestModel(final UserRestModel model) {
        UsersEntity userToAdd = new UsersEntity(model.getUsername(), model.getPassword(), model.getEmail(), model.getFirstname(), model.getLastname(), model.getSex(), model.getStatus());
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
