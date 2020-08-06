package pl.Korty.Korty.model.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.catalina.User;
import pl.Korty.Korty.model.entities.ReservationsEntity;
import pl.Korty.Korty.model.entities.Squash_CourtsEntity;
import pl.Korty.Korty.model.entities.UsersEntity;

import javax.persistence.*;
import java.util.Date;

public class ReservationRestModel {


    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date start_date;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date end_date;

    private Integer people_num;
    private String additional_info;
    private Long courtId;

    private String userLogin;
    private UserRestModel userRestModel;


    public ReservationRestModel() {
    }

    public ReservationRestModel(Date start_date, Date end_date, Integer people_num, String additional_info, Long courtId,String userLogin, UserRestModel userRestModel) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.people_num = people_num;
        this.additional_info = additional_info;
        this.courtId = courtId;
        this.userLogin = userLogin;
        this.userRestModel = userRestModel;
    }

    public ReservationRestModel(ReservationsEntity entity) {
        this.start_date = entity.getStart_date();
        this.end_date = entity.getEnd_date();
        this.people_num = entity.getPeople_num();
        this.additional_info = entity.getAdditional_info();
        this.courtId = entity.getReservationSquashCourt().getId();
        //this.userRestModel = new UserRestModel(entity.getReservationUser());
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

    public Long getCourtId() {
        return courtId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public UserRestModel getUserRestModel() {
        return userRestModel;
    }

    @Override
    public String toString() {
        return "ReservationRestModel{" +
                "start_date=" + start_date +
                ", end_date=" + end_date +
                ", people_num=" + people_num +
                ", additional_info='" + additional_info + '\'' +
                ", courtId=" + courtId +
                ", userLogin='" + userLogin + '\'' +
                ", userRestModel=" + userRestModel +
                '}';
    }
}
