package pl.Korty.Korty.model.enums;

public enum ApplicationUserPermission {
    ADDRESS_READ("address:read"),
    ADDRESS_WRITE("address:write"),
    RESERVATION_READ("reservation:read"),
    RESERVATION_WRITE("reservation:write"),
    COURT_READ("court:read"),
    COURT_WRITE("court:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String permission;


    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
