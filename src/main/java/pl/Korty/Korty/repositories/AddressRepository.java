package pl.Korty.Korty.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.Korty.Korty.model.entities.AddressesEntity;

@Repository
public interface AddressRepository extends JpaRepository<AddressesEntity,Long> {

}
