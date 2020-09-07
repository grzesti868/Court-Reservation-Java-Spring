package pl.Korty.Korty.services;


import org.springframework.stereotype.Service;
import pl.Korty.Korty.exception.ApiNotFoundException;
import pl.Korty.Korty.exception.ApiRequestException;
import pl.Korty.Korty.model.entities.AddressesEntity;
import pl.Korty.Korty.model.entities.Squash_CourtsEntity;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.model.responses.Squash_CourtRestModel;
import pl.Korty.Korty.repositories.AddressRepository;
import pl.Korty.Korty.repositories.Squash_CourtsRepository;

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

        if(newCourt.isEmpty())
           throw new ApiRequestException("New court can not be empty");

        else if(Optional.ofNullable(newCourt.get().getAddressRestModel()).isEmpty())
                throw new ApiRequestException("Address of new court can not be empty");

            else
                return squash_courtsRepository.save(mapRestModel(court)).getId();
    }

    public Squash_CourtRestModel update(final Long id, final Squash_CourtRestModel court) {

        Optional<Squash_CourtRestModel> updateCourt = Optional.ofNullable(Optional.ofNullable(court)
                .orElseThrow(() -> new ApiRequestException("Update court can not be empty")));

        Optional<Squash_CourtsEntity> courtToUpdate = Optional.ofNullable(squash_courtsRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(String.format("Court by id %d to update does not exist",id))));




        Optional<AddressRestModel> courtAddress = Optional.ofNullable(Optional.ofNullable(court.getAddressRestModel())
                    .orElseThrow(() -> new ApiRequestException("Address of updated court can not be empty")));

        courtToUpdate.get().setFields_num(updateCourt.get().getFields_num());

        AddressesEntity updateCourtAddress = addressRepository.findById(courtToUpdate.get().getSquashCourtAddress().getId()).get();
        updateCourtAddress.setStreet(courtAddress.get().getStreet());
        updateCourtAddress.setBuilding_num(courtAddress.get().getBuilding_num());
        updateCourtAddress.setApartment_num(courtAddress.get().getApartment_num());
        updateCourtAddress.setCity(courtAddress.get().getCity());
        updateCourtAddress.setPostal_code(courtAddress.get().getPostal_code());
        updateCourtAddress.setCountry(courtAddress.get().getCountry());

        addressRepository.save(updateCourtAddress);



        return new Squash_CourtRestModel(squash_courtsRepository.save(courtToUpdate.get()));


    }

    public void deleteByID(final long id){

        if(squash_courtsRepository.existsById(id))
            squash_courtsRepository.deleteById(id);
        else
            throw new ApiNotFoundException(String.format("Court by id %d does not exists",id));
    }

    public Squash_CourtRestModel getById(final Long id) {

        Optional<Squash_CourtsEntity> court = squash_courtsRepository.findById(id);

        return court.map(Squash_CourtRestModel::new)
                .orElseThrow(() -> new ApiNotFoundException(String.format("Court by id %d was not found.",id)));
    }


    public Squash_CourtRestModel getByAddressId(Long addressId){

        Optional<Squash_CourtsEntity> court = Optional.ofNullable(squash_courtsRepository.findBySquashCourtAddressId(addressId));

            return court.map(Squash_CourtRestModel::new)
                    .orElseThrow(()->new ApiNotFoundException(String.format("No court was found by address id: %d.",addressId)));
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
