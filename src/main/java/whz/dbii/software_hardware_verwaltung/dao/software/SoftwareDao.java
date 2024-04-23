package whz.dbii.software_hardware_verwaltung.dao.software;

import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.model.software.Software;

public interface SoftwareDao {
    Software findById(Integer id);
    ObservableList<Software> findAll();
    boolean insert(Software software);
    boolean update(Software software);
    boolean deleteById(Integer id);
}
