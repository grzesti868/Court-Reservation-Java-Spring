package com.squash.squashcourts.model.entities;


import com.squash.squashcourts.model.enums.StatusEnum;
import com.squash.squashcourts.model.enums.SexEnum;

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


    public UsersEntity(Long id, String login, String password, String email, String firstname, String lastname, SexEnum sex, Long id_address, StatusEnum status) {
        this.id = id;
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
}
