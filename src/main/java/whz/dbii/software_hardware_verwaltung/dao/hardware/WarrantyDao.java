package whz.dbii.software_hardware_verwaltung.dao.hardware;

import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.model.hardware.Warranty;
import whz.dbii.software_hardware_verwaltung.model.software.Vendor;

public interface WarrantyDao {
    Warranty findById(Integer id);
    ObservableList<Vendor> findAll();
    boolean insert(Warranty warranty);
    boolean update(Warranty warranty);
    boolean deletyById(Integer id);
    ObservableList<String> findAllWarrantyNames();
    int findIdByName(String name);


}
