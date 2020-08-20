package pl.Korty.Korty.model.services;


import org.springframework.stereotype.Service;
import pl.Korty.Korty.model.entities.AddressesEntity;
import pl.Korty.Korty.model.entities.Squash_CourtsEntity;
import pl.Korty.Korty.model.repositories.AddressRepository;
import pl.Korty.Korty.model.repositories.Squash_CourtsRepository;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.model.responses.Squash_CourtRestModel;

import java.util.List;
import java.util.Optional;
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
        Optional<Squash_CourtRestModel> newCourt = Optional.ofNullable(court);
        if(newCourt.isEmpty()){
            return -1L; //new court is empty
        } else {
            if(Optional.ofNullable(newCourt.get().getAddressRestModel()).isEmpty()){
                return -2L; //address court is empty
            }else{
                return squash_courtsRepository.save(mapRestModel(court)).getId();
            }
        }
    }

    public Squash_CourtRestModel update(final Long id, final Squash_CourtRestModel court) {

        Optional<Squash_CourtsEntity> courtToUpdate = squash_courtsRepository.findById(id);
        if(courtToUpdate.isPresent()){

            courtToUpdate.get().setFields_num(court.getFields_num());
            Optional<AddressRestModel> courtAddress = Optional.ofNullable(court.getAddressRestModel());

            if(courtAddress.isPresent()){
                AddressesEntity updateCourtAddress = addressRepository.findById(courtToUpdate.get().getSquashCourtAddress().getId()).get();
                updateCourtAddress.setStreet(court.getAddressRestModel().getStreet());
                updateCourtAddress.setBuilding_num(court.getAddressRestModel().getBuilding_num());
                updateCourtAddress.setApartment_num(court.getAddressRestModel().getApartment_num());
                updateCourtAddress.setCity(court.getAddressRestModel().getCity());
                updateCourtAddress.setPostal_code(court.getAddressRestModel().getPostal_code());
                updateCourtAddress.setCountry(court.getAddressRestModel().getCountry());

                addressRepository.save(updateCourtAddress);

            }

            return new Squash_CourtRestModel(squash_courtsRepository.save(courtToUpdate.get()));
        }else
            return null;

    }

    public Boolean deleteByID(final long id){
        if(squash_courtsRepository.existsById(id)){
            squash_courtsRepository.deleteById(id);
            return Boolean.TRUE;
        }
        else
            return Boolean.FALSE;
    }

    public Squash_CourtRestModel getById(final Long id) {
        if(squash_courtsRepository.existsById(id))
        return new Squash_CourtRestModel(squash_courtsRepository.getOne(id));
        else
            return null;
    }


    public Squash_CourtRestModel getByAddressId(Long addressId){
        if(addressRepository.existsById(addressId))
            return new Squash_CourtRestModel(squash_courtsRepository.getOne(addressId));
        else
            return null;
    }

    private Squash_CourtsEntity mapRestModel(final Squash_CourtRestModel model) {

        Squash_CourtsEntity squash_courtToAdd = new Squash_CourtsEntity(model.getFields_num());

        AddressesEntity addressOfSquash_Court = new AddressesEntity(
                model.getAddressRestModel().getStreet(),
                model.getAddressRestModel().getBuilding_num(),
                model.getAddressRestModel().getApartment_num(),
                model.getAddressRestModel().getCity(),
                model.getAddressRestModel().getPostal_code(),
                model.getAddressRestModel().getCountry());

        squash_courtToAdd.setSquashCourtAddress(addressOfSquash_Court);
        return squash_courtToAdd;
    }



}
