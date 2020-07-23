package enums;

public enum StatusEnum {
    Active("1"), Disabled("0");

    private String code;

    private StatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
