package pl.Korty.Korty.model.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.Korty.Korty.model.entities.ReservationsEntity;

import java.util.List;

@Repository
public interface ReservationRepository  extends JpaRepository<ReservationsEntity,Long> {

    public List<ReservationsEntity> findAllByReservationUserId(Long user_id);
}
