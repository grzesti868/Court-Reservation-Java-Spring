package pl.Korty.Korty.model.services;

import org.springframework.stereotype.Service;
import pl.Korty.Korty.model.entities.AddressesEntity;
import pl.Korty.Korty.model.entities.ReservationsEntity;
import pl.Korty.Korty.model.entities.UsersEntity;
import pl.Korty.Korty.model.repositories.ReservationRepository;
import pl.Korty.Korty.model.repositories.Squash_CourtsRepository;
import pl.Korty.Korty.model.repositories.UserRepository;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.model.responses.ReservationRestModel;
import pl.Korty.Korty.model.responses.UserRestModel;

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

    public Long add(final ReservationRestModel reservationRestModel) { //todo: check when adding on EXISTING user
        Optional<ReservationRestModel> reservationToAdd = Optional.ofNullable(reservationRestModel);
        if(reservationToAdd.isEmpty()) {
            return -1L; //rezerwacja jest pusta
        } else {
            if(!squash_courtsRepository.existsById(reservationToAdd.get().getCourtId())) {
                return -2L; //nie ma id courtu
            } else {
                Optional<UserRestModel> reservationUser = Optional.ofNullable(reservationToAdd.get().getUserRestModel());
                Optional<String> userLogin = Optional.ofNullable(reservationToAdd.get().getUserLogin());
                if(reservationUser.isEmpty() && userLogin.isEmpty()){
                    return -3L; //nie ma ani nowego usera ani id starego
                } else {
                    Optional<ReservationsEntity> mappedReservation = Optional.ofNullable(mapReservationRestModel(reservationToAdd.get()));
                    if(mappedReservation.isEmpty()){
                        return -4L; //zle dane usera lub nie mozna zarezerwowac kortu w tym przedziale czasowym
                    } else {
                        return reservationRepository.save(mappedReservation.get()).getId();
                    }
                }
            }
        }
    }

    public ReservationRestModel update(final Long id, ReservationRestModel reservation) //TODO: check if reservation is correct
    {
        Optional<ReservationRestModel> reservationRestModel = Optional.ofNullable(reservation);
        if(reservationRepository.existsById(id) && reservationRestModel.isPresent()){
            Optional <ReservationsEntity> reservationToUpdate = reservationRepository.findById(id);

            if(isTimeValid(reservation.getStart_date(),reservation.getEnd_date(),reservation.getCourtId()))
            {
                reservationToUpdate.get().setStart_date(reservationRestModel.get().getStart_date());
                reservationToUpdate.get().setEnd_date(reservationRestModel.get().getEnd_date());
            }
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

    private ReservationsEntity mapReservationRestModel(final ReservationRestModel model) {
        System.out.println("MODEL REZERWACJI:"+ model.toString());

        if(!isTimeValid(model.getStart_date(),model.getEnd_date(),model.getCourtId()))
            return null;//nie mozna zarezerwowac w tym przedziale czasowym

        ReservationsEntity reservationsEntity = new ReservationsEntity(model.getStart_date(),model.getEnd_date(),model.getPeople_num(), model.getAdditional_info());

        reservationsEntity.setReservationSquashCourt(squash_courtsRepository.findById(model.getCourtId()).get());

        System.out.println("BAZOWA ENCJA:"+reservationsEntity.toString());

        Optional<String> reservationUser = Optional.ofNullable(model.getUserLogin());
        if(reservationUser.isEmpty()) {
            Optional<AddressRestModel> userAddress = Optional.ofNullable(model.getUserRestModel().getAddressRestModel());
            UsersEntity newUser = mapUserRestModel(model.getUserRestModel());
            if(userAddress.isEmpty()){
                return null; //nie ma id adresu
            }
            if(userRepository.existsByLogin(newUser.getLogin())){
                return null; //jest juz user o takim loginie
            }
            System.out.println("NOWY USER:"+newUser.toString());
            reservationsEntity.setReservationUser(newUser);
        } else {

            Optional<UsersEntity> oldUser = Optional.ofNullable(userRepository.findByLogin(model.getUserLogin()));
            System.out.println("OLD USER:"+oldUser.toString());
            if(oldUser.isEmpty()){
                return null; //nie ma takiego usera
            }
            reservationsEntity.setReservationUser(oldUser.get());
        }


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

    public Boolean isTimeValid(Date start, Date end, Long idCourt) { //TODO: change to private after all is ok and delete test



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
