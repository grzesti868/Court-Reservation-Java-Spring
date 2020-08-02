package pl.Korty.Korty.model.services;


import org.springframework.stereotype.Service;
import pl.Korty.Korty.model.entities.AddressesEntity;
import pl.Korty.Korty.model.entities.Squash_CourtsEntity;
import pl.Korty.Korty.model.entities.UsersEntity;
import pl.Korty.Korty.model.repositories.AddressRepository;
import pl.Korty.Korty.model.repositories.Squash_CourtsRepository;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.model.responses.ReservationRestModel;
import pl.Korty.Korty.model.responses.Squash_CourtRestModel;
import pl.Korty.Korty.model.responses.UserRestModel;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Squash_CourtService {
    final private Squash_CourtsRepository squash_courtsRepository;

    final private AddressRepository addressRepository;

    public Squash_CourtService(Squash_CourtsRepository squash_courtsRepository, AddressRepository addressRepository) {
        this.squash_courtsRepository = squash_courtsRepository;
        this.addressRepository = addressRepository;
    }

    public List<Squash_CourtRestModel> getAll(){
        return squash_courtsRepository.findAll().stream()
                .map(Squash_CourtRestModel::new)
                .collect(Collectors.toList());
    }

    public Long add(final Squash_CourtRestModel court) {
        return squash_courtsRepository.save(mapRestModel(court)).getId();
    }

    public Squash_CourtRestModel update(final Long id, final Squash_CourtRestModel court) {

        Squash_CourtsEntity courtToUpdate = squash_courtsRepository.findById(id).get();

        AddressesEntity addressOfCourt = new AddressesEntity(
                court.getAddressRestModel().getStreet(),
                court.getAddressRestModel().getBuilding_num(),
                court.getAddressRestModel().getApartment_num(),
                court.getAddressRestModel().getCity(),
                court.getAddressRestModel().getPostal_code(),
                court.getAddressRestModel().getCountry());

        courtToUpdate.setSquashCourtAddress(addressOfCourt);
        courtToUpdate.setFields_num(court.getFields_num());

        return new Squash_CourtRestModel(squash_courtsRepository.save(courtToUpdate));
    }

    public void deleteByID(final long id){
        squash_courtsRepository.deleteById(id);
    }

    public Squash_CourtRestModel getById(final Long id) {
        return new Squash_CourtRestModel(squash_courtsRepository.getOne(id));
    }


    public Squash_CourtsEntity getByAddressId(Long addressId){
        return squash_courtsRepository.findBySquashCourtAddressId(addressId);
    }

    private Squash_CourtsEntity mapRestModel(final Squash_CourtRestModel court) {
        Squash_CourtsEntity squash_courtToAdd = new Squash_CourtsEntity(court.getFields_num());

        AddressesEntity addressOfSquash_Court = new AddressesEntity(
                court.getAddressRestModel().getStreet(),
                court.getAddressRestModel().getBuilding_num(),
                court.getAddressRestModel().getApartment_num(),
                court.getAddressRestModel().getCity(),
                court.getAddressRestModel().getPostal_code(),
                court.getAddressRestModel().getCountry());

        squash_courtToAdd.setSquashCourtAddress(addressOfSquash_Court);
        return squash_courtToAdd;
    }



}
