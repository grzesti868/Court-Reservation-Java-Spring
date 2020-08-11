package pl.Korty.Korty.model.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.Korty.Korty.model.entities.AddressesEntity;
import pl.Korty.Korty.model.entities.UsersEntity;
import pl.Korty.Korty.model.repositories.AddressRepository;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.model.responses.UserRestModel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<AddressRestModel> getAll(){
        return addressRepository.findAll().stream()
                .map(AddressRestModel::new)
                .collect(Collectors.toList());
    }

    public Long add(final AddressRestModel address) {
        Optional<AddressRestModel> newAddress = Optional.ofNullable(address);
        if(newAddress.isPresent())
        return addressRepository.save(mapRestModel(address)).getId();
        else
            return  Long.valueOf(-1);
    }

    public AddressRestModel update(final Long id, final AddressRestModel address) {

        Optional<AddressRestModel> updateAddress = Optional.ofNullable(address);

        if(updateAddress.isPresent() && id > 0){
            Optional<AddressesEntity> addressToUpdate = Optional.ofNullable(addressRepository.findById(id).orElse(null));
            if(addressToUpdate.isPresent()){
                addressToUpdate.get().setStreet(updateAddress.get().getStreet());
                addressToUpdate.get().setApartment_num(updateAddress.get().getApartment_num());
                addressToUpdate.get().setBuilding_num(updateAddress.get().getBuilding_num());
                addressToUpdate.get().setCity(updateAddress.get().getCity());
                addressToUpdate.get().setPostal_code(updateAddress.get().getPostal_code());
                addressToUpdate.get().setCountry(updateAddress.get().getCountry());
                return new AddressRestModel(addressRepository.save(addressToUpdate.get()));
            } else {
                return null;
            }
        }
        else
            return null;
    }

    public Boolean deleteByID(final long id){
        try{
            addressRepository.deleteById(id);
            return Boolean.TRUE;
        }
        catch (EmptyResultDataAccessException e)
        {
            return Boolean.FALSE;
        }

    }

    public AddressRestModel getById(final Long id) {

            Optional<AddressesEntity> address = Optional.ofNullable(addressRepository.findById(id).orElse(null));

        if(address.isPresent())
            return new AddressRestModel(address.get());
        else
            return null;
    }

    private AddressesEntity mapRestModel(final AddressRestModel model) {
        return new AddressesEntity(model.getStreet(), model.getBuilding_num(), model.getApartment_num(), model.getCity(), model.getPostal_code(), model.getCountry());
    }
}
