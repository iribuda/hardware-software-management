package whz.dbii.software_hardware_verwaltung.dao.hardware;

import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.model.hardware.Manufacturer;

public interface ManufacturerDao {
    Manufacturer findById(Integer id);
    ObservableList<Manufacturer> findAll();
    boolean insert(Manufacturer manufacturer);
    boolean update(Manufacturer manufacturer);
    boolean delete(Manufacturer manufacturer);
}
