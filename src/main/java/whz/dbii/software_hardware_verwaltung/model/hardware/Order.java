package whz.dbii.software_hardware_verwaltung.model.hardware;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Order {
    private final IntegerProperty order_id;
    private final SimpleObjectProperty<Hardware> hardware;
    private final SimpleStringProperty order_status;
    private final SimpleObjectProperty<LocalDate> order_date;

    public Order(){
        this.order_id = new SimpleIntegerProperty();
        this.hardware = new SimpleObjectProperty();
        this.order_status = new SimpleStringProperty();
        this.order_date = new SimpleObjectProperty();
    }

    public Order(Integer order_id, Hardware hardware, OrderStatus order_status, LocalDate order_date) {
        this.order_id = new SimpleIntegerProperty(order_id);
        this.hardware = new SimpleObjectProperty<>(hardware);
        this.order_status = new SimpleStringProperty(order_status.toString());
        this.order_date = new SimpleObjectProperty<>(order_date);
    }

    public int getOrder_id() {
        return order_id.get();
    }

    public IntegerProperty order_idProperty() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id.set(order_id);
    }



    public String getOrder_status() {
        return order_status.get();
    }

    public SimpleStringProperty order_statusProperty() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status.set(order_status);
    }

    public Hardware getHardware() {
        return hardware.get();
    }

    public SimpleObjectProperty<Hardware> hardwareProperty() {
        return hardware;
    }

    public void setHardware(Hardware hardware) {
        this.hardware.set(hardware);
    }

    public LocalDate getOrder_date() {
        return order_date.get();
    }

    public SimpleObjectProperty<LocalDate> order_dateProperty() {
        return order_date;
    }

    public void setOrder_date(LocalDate order_date) {
        this.order_date.set(order_date);
    }
}
