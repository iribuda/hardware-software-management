package whz.dbii.software_hardware_verwaltung.model.hardware;

public enum OrderStatus {
    PENDING_PURCHASE ("anstehender Kauf"),
    BOUGHT ("gekauft"),
    DELIVERED ("geliefert"),
    CANCELED ("storniert");

    private String status;
    OrderStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return this.status;
    }
}
