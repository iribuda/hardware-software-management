package whz.dbii.software_hardware_verwaltung.model.software;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.util.Date;

public class License {

    private final IntegerProperty id;
    private final SimpleIntegerProperty key;
    private final SimpleObjectProperty<Date> startDate;
    private final SimpleObjectProperty<Date> expirationDate;
    private final SimpleObjectProperty<Date> purchaseDate;
    private final SimpleStringProperty status;
    private final SimpleFloatProperty price;
    private final SimpleObjectProperty<Software> software;

    public License() {
        this.id = new SimpleIntegerProperty();
        this.key = new SimpleIntegerProperty();
        this.startDate = new SimpleObjectProperty<>();
        this.expirationDate = new SimpleObjectProperty<>();
        this.purchaseDate = new SimpleObjectProperty<>();
        this.status = new SimpleStringProperty();
        this.price = new SimpleFloatProperty();
        this.software = new SimpleObjectProperty<>();
    }
    public License(Integer id, Integer key, Date startDate, Date expirationDate, Date purchaseDate,
                   Float price, Software software) {
        this.id = new SimpleIntegerProperty(id);
        this.key = new SimpleIntegerProperty(key);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.expirationDate = new SimpleObjectProperty<>(expirationDate);
        this.purchaseDate = new SimpleObjectProperty<>(purchaseDate);
        this.status = new SimpleStringProperty(LicenseStatus.ACTIVE.toString());
        this.price = new SimpleFloatProperty(price);
        this.software = new SimpleObjectProperty<>(software);
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

    public int getKey() {
        return key.get();
    }

    public SimpleIntegerProperty keyProperty() {
        return key;
    }

    public void setKey(int key) {
        this.key.set(key);
    }

    public Date getStartDate() {
        return startDate.get();
    }

    public SimpleObjectProperty<Date> startDateProperty() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate.set(startDate);
    }

    public Date getExpirationDate() {
        return expirationDate.get();
    }

    public SimpleObjectProperty<Date> expirationDateProperty() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate.set(expirationDate);
    }

    public Date getPurchaseDate() {
        return purchaseDate.get();
    }

    public SimpleObjectProperty<Date> purchaseDateProperty() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate.set(purchaseDate);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public float getPrice() {
        return price.get();
    }

    public SimpleFloatProperty priceProperty() {
        return price;
    }

    public void setPrice(float price) {
        this.price.set(price);
    }

    public Software getSoftware() {
        return software.get();
    }

    public SimpleObjectProperty<Software> softwareProperty() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software.set(software);
    }
}
