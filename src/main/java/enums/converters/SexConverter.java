package enums.converters;

import enums.SexEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class SexConverter implements AttributeConverter<SexEnum, String> {

    @Override
    public String convertToDatabaseColumn(SexEnum sex) {
        if (sex == null) {
            return null;
        }
        return sex.getCode();
    }

    @Override
    public SexEnum convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(SexEnum.values())
                .filter(s -> s.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
