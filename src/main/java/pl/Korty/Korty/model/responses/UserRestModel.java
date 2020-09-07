package pl.Korty.Korty.model.responses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.Korty.Korty.model.entities.ReservationsEntity;
import pl.Korty.Korty.model.entities.UsersEntity;
import pl.Korty.Korty.model.enums.SexEnum;
import pl.Korty.Korty.model.enums.StatusEnum;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class UserRestModel {

    private String login;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private SexEnum sex;
    private StatusEnum status;
    private AddressRestModel addressRestModel;
    private List<ReservationRestModel> reservationRestModels;

    public UserRestModel(String login, String password, String email, String firstname, String lastname, SexEnum sex, StatusEnum status, AddressRestModel addressRestModel, List<ReservationRestModel> reservationRestModels) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.sex = sex;
        this.status = status;
        this.addressRestModel = addressRestModel;
        this.reservationRestModels = reservationRestModels;
    }

    public UserRestModel(final UsersEntity entity) {
        Optional<List<ReservationsEntity>> hasReservations = Optional.ofNullable(entity.getReservationsEntity());
         this.login = entity.getLogin();
        this.password = entity.getPassword();
        this.email = entity.getEmail();
        this.firstname = entity.getFirstname();
        this.lastname = entity.getLastname();
        this.sex = entity.getSex();
        this.status = entity.getStatus();
        this.addressRestModel = new AddressRestModel(entity.getAddressesEntity());
        if(hasReservations.isPresent()) { this.reservationRestModels = entity.getReservationsEntity().stream().map(ReservationRestModel::new).collect(Collectors.toList()); }
    }

}
