package pl.Korty.Korty.model.entities;


import pl.Korty.Korty.model.enums.StatusEnum;
import pl.Korty.Korty.model.enums.SexEnum;

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

    @OneToOne
    @JoinColumn(name = "id_address", referencedColumnName = "id")
    private AddressesEntity addressesEntity;

    @OneToMany(mappedBy = "reservationUser")
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

    public void setId(Long id) {
        this.id = id;
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
}
