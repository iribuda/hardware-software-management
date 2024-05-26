package whz.dbii.software_hardware_verwaltung.dao.hardware;

import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.model.hardware.Failure;
import whz.dbii.software_hardware_verwaltung.model.hardware.Order;

public interface FailureDao {

    Failure findById(Integer id);
    ObservableList<Failure> findAll();
    boolean insert(Failure failure);
    boolean update(Failure failure);
    boolean deleteById(Integer id);
}
