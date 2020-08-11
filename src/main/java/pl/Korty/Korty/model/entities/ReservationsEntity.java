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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date start_date;

    @Column
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date end_date;

    @Column
    private Integer people_num;

    @Column
    private String additional_info;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_court",referencedColumnName = "id")
    private Squash_CourtsEntity reservationSquashCourt;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="id_user")
    private UsersEntity reservationUser;

    public ReservationsEntity(Date start_date, Date end_date,Integer people_num, String additional_info) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.people_num = people_num;
        this.additional_info = additional_info;
    }

    public ReservationsEntity() {
    }

    public Long getId() {
        return id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public Integer getPeople_num() {
        return people_num;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public Squash_CourtsEntity getReservationSquashCourt() { return reservationSquashCourt; }

    public UsersEntity getReservationUser() {
        return reservationUser;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public void setPeople_num(Integer people_num) {
        this.people_num = people_num;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public void setReservationSquashCourt(Squash_CourtsEntity reservationSquashCourt) {
        this.reservationSquashCourt = reservationSquashCourt;
    }

    public void setReservationUser(UsersEntity reservationUser) {
        this.reservationUser = reservationUser;
    }

    @Override
    public String toString() {
        return "ReservationsEntity{" +
                "id=" + id +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", people_num=" + people_num +
                ", additional_info='" + additional_info + '\'' +
                ", reservationSquashCourt=" + reservationSquashCourt +
                ", reservationUser=" + reservationUser +
                '}';
    }
}
