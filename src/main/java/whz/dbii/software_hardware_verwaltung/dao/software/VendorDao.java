package whz.dbii.software_hardware_verwaltung.dao.software;

import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.model.software.Vendor;

public interface VendorDao {
    Vendor findById(Integer id);
    ObservableList<Vendor> findAll();
    boolean insert(Vendor vendor);
    boolean update(Vendor vendor);
    boolean deleteById(Integer id);
}
