package whz.dbii.software_hardware_verwaltung.dao.hardware;

import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.model.hardware.Hardware;

public interface HardwareDao {
    Hardware findById(Integer id);
    ObservableList<Hardware> findAll();
    boolean insert(Hardware hardware);
    boolean deleteById(Integer id);
    boolean update(Hardware hardware);
}
