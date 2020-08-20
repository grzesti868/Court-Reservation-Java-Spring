package pl.Korty.Korty.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.Korty.Korty.model.entities.Squash_CourtsEntity;

@Repository
public interface Squash_CourtsRepository extends JpaRepository<Squash_CourtsEntity,Long> {

    Squash_CourtsEntity findBySquashCourtAddressId(Long id);
}
