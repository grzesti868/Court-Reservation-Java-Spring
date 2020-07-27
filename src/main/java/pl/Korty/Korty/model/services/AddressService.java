package pl.Korty.Korty.model.services;

import org.springframework.stereotype.Service;
import pl.Korty.Korty.model.entities.AddressesEntity;
import pl.Korty.Korty.model.entities.UsersEntity;
import pl.Korty.Korty.model.repositories.AddressRepository;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.model.responses.UserRestModel;

import java.util.List;
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
        return addressRepository.save(mapRestModel(address)).getId();
    }

    public AddressRestModel update(final Long id, final AddressRestModel address) {

        AddressesEntity addressToUpdate = addressRepository.findById(id).get();

        addressToUpdate.setStreet(address.getStreet());
        addressToUpdate.setApartment_num(address.getApartment_num());
        addressToUpdate.setBuilding_num(address.getBuilding_num());
        addressToUpdate.setCity(address.getCity());
        addressToUpdate.setPostal_code(address.getPostal_code());
        addressToUpdate.setCountry(address.getCountry());

        return new AddressRestModel(addressRepository.save(addressToUpdate));
    }

    public void deleteByID(final long id){
        addressRepository.deleteById(id);
    }

    private AddressesEntity mapRestModel(final AddressRestModel model) {
        return new AddressesEntity(model.getStreet(), model.getBuilding_num(), model.getApartment_num(), model.getCity(), model.getPostal_code(), model.getCountry());
    }

    public AddressRestModel getById(final Long id) {
        return new AddressRestModel(addressRepository.getOne(id));
    }
}
