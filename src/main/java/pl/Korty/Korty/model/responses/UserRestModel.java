package pl.Korty.Korty.model.responses;

import pl.Korty.Korty.model.entities.AddressesEntity;
import pl.Korty.Korty.model.entities.ReservationsEntity;
import pl.Korty.Korty.model.entities.UsersEntity;
import pl.Korty.Korty.model.enums.SexEnum;
import pl.Korty.Korty.model.enums.StatusEnum;

import javax.persistence.Column;
import java.util.List;

public class UserRestModel {

    private String login;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private SexEnum sex;
    private StatusEnum status;
    private AddressesEntity addressesEntity;
    private List<ReservationsEntity> reservationsEntity;

    public UserRestModel(String login, String password, String email, String firstname, String lastname, SexEnum sex, StatusEnum status, AddressesEntity addressesEntity, List<ReservationsEntity> reservationsEntity) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.sex = sex;
        this.status = status;
        this.addressesEntity = addressesEntity;
        this.reservationsEntity = reservationsEntity;
    }

    public UserRestModel() {
    }

    public UserRestModel(final UsersEntity entity) {
        this.login = entity.getLogin();
        this.password = entity.getPassword();
        this.email = entity.getEmail();
        this.firstname = entity.getFirstname();
        this.lastname = entity.getLastname();
        this.sex = entity.getSex();
        this.status = entity.getStatus();
        this.addressesEntity = entity.getAddressesEntity();
        this.reservationsEntity = entity.getReservationsEntity();
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

    public List<ReservationsEntity> getReservationsEntity() {
        return reservationsEntity;
    }

    public AddressesEntity getAddressesEntity() {
        return addressesEntity;
    }
}
