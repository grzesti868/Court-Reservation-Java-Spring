package pl.Korty.Korty.model.services;

import org.springframework.stereotype.Service;
import pl.Korty.Korty.model.entities.ReservationsEntity;
import pl.Korty.Korty.model.entities.UsersEntity;
import pl.Korty.Korty.model.repositories.ReservationRepository;
import pl.Korty.Korty.model.repositories.UserRepository;
import pl.Korty.Korty.model.responses.ReservationRestModel;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    final private ReservationRepository reservationRepository;

    final private UserRepository userRepository;

    public ReservationService(ReservationRepository reservationService, UserRepository userRepository) {
        this.reservationRepository = reservationService;

        this.userRepository = userRepository;
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

    public Long add(final ReservationRestModel reservationRestModel) { return reservationRepository.save(mapRestModel(reservationRestModel)).getId(); }

    public ReservationRestModel update(final Long id, ReservationRestModel reservation)
    {
        ReservationsEntity reservationToUpdate = reservationRepository.findById(id).get();

        reservationToUpdate.setStart_date(reservation.getStart_date());
        reservationToUpdate.setEnd_date(reservation.getEnd_date());
        reservationToUpdate.setPeople_num(reservation.getPeople_num());
        reservationToUpdate.setAdditional_info(reservation.getAdditional_info());
        reservationToUpdate.setReservationSquashCourt(reservation.getSquash_courtsEntity());
        //reservationToUpdate.setReservationUser(new ReservationsEntity());

        return new ReservationRestModel(reservationRepository.save(reservationToUpdate));
    }

    public void deleteByID(final long id){
        reservationRepository.deleteById(id);
    }

    public ReservationRestModel getById(final Long id) {
        return new ReservationRestModel(reservationRepository.getOne(id));
    }

    private ReservationsEntity mapRestModel(final ReservationRestModel model) {
        return new ReservationsEntity(model.getStart_date(),model.getEnd_date(),model.getPeople_num(), model.getAdditional_info());
    }


}
