package pl.Korty.Korty.model.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import pl.Korty.Korty.model.enums.SexEnum;
import pl.Korty.Korty.model.enums.StatusEnum;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Data

@Entity
@Table(name = "users")
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, name = "login")
    private String login;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "firstname")
    private String firstname;

    @Column(nullable = false, name = "lastname")
    private String lastname;

    @Column(nullable = false, name = "sex")
    private SexEnum sex;

    @Column(nullable = false,name = "status")
    private StatusEnum status;

    @OneToOne(targetEntity = AddressesEntity.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address", referencedColumnName = "id")
    private AddressesEntity addressesEntity;

    @OneToMany(mappedBy = "reservationUser",cascade = CascadeType.ALL)
    private List<ReservationsEntity> reservationsEntity;



    public UsersEntity(String login, String password, String email, String firstname, String lastname, SexEnum sex, StatusEnum status) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.sex = sex;
        this.status = status;
    }

}
