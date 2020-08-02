package pl.Korty.Korty.model.entities;


import pl.Korty.Korty.model.enums.StatusEnum;
import pl.Korty.Korty.model.enums.SexEnum;
import pl.Korty.Korty.model.responses.UserRestModel;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private SexEnum sex;

    @Column
    private StatusEnum status;

    @OneToOne(targetEntity = AddressesEntity.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address", referencedColumnName = "id")
    private AddressesEntity addressesEntity;

    @OneToMany(mappedBy = "reservationUser",cascade = CascadeType.ALL)
    private List<ReservationsEntity> reservationsEntity;

    public UsersEntity() {
    }


    public UsersEntity(String login, String password, String email, String firstname, String lastname, SexEnum sex, StatusEnum status) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.sex = sex;
        this.status = status;
    }

    public Long getId() {
        return id;
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

    public AddressesEntity getAddressesEntity() {
        return addressesEntity;
    }

    public List<ReservationsEntity> getReservationsEntity() {
        return reservationsEntity;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public void setAddressesEntity(AddressesEntity addressesEntity) {
        this.addressesEntity = addressesEntity;
    }

    public void setReservationsEntity(List<ReservationsEntity> reservationsEntity) {
        this.reservationsEntity = reservationsEntity;
    }

    @Override
    public String toString() {
        return "UsersEntity{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", sex=" + sex +
                ", status=" + status +
                ", addressesEntity=" + addressesEntity +
                ", reservationsEntity=" + reservationsEntity +
                '}';
    }
}
