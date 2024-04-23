package whz.dbii.software_hardware_verwaltung.dao.software;

import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.model.software.Vendor;

public interface VendorDao {
    Vendor findById(Integer id);
    ObservableList<Vendor> findAll();
    void insert(Vendor vendor);
    void update(Vendor vendor);
    void deleteById(Integer id);
}
