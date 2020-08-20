package pl.Korty.Korty.model.responses;

import pl.Korty.Korty.model.entities.Squash_CourtsEntity;

import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Squash_CourtRestModel)) return false;
        Squash_CourtRestModel that = (Squash_CourtRestModel) o;
        return getAddressRestModel().equals(that.getAddressRestModel()) &&
                getFields_num().equals(that.getFields_num());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddressRestModel(), getFields_num());
    }
}
