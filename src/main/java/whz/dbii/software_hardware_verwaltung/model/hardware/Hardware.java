package whz.dbii.software_hardware_verwaltung.model.hardware;

import javafx.beans.property.*;
import whz.dbii.software_hardware_verwaltung.dao.hardware.ManufacturerDao;

public class Hardware {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty version;
    private final ObjectProperty<Warranty> warranty;

    public Hardware(){
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.version = new SimpleStringProperty();
        this.warranty = new SimpleObjectProperty<>();
    }


    public Hardware(IntegerProperty id, StringProperty name, StringProperty version, ObjectProperty<Warranty> warranty) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.warranty = warranty;
    }


    public Warranty getWarranty() {
        return warranty.get();
    }

    public ObjectProperty<Warranty> warrantyProperty() {
        return warranty;
    }

    public String getVersion() {
        return version.get();
    }

    public StringProperty versionProperty() {
        return version;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }


    public void setId(int hardwareId) {
    }

    public void setName(String hardwareName) {
    }

    public void setVersion(String softwareVersion) {
    }

    public Manufacturer getManufacturer() {
        return getManufacturer();
    }
}
