package whz.dbii.software_hardware_verwaltung.model.hardware;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalDate;

public class Warranty {
    private final IntegerProperty id;
    private final SimpleObjectProperty<LocalDate> startDate;
    private final SimpleObjectProperty<LocalDate> expirationDate;
    private final SimpleStringProperty status;

    public Warranty() {
        this.id = new SimpleIntegerProperty();
        this.startDate = new SimpleObjectProperty<>();
        this.expirationDate = new SimpleObjectProperty<>();
        this.status = new SimpleStringProperty();
    }
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }
    public LocalDate getStartDate() {
        return startDate.get();
    }

    public SimpleObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate.get();
    }

    public SimpleObjectProperty<LocalDate> expirationDateProperty() {
        return expirationDate;
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }
    public void setId(int id) {
        this.id.set(id);
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate.set(startDate);
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate.set(expirationDate);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

}
