package pl.Korty.Korty.model.entities;


import pl.Korty.Korty.model.enums.StatusEnum;
import pl.Korty.Korty.model.enums.SexEnum;

import javax.persistence.*;

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
    private  Long id_address;

    @Column
    private StatusEnum status;


    public UsersEntity(String login, String password, String email, String firstname, String lastname, SexEnum sex, Long id_address, StatusEnum status) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.sex = sex;
        this.id_address = id_address;
        this.status = status;
    }

    public UsersEntity() {
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

    public Long getId_address() {
        return id_address;
    }

    public StatusEnum getStatus() {
        return status;
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

    public void setId_address(Long id_address) {
        this.id_address = id_address;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
