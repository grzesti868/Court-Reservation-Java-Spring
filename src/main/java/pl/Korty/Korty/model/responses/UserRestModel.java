package pl.Korty.Korty.model.responses;

import pl.Korty.Korty.model.entities.ReservationsEntity;
import pl.Korty.Korty.model.entities.UsersEntity;
import pl.Korty.Korty.model.enums.SexEnum;
import pl.Korty.Korty.model.enums.StatusEnum;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public UserRestModel() {
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

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public SexEnum getSex() {
        return sex;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public AddressRestModel getAddressRestModel() {
        return addressRestModel;
    }

    public List<ReservationRestModel> getReservationRestModels() {
        return reservationRestModels;
    }

    @Override
    public String toString() {
        return "UserRestModel{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", sex=" + sex +
                ", status=" + status +
                ", addressRestModel=" + addressRestModel +
                ", reservationRestModels=" + reservationRestModels +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRestModel)) return false;
        UserRestModel that = (UserRestModel) o;
        return getLogin().equals(that.getLogin()) &&
                getPassword().equals(that.getPassword()) &&
                getEmail().equals(that.getEmail()) &&
                getFirstname().equals(that.getFirstname()) &&
                getLastname().equals(that.getLastname()) &&
                getSex() == that.getSex() &&
                getStatus() == that.getStatus() &&
                Objects.equals(getAddressRestModel(), that.getAddressRestModel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPassword(), getEmail(), getFirstname(), getLastname(), getSex(), getStatus(), getAddressRestModel());
    }
}
