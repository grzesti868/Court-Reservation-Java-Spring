package pl.Korty.Korty.services;

import org.springframework.stereotype.Service;
import pl.Korty.Korty.exception.ApiNotFoundException;
import pl.Korty.Korty.exception.ApiRequestException;
import pl.Korty.Korty.model.entities.AddressesEntity;
import pl.Korty.Korty.model.entities.ReservationsEntity;
import pl.Korty.Korty.model.entities.UsersEntity;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.model.responses.ReservationRestModel;
import pl.Korty.Korty.model.responses.UserRestModel;
import pl.Korty.Korty.repositories.ReservationRepository;
import pl.Korty.Korty.repositories.Squash_CourtsRepository;
import pl.Korty.Korty.repositories.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    final private ReservationRepository reservationRepository;

    final private UserRepository userRepository;

    final private Squash_CourtsRepository squash_courtsRepository;

    public ReservationService(ReservationRepository reservationService, UserRepository userRepository, Squash_CourtsRepository squash_courtsRepository) {
        this.reservationRepository = reservationService;
        this.userRepository = userRepository;
        this.squash_courtsRepository = squash_courtsRepository;
    }

    public List<ReservationRestModel> getAll(){

        return reservationRepository.findAll().stream()
                .map(ReservationRestModel::new)
                .collect(Collectors.toList());
    }

    public List<ReservationRestModel> getAllByUserUsername(final String username){


        if(!userRepository.existsByUsername(username))
            throw new ApiNotFoundException(String.format("No user found by %s username",username));

        return reservationRepository.findAllByReservationUserUsername(username).stream()
                .map(ReservationRestModel::new)
                .collect(Collectors.toList());

    }

    public List<ReservationRestModel> getAllByCourtId(final Long id){

        if(!squash_courtsRepository.existsById(id))
            throw new ApiNotFoundException(String.format("No court found by id: %d",id));

        return reservationRepository.findAllByReservationSquashCourtId(id).stream()
                .map(ReservationRestModel::new)
                .collect(Collectors.toList());
    }

    public Long add(final ReservationRestModel reservationRestModel) {

        Optional<ReservationRestModel> reservationToAdd = Optional.ofNullable(Optional.ofNullable(reservationRestModel)
                .orElseThrow(() -> new ApiRequestException("New reservation can not be empty")));


        if(!squash_courtsRepository.existsById(reservationToAdd.get().getCourtId()))
                throw new ApiRequestException("No court found by this id");

        Optional<UserRestModel> reservationUser = Optional.ofNullable(reservationToAdd.get().getUserRestModel());
        Optional<String> userUsername = Optional.ofNullable(reservationToAdd.get().getUserUsername());

        if(reservationUser.isEmpty() && userUsername.isEmpty())
            throw new ApiRequestException("No information about user.");

        Optional<ReservationsEntity> mappedReservation = Optional.ofNullable(mapReservationRestModel(reservationToAdd.get()));

        if(mappedReservation.isEmpty())
            throw new ApiRequestException("Wrong user's information or invalid period of time");

        return reservationRepository.save(mappedReservation.get()).getId();

    }

    public ReservationRestModel update(final Long id, ReservationRestModel reservation)
    {
        Optional<ReservationRestModel> reservationRestModel = Optional.ofNullable(Optional.ofNullable(reservation)
                .orElseThrow(() -> new ApiRequestException("Updated reservation can not be null")));


        Optional <ReservationsEntity> reservationToUpdate = Optional.ofNullable(reservationRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException("Reservation to update does not exists")));

        if(!isTimeValid(reservation.getStart_date(),reservation.getEnd_date(),reservation.getCourtId()))
                throw new ApiRequestException("No available courts in this period of time.");

        reservationToUpdate.get().setStart_date(reservationRestModel.get().getStart_date());
        reservationToUpdate.get().setEnd_date(reservationRestModel.get().getEnd_date());

        reservationToUpdate.get().setPeople_num(reservationRestModel.get().getPeople_num());
        reservationToUpdate.get().setAdditional_info(reservationRestModel.get().getAdditional_info());


        return new ReservationRestModel(reservationRepository.save(reservationToUpdate.get()));
    }

    public void deleteById(final long id){
        if(reservationRepository.existsById(id))
            reservationRepository.deleteById(id);
        else
            throw new ApiNotFoundException(String.format("Reservation by id %d does not exists",id));
    }

    public ReservationRestModel getById(final Long id) {

        Optional<ReservationsEntity> reservation = reservationRepository.findById(id);

        return reservation.map(ReservationRestModel::new)
                .orElseThrow(() -> new ApiNotFoundException(String.format("Reservation by id %d does not exists",id)));
    }

    private ReservationsEntity mapReservationRestModel(final ReservationRestModel model) {

        if(!isTimeValid(model.getStart_date(),model.getEnd_date(),model.getCourtId()))
            return null;//can't book in this period of time

        ReservationsEntity reservationsEntity = new ReservationsEntity(model.getStart_date(),model.getEnd_date(),model.getPeople_num(), model.getAdditional_info());

        reservationsEntity.setReservationSquashCourt(squash_courtsRepository.findById(model.getCourtId()).get());

        Optional<String> reservationUser = Optional.ofNullable(model.getUserUsername());
        if(reservationUser.isEmpty()) {
            Optional<AddressRestModel> userAddress = Optional.ofNullable(model.getUserRestModel().getAddressRestModel());
            UsersEntity newUser = mapUserRestModel(model.getUserRestModel());
            if(userAddress.isEmpty()){

                return null; //no address id
            }
            if(userRepository.existsByUsername(newUser.getUsername())){
                return null; //login is already existing in db
            }
            reservationsEntity.setReservationUser(newUser);
        } else {
            Optional<UsersEntity> oldUser = Optional.ofNullable(userRepository.findByUsername(reservationUser.get()));
            if(oldUser.isEmpty()){
                return null; //no user find
            }
            reservationsEntity.setReservationUser(oldUser.get());
        }

        return reservationsEntity;
    }

    private UsersEntity mapUserRestModel(final UserRestModel model){
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

    private Boolean isTimeValid(Date start, Date end, Long idCourt) {



        List<ReservationsEntity> reservations = reservationRepository.findAllByReservationSquashCourtId(idCourt).stream()
                .filter(r -> start.before(r.getEnd_date()) && r.getStart_date().before(end))
                .collect(Collectors.toList());

        if(reservations.size()< squash_courtsRepository.findById(idCourt).get().getFields_num()){
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }

    }


}
