package pl.Korty.Korty.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor

@Entity
@Table(name = "squash_courts")
public class Squash_CourtsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name ="id_address",referencedColumnName = "id")
   private AddressesEntity squashCourtAddress;

    @Column(name = "fields_num")
    private Integer fields_num;


    public Squash_CourtsEntity(Integer fields_num) {
        this.fields_num = fields_num;
    }


}
