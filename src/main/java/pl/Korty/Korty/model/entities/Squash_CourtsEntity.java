package pl.Korty.Korty.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "squash_courts")
public class Squash_CourtsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

   @OneToOne
   @JoinColumn(name ="id_address",referencedColumnName = "id")
   private AddressesEntity squashCourtAddress;

    @Column
    private Integer fields_num;

    public Squash_CourtsEntity() {

    }
    public Squash_CourtsEntity(Integer fields_num) {
        this.fields_num = fields_num;
    }

    public Long getId() {
        return id;
    }

    public AddressesEntity getSquashCourtAddress() {
        return squashCourtAddress;
    }

    public Integer getFields_num() {
        return fields_num;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSquashCourtAddress(AddressesEntity squashCourtAddress) {
        this.squashCourtAddress = squashCourtAddress;
    }

    public void setFields_num(Integer fields_num) {
        this.fields_num = fields_num;
    }
}
