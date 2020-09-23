package pl.Korty.Korty.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.Korty.Korty.model.entities.ReservationsEntity;

import java.util.List;

@Repository
public interface ReservationRepository  extends JpaRepository<ReservationsEntity,Long> {

   List<ReservationsEntity> findAllByReservationUserUsername(String username);
   List<ReservationsEntity> findAllByReservationSquashCourtId(Long courtId);
}
