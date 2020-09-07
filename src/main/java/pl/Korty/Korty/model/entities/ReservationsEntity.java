package pl.Korty.Korty.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reservations")
public class ReservationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "start_date",nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date start_date;

    @Column(name = "end_date",nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date end_date;

    @Column(name = "people_num")
    private Integer people_num;

    @Column(name = "additional_info")
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

}
