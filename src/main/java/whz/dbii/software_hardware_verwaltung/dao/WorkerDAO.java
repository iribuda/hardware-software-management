package whz.dbii.software_hardware_verwaltung.dao;

import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.model.Worker;

public interface WorkerDAO {
    Worker findById(Integer id);
    ObservableList<Worker> findAll();
    boolean save(Worker worker);
    boolean update(Worker worker);
    boolean delete(int id);
}
