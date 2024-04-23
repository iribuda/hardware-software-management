package whz.dbii.software_hardware_verwaltung.model.hardware;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Warranty {
    private final IntegerProperty id;
    private final SimpleObjectProperty<LocalDateTime> startDate;
    private final SimpleObjectProperty<LocalDateTime> expirationDate;
    private final SimpleStringProperty status;

    public Warranty() {
        this.id = new SimpleIntegerProperty();
        this.startDate = new SimpleObjectProperty<>();
        this.expirationDate = new SimpleObjectProperty<>();
        this.status = new SimpleStringProperty();
        this.hardware = new SimpleObjectProperty<>();
    }
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }
    public LocalDateTime getStartDate() {
        return startDate.get();
    }

    public SimpleObjectProperty<LocalDateTime> startDateProperty() {
        return startDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate.get();
    }

    public SimpleObjectProperty<LocalDateTime> expirationDateProperty() {
        return expirationDate;
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }


    public Hardware getHardware() {
        return hardware.get();
    }

    public SimpleObjectProperty<Hardware> hardwareProperty() {
        return hardware;
    }

    private final SimpleObjectProperty<Hardware> hardware;

    public Warranty(IntegerProperty id, SimpleIntegerProperty key, SimpleObjectProperty<LocalDateTime> startDate, SimpleObjectProperty<LocalDateTime> expirationDate, SimpleObjectProperty<LocalDateTime> purchaseDate, SimpleStringProperty status, SimpleFloatProperty price, SimpleObjectProperty<Hardware> hardware) {
        this.id = id;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.status = status;
        this.hardware = hardware;
    }
}
