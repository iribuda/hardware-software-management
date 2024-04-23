package whz.dbii.software_hardware_verwaltung.model.hardware;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order {
    private final IntegerProperty order_id;
    private final IntegerProperty hardware_id;
    private final SimpleStringProperty order_status;
    private final SimpleObjectProperty order_date;

    public Order(){
        this.order_id = new SimpleIntegerProperty();
        this.hardware_id = new SimpleIntegerProperty();
        this.order_status = new SimpleStringProperty();
        this.order_date = new SimpleObjectProperty();
    }

    public Order(IntegerProperty order_id, IntegerProperty hardware_id, SimpleStringProperty order_status, SimpleObjectProperty order_date) {
        this.order_id = order_id;
        this.hardware_id = hardware_id;
        this.order_status = order_status;
        this.order_date = order_date;
    }

    public int getOrder_id() {
        return order_id.get();
    }

    public IntegerProperty order_idProperty() {
        return order_id;
    }

    public int getHardware_id() {
        return hardware_id.get();
    }

    public IntegerProperty hardware_idProperty() {
        return hardware_id;
    }

    public String getOrder_status() {
        return order_status.get();
    }

    public SimpleStringProperty order_statusProperty() {
        return order_status;
    }

    public Object getOrder_date() {
        return order_date.get();
    }

    public SimpleObjectProperty order_dateProperty() {
        return order_date;
    }




}
