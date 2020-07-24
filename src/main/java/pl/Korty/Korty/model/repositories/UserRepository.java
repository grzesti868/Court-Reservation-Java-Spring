package pl.Korty.Korty.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.Korty.Korty.model.entities.UsersEntity;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity,Long> {

    UsersEntity getByLogin(String login);
}
