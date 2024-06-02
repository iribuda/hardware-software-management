package whz.dbii.software_hardware_verwaltung.model.hardware;

import javafx.beans.property.*;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.time.LocalDate;

@XmlType(name = "failure")
@XmlRootElement
public class Failure {

    private final IntegerProperty id;
    private final StringProperty type;
    private final StringProperty status;
    private final ObjectProperty<LocalDate> date;
    private final IntegerProperty hardwareId;
    private final StringProperty hardwareName;
    private final StringProperty workerName;

    public Failure(){
        this.id = new SimpleIntegerProperty();
        this.type = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
        this.date = new SimpleObjectProperty<>();
        this.hardwareId = new SimpleIntegerProperty();
        this.hardwareName = new SimpleStringProperty();
        this.workerName = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public Object getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public int getHardwareId() {
        return hardwareId.get();
    }

    public IntegerProperty hardwareIdProperty() {
        return hardwareId;
    }

    public void setHardwareId(int hardwareId) {
        this.hardwareId.set(hardwareId);
    }

    public String getHardwareName() {
        return hardwareName.get();
    }

    public StringProperty hardwareNameProperty() {
        return hardwareName;
    }

    public void setHardwareName(String hardwareName) {
        this.hardwareName.set(hardwareName);
    }

    public String getWorkerName() {
        return workerName.get();
    }

    public StringProperty workerNameProperty() {
        return workerName;
    }

    public void setWorkerName(String name, String surname) {
        if (name == null || surname == null)
            this.workerName.set("kein Mitarbeiter");
        else
            this.workerName.set(name + " " + surname);
    }
}
