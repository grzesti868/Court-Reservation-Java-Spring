package pl.Korty.Korty.model.enums;

public enum SexEnum {
    Male("M"), Female("F");

    private String code;

    private SexEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
