package whz.dbii.software_hardware_verwaltung.model.hardware;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Manufacturer {

    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty email;
    private final StringProperty mobileNumber;


    public Manufacturer(){
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.mobileNumber = new SimpleStringProperty();
    }
    public Manufacturer(IntegerProperty id, StringProperty name, StringProperty email, StringProperty mobileNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
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

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber.get();
    }

    public StringProperty mobileNumberProperty() {
        return mobileNumber;
    }


    public void setId(int anInt) {
    }

    public void setName(String vendorName) {
    }

    public void setEmail(String email) {
    }

    public void setMobileNumber(String mobileNumber) {
    }
}
