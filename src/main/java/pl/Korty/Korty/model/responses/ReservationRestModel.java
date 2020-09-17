package pl.Korty.Korty.model.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.Korty.Korty.model.entities.ReservationsEntity;

import java.util.Date;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ReservationRestModel {


    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date start_date;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date end_date;

    private Integer people_num;
    private String additional_info;
    private Long courtId;

    private String userUsername;
    private UserRestModel userRestModel;

    public ReservationRestModel(Date start_date, Date end_date, Integer people_num, String additional_info, Long courtId,String userUsername, UserRestModel userRestModel) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.people_num = people_num;
        this.additional_info = additional_info;
        this.courtId = courtId;
        this.userUsername = userUsername;
        this.userRestModel = userRestModel;
    }

    public ReservationRestModel(ReservationsEntity entity) {
        this.start_date = entity.getStart_date();
        this.end_date = entity.getEnd_date();
        this.people_num = entity.getPeople_num();
        this.additional_info = entity.getAdditional_info();
        this.courtId = entity.getReservationSquashCourt().getId();
        this.userUsername = entity.getReservationUser().getUsername();
    }
}
