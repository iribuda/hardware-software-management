package whz.dbii.software_hardware_verwaltung.dao.hardware;

import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.model.hardware.Order;
import whz.dbii.software_hardware_verwaltung.model.software.License;

public interface OrderDao {

    Order findById(Integer id);
    ObservableList<Order> findAll();
    boolean insert(Order order);
    boolean update(Order order);
    boolean deleteById(Integer id);
}
