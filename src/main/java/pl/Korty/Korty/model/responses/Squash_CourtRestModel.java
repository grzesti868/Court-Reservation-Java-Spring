package pl.Korty.Korty.model.responses;

import pl.Korty.Korty.model.entities.AddressesEntity;
import pl.Korty.Korty.model.entities.Squash_CourtsEntity;


public class Squash_CourtRestModel {

    private AddressRestModel addressRestModel;
    private Integer fields_num;

    public Squash_CourtRestModel(AddressRestModel addressRestModel, Integer fields_num) {
        this.addressRestModel = addressRestModel;
        this.fields_num = fields_num;
    }

    public Squash_CourtRestModel() {
    }

    public Squash_CourtRestModel(Squash_CourtsEntity entity)
    {
        this.addressRestModel = new AddressRestModel(entity.getSquashCourtAddress());
        this.fields_num = entity.getFields_num();
    }

    public AddressRestModel getAddressRestModel() {
        return addressRestModel;
    }

    public Integer getFields_num() {
        return fields_num;
    }

    @Override
    public String toString() {
        return "Squash_CourtModel{" +
                "addressRestModel=" + addressRestModel +
                ", fields_num=" + fields_num +
                '}';
    }
}
