package whz.dbii.software_hardware_verwaltung.model.hardware;

public enum FailureStatus {
    TO_REPAIR ("zur Reparatur"),
    REPAIRED ("repariert"),
    REPLACEMENT_ORDERED ("Ersatz bestellt"),
    UNDER_INSPECTION ("unter Kontrolle"),
    AWAITING_PARTS ("warten auf Teile"),
    NOT_REPAIRABLE ("nicht reparaturfähig");
    private String status;
    FailureStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return this.status;
    }
}
