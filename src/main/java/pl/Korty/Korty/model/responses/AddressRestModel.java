package pl.Korty.Korty.model.responses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.Korty.Korty.model.entities.AddressesEntity;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class AddressRestModel {

    private String street;
    private Integer building_num;
    private Integer apartment_num;
    private String city;
    private String postal_code;
    private String country;

    public AddressRestModel(String street, Integer building_num, Integer apartment_num, String city, String postal_code, String country) {
        this.street = street;
        this.building_num = building_num;
        this.apartment_num = apartment_num;
        this.city = city;
        this.postal_code = postal_code;
        this.country = country;
    }

    public AddressRestModel(final AddressesEntity entity) {
        this.street = entity.getStreet();
        this.building_num = entity.getBuilding_num();
        this.apartment_num = entity.getApartment_num();
        this.city = entity.getCity();
        this.postal_code = entity.getPostal_code();
        this.country = entity.getCountry();
    }
}
