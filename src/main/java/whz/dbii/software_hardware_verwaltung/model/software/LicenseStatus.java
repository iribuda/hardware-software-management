package whz.dbii.software_hardware_verwaltung.model.software;

public enum LicenseStatus {
    ACTIVE ("aktiv"),
    INACTIVE ("inaktiv"),
    EXPIRED ("abgelaufen");
    private String status;
    LicenseStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
