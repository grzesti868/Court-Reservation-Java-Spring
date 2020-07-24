package pl.Korty.Korty.model.responses;

import pl.Korty.Korty.model.entities.UsersEntity;
import pl.Korty.Korty.model.enums.SexEnum;
import pl.Korty.Korty.model.enums.StatusEnum;

import javax.persistence.Column;

public class UserRestModel {

    private String login;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private SexEnum sex;
    private Long id_address;
    private StatusEnum status;

    public UserRestModel(String login, String password, String email, String firstname, String lastname, SexEnum sex, Long id_address, StatusEnum status) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.sex = sex;
        this.id_address = id_address;
        this.status = status;
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
        this.id_address = entity.getId_address();
        this.status = entity.getStatus();

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
}
