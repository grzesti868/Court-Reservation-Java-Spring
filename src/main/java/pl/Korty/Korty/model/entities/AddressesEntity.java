package pl.Korty.Korty.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class AddressesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String street;

    @Column
    private Integer building_num;

    @Column
    private Integer apartment_num;

    @Column
    private String city;

    @Column
    private String postal_code;

    @Column
    private String country;

    public AddressesEntity() {
    }

    public AddressesEntity(String street, Integer building_num, Integer apartment_num, String city, String postal_code, String country) {
        this.street = street;
        this.building_num = building_num;
        this.apartment_num = apartment_num;
        this.city = city;
        this.postal_code = postal_code;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public Integer getBuilding_num() {
        return building_num;
    }

    public Integer getApartment_num() {
        return apartment_num;
    }

    public String getCity() {
        return city;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public String getCountry() {
        return country;
    }


    public void setStreet(String street) {
        this.street = street;
    }

    public void setBuilding_num(Integer building_num) {
        this.building_num = building_num;
    }

    public void setApartment_num(Integer apartment_num) {
        this.apartment_num = apartment_num;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "AddressesEntity{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", building_num=" + building_num +
                ", apartment_num=" + apartment_num +
                ", city='" + city + '\'' +
                ", postal_code='" + postal_code + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

}
