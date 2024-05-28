package whz.dbii.software_hardware_verwaltung.dao.software;

import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.model.software.License;

public interface LicenseDao {
    License findById(Integer id);
    ObservableList<License> findAll();
    boolean insert(License license);
    boolean update(License license);
    boolean deleteById(Integer id);

    boolean renewLicense(License license);
}
