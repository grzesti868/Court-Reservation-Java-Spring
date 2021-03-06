package pl.Korty.Korty.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.Korty.Korty.model.entities.UsersEntity;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity,Long> {

    UsersEntity findByUsername(String username);
    boolean  existsByUsername(String username);
    void deleteByUsername(String username);
}
