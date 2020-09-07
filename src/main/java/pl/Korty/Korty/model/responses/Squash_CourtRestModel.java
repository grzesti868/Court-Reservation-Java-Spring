package pl.Korty.Korty.model.responses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.Korty.Korty.model.entities.Squash_CourtsEntity;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Squash_CourtRestModel {

    private AddressRestModel addressRestModel;
    private Integer fields_num;

    public Squash_CourtRestModel(AddressRestModel addressRestModel, Integer fields_num) {
        this.addressRestModel = addressRestModel;
        this.fields_num = fields_num;
    }

    public Squash_CourtRestModel(Squash_CourtsEntity entity)
    {
        this.addressRestModel = new AddressRestModel(entity.getSquashCourtAddress());
        this.fields_num = entity.getFields_num();
    }

}
