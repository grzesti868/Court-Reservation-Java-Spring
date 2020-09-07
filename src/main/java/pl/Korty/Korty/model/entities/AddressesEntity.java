package pl.Korty.Korty.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data

@Entity
@Table(name = "addresses")
public class AddressesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "building_num")
    private Integer building_num;

    @Column(name = "apartment_num")
    private Integer apartment_num;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postal_code;

    @Column(name = "country")
    private String country;


    public AddressesEntity(String street, Integer building_num, Integer apartment_num, String city, String postal_code, String country) {
        this.street = street;
        this.building_num = building_num;
        this.apartment_num = apartment_num;
        this.city = city;
        this.postal_code = postal_code;
        this.country = country;
    }


}
