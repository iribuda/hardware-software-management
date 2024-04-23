package whz.dbii.software_hardware_verwaltung.model.software;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Vendor {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty email;
    private final SimpleStringProperty mobileNumber;

    public Vendor() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.mobileNumber = new SimpleStringProperty();
    }
    public Vendor(Integer id, String name, String email, String mobileNumber) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.mobileNumber = new SimpleStringProperty(mobileNumber);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getMobileNumber() {
        return mobileNumber.get();
    }

    public SimpleStringProperty mobileNumberProperty() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber.set(mobileNumber);
    }
}
