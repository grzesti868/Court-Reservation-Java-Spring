package pl.Korty.Korty.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservations")
public class ReservationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String login;

    @Column
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date start_date;

    @Column
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date end_date;

    @Column
    private Long id_court;

    @Column
    private Integer people_num;

    @Column
    private String additional_info;

    @Column
    private Long id_user;

    public ReservationsEntity(Long id, String login, Date start_date, Date end_date, Long id_court, Integer people_num, String additional_info, Long id_user) {
        this.id = id;
        this.login = login;
        this.start_date = start_date;
        this.end_date = end_date;
        this.id_court = id_court;
        this.people_num = people_num;
        this.additional_info = additional_info;
        this.id_user = id_user;
    }

    public ReservationsEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public Long getId_court() {
        return id_court;
    }

    public Integer getPeople_num() {
        return people_num;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public Long getId_user() {
        return id_user;
    }
}
