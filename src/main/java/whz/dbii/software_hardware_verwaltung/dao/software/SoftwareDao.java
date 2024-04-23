package whz.dbii.software_hardware_verwaltung.dao.software;

import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.model.software.Software;

public interface SoftwareDao {
    Software findById(Integer id);
    ObservableList<Software> findAll();
    void insert(Software software);
    void update(Software software);
    void deleteById(Integer id);
}
