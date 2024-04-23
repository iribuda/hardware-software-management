package whz.dbii.software_hardware_verwaltung.model.software;

import javafx.beans.property.*;

public class Software {

    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty version;
    private final ObjectProperty<Vendor> vendor;

    public Software() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.version = new SimpleStringProperty();
        this.vendor = new SimpleObjectProperty<>();
    }
    public Software(Integer id, String name, String version, Vendor vendor) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.version = new SimpleStringProperty(version);
        this.vendor = new SimpleObjectProperty<>(vendor);
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getVersion() {
        return version.get();
    }

    public StringProperty versionProperty() {
        return version;
    }

    public void setVersion(String version) {
        this.version.set(version);
    }

    public Vendor getVendor() {
        return vendor.get();
    }

    public ObjectProperty<Vendor> vendorProperty() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor.set(vendor);
    }
}
