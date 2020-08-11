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
        return reservationRepository.findAllByReservationUserId(id).stream()
                .map(ReservationRestModel::new)
                .collect(Collectors.toList());
    }

    public List<ReservationRestModel> getAllByCourtId(final Long id){
        return reservationRepository.findAllByReservationSquashCourtId(id).stream()
                .map(ReservationRestModel::new)
                .collect(Collectors.toList());
    }

    public Long add(final ReservationRestModel reservationRestModel) {
        return reservationRepository.save(mapReservationRestModel(reservationRestModel)).getId();
    }

    public ReservationRestModel update(final Long id, ReservationRestModel reservation)
    {
        ReservationsEntity reservationToUpdate = reservationRepository.findById(id).get();

        reservationToUpdate.setStart_date(reservation.getStart_date());
        reservationToUpdate.setEnd_date(reservation.getEnd_date());
        reservationToUpdate.setPeople_num(reservation.getPeople_num());
        reservationToUpdate.setAdditional_info(reservation.getAdditional_info());


        return new ReservationRestModel(reservationRepository.save(reservationToUpdate));
    }

    public void deleteByID(final long id){
        reservationRepository.deleteById(id);
    }

    public ReservationRestModel getById(final Long id) {
        return new ReservationRestModel(reservationRepository.getOne(id));
    }

    public List<ReservationRestModel> getByReservationUserId(final Long userId){
        return reservationRepository.findAllByReservationUserId(userId).stream()
                .map(ReservationRestModel::new)
                .collect(Collectors.toList());
    }

    public List<ReservationRestModel> getByReservationSquashCourtId(final Long courtId){
        return reservationRepository.findAllByReservationSquashCourtId(courtId).stream()
                .map(ReservationRestModel::new)
                .collect(Collectors.toList());
    }



    private ReservationsEntity mapReservationRestModel(final ReservationRestModel model) {
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

        /*if(model.getUserLogin()==null){
            UsersEntity newUser = mapUserRestModel(model.getUserRestModel());
            System.out.println("NOWY USER:"+newUser.toString());
            reservationsEntity.setReservationUser(newUser);
        }
        else{

            UsersEntity oldUser = userRepository.findByLogin(model.getUserLogin());
            System.out.println("OLD USER:"+oldUser.toString());
            reservationsEntity.setReservationUser(oldUser);
        }*/

        System.out.println(reservationsEntity.toString());
        return reservationsEntity;
    }
    private UsersEntity mapUserRestModel(final UserRestModel model){
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
