package pl.Korty.Korty.model.services;

import org.springframework.dao.EmptyResultDataAccessException;
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
            Optional<AddressRestModel> hasAddress = Optional.ofNullable(newUser.get().getAddressRestModel());
            if(hasAddress.isPresent())
            return userRepository.save(mapRestModel(user)).getId();
            else
                return Long.valueOf(-1);
        }
        else
            return Long.valueOf(-1);
    }

    public UserRestModel update(final Long id, final UserRestModel user) {

        Optional<UserRestModel> updateUser = Optional.ofNullable(user);
        System.out.println("JEBŁO");

        if(updateUser.isPresent() && id>0)
        {
            Optional<UsersEntity> userToUpdate =Optional.ofNullable(userRepository.findById(id).orElse(null));
            if(userToUpdate.isPresent())
            {
                userToUpdate.get().setEmail(updateUser.get().getEmail());
                userToUpdate.get().setFirstname(updateUser.get().getFirstname());
                userToUpdate.get().setLastname(updateUser.get().getLastname());
                userToUpdate.get().setLogin(updateUser.get().getLogin());
                userToUpdate.get().setPassword(updateUser.get().getPassword());
                userToUpdate.get().setSex(updateUser.get().getSex());
                userToUpdate.get().setStatus(updateUser.get().getStatus());

                AddressesEntity updateUserAddress = userToUpdate.get().getAddressesEntity();

                updateUserAddress.setStreet(updateUser.get().getAddressRestModel().getStreet());
                updateUserAddress.setBuilding_num(updateUser.get().getAddressRestModel().getBuilding_num());
                updateUserAddress.setApartment_num(updateUser.get().getAddressRestModel().getApartment_num());
                updateUserAddress.setCity(updateUser.get().getAddressRestModel().getCity());
                updateUserAddress.setPostal_code(updateUser.get().getAddressRestModel().getPostal_code());
                updateUserAddress.setCountry(updateUser.get().getAddressRestModel().getCountry());


                addressRepository.save(updateUserAddress);
                return new UserRestModel(userRepository.save(userToUpdate.get()));

            } else
                return null;


        } else
            return null;
    }

    public Boolean deleteByID(final long id){
        try{
            userRepository.deleteById(id);;
            return Boolean.TRUE;
        }
        catch (EmptyResultDataAccessException e)
        {
            return Boolean.FALSE;
        }
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
