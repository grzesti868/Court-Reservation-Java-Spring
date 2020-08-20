package pl.Korty.Korty.model.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import pl.Korty.Korty.model.entities.ReservationsEntity;

import java.util.Date;
import java.util.Objects;

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
        this.userLogin = entity.getReservationUser().getLogin();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationRestModel)) return false;
        ReservationRestModel that = (ReservationRestModel) o;
        return getStart_date().equals(that.getStart_date()) &&
                getEnd_date().equals(that.getEnd_date()) &&
                getPeople_num().equals(that.getPeople_num()) &&
                Objects.equals(getAdditional_info(), that.getAdditional_info()) &&
                getCourtId().equals(that.getCourtId()) &&
                getUserLogin().equals(that.getUserLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStart_date(), getEnd_date(), getPeople_num(), getAdditional_info(), getCourtId(), getUserLogin());
    }
}
