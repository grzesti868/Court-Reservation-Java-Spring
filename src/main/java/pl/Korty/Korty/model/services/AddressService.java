package pl.Korty.Korty.model.services;

import org.springframework.stereotype.Service;
import pl.Korty.Korty.model.entities.AddressesEntity;
import pl.Korty.Korty.model.repositories.AddressRepository;
import pl.Korty.Korty.model.responses.AddressRestModel;

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
            return  -1L;
    }

    public AddressRestModel update(final Long id, final AddressRestModel address) {

        Optional<AddressRestModel> updateAddress = Optional.ofNullable(address);

        if(updateAddress.isPresent() && addressRepository.existsById(id)){
            Optional<AddressesEntity> addressToUpdate = addressRepository.findById(id);
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
        if(addressRepository.existsById(id)){
            addressRepository.deleteById(id);
            return Boolean.TRUE;
        }
        else
            return Boolean.FALSE;

    }

    public AddressRestModel getById(final Long id) {

            Optional<AddressesEntity> address = addressRepository.findById(id);

        return address.map(AddressRestModel::new).orElse(null);
    }

    private AddressesEntity mapRestModel(final AddressRestModel model) {
        return new AddressesEntity(model.getStreet(), model.getBuilding_num(), model.getApartment_num(), model.getCity(), model.getPostal_code(), model.getCountry());
    }
}
