package whz.dbii.software_hardware_verwaltung.model.hardware;

import javafx.beans.property.*;
import whz.dbii.software_hardware_verwaltung.dao.hardware.ManufacturerDao;

public class Hardware {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty version;
    private final ObjectProperty<Warranty> warranty;
    private final ObjectProperty<Manufacturer> manufacturer;

    public Hardware(){
        this.manufacturer = new SimpleObjectProperty<>();
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.version = new SimpleStringProperty();
        this.warranty = new SimpleObjectProperty<>();
    }


    public Hardware(Integer id, String name, String version, Warranty warranty, Manufacturer manufacturer) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.version = new SimpleStringProperty(version);
        this.warranty = new SimpleObjectProperty<>(warranty);
        this.manufacturer = new SimpleObjectProperty<>(manufacturer);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getVersion() {
        return version.get();
    }

    public StringProperty versionProperty() {
        return version;
    }

    public Warranty getWarranty() {
        return warranty.get();
    }

    public ObjectProperty<Warranty> warrantyProperty() {
        return warranty;
    }

    public void setWarranty(Warranty warranty) {
        this.warranty.set(warranty);
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setVersion(String version) {
        this.version.set(version);
    }

    public Manufacturer getManufacturer() {
        return manufacturer.get();
    }

    public ObjectProperty<Manufacturer> manufacturerProperty() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer.set(manufacturer);
    }
}
