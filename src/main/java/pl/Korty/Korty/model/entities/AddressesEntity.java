package com.squash.squashcourts.model.entities;

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

    public AddressesEntity(Long id, String street, Integer building_num, Integer apartment_num, String city, String postal_code, String country) {
        this.id = id;
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
}
