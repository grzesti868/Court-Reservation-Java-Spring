package pl.Korty.Korty.model.services;

import org.springframework.stereotype.Service;
import pl.Korty.Korty.model.entities.AddressesEntity;
import pl.Korty.Korty.model.entities.ReservationsEntity;
import pl.Korty.Korty.model.entities.UsersEntity;
import pl.Korty.Korty.model.repositories.ReservationRepository;
import pl.Korty.Korty.model.repositories.Squash_CourtsRepository;
import pl.Korty.Korty.model.repositories.UserRepository;
import pl.Korty.Korty.model.responses.ReservationRestModel;
import pl.Korty.Korty.model.responses.UserRestModel;

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

    public List<ReservationRestModel> getAllByUserId(final Long id){
        if(userRepository.existsById(id))
        return reservationRepository.findAllByReservationUserId(id).stream()
                .map(ReservationRestModel::new)
                .collect(Collectors.toList());
        else
            return null;
    }

    public List<ReservationRestModel> getAllByCourtId(final Long id){
        if(squash_courtsRepository.existsById(id))
        return reservationRepository.findAllByReservationSquashCourtId(id).stream()
                .map(ReservationRestModel::new)
                .collect(Collectors.toList());
        else
            return null;
    }

    public Long add(final ReservationRestModel reservationRestModel) {//TODO: fix this
        return reservationRepository.save(mapReservationRestModel(reservationRestModel)).getId();
    }

    public ReservationRestModel update(final Long id, ReservationRestModel reservation) //TODO: check of reservation is correct
    {
        Optional<ReservationRestModel> reservationRestModel = Optional.ofNullable(reservation);
        if(reservationRepository.existsById(id) && reservationRestModel.isPresent()){
            Optional <ReservationsEntity> reservationToUpdate = reservationRepository.findById(id);

            reservationToUpdate.get().setStart_date(reservationRestModel.get().getStart_date());
            reservationToUpdate.get().setEnd_date(reservationRestModel.get().getEnd_date());
            reservationToUpdate.get().setPeople_num(reservationRestModel.get().getPeople_num());
            reservationToUpdate.get().setAdditional_info(reservationRestModel.get().getAdditional_info());


            return new ReservationRestModel(reservationRepository.save(reservationToUpdate.get()));
        }
        else
            return null;
    }

    public Boolean deleteByID(final long id){
        if(reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return Boolean.TRUE;
        }
        else
            return  Boolean.FALSE;
    }

    public ReservationRestModel getById(final Long id) {
        if(reservationRepository.existsById(id))
        return new ReservationRestModel(reservationRepository.getOne(id));
        else
            return null;
    }

    private ReservationsEntity mapReservationRestModel(final ReservationRestModel model) { //TODO: fix this, zakladam ze wszyskto jest ok, logika w "add"
        System.out.println("MODEL REZERWACJI:"+ model.toString());

        ReservationsEntity reservationsEntity = new ReservationsEntity(model.getStart_date(),model.getEnd_date(),model.getPeople_num(), model.getAdditional_info());

        reservationsEntity.setReservationSquashCourt(squash_courtsRepository.findById(model.getCourtId()).get());

        System.out.println("BAZOWA ENCJA:"+reservationsEntity.toString());

        Optional<String> reservationUser = Optional.ofNullable(model.getUserLogin());

        if(reservationUser.isEmpty()) {
            UsersEntity newUser = mapUserRestModel(model.getUserRestModel());
            System.out.println("NOWY USER:"+newUser.toString());
            reservationsEntity.setReservationUser(newUser);
        } else {

            UsersEntity oldUser = userRepository.findByLogin(model.getUserLogin());
            System.out.println("OLD USER:"+oldUser.toString());
            reservationsEntity.setReservationUser(oldUser);
        }


        System.out.println(reservationsEntity.toString());
        return reservationsEntity;
    }

    private UsersEntity mapUserRestModel(final UserRestModel model){ //TODO: get rid of this?
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
