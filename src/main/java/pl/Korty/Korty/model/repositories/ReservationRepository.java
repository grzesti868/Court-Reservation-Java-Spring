package pl.Korty.Korty.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.Korty.Korty.model.entities.ReservationsEntity;

import java.util.List;

@Repository
public interface ReservationRepository  extends JpaRepository<ReservationsEntity,Long> {

    List<ReservationsEntity> findAllByReservationUserLogin(String userLogin);
    List<ReservationsEntity> findAllByReservationSquashCourtId(Long courtId);
}
