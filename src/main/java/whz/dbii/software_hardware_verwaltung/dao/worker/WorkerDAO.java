package whz.dbii.software_hardware_verwaltung.dao.worker;

import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.model.Worker;
import whz.dbii.software_hardware_verwaltung.model.hardware.Hardware;
import whz.dbii.software_hardware_verwaltung.model.software.Software;

import java.util.List;

public interface WorkerDAO {
    Worker findById(Integer id);
    ObservableList<Worker> findAll();
    boolean save(Worker worker);
    boolean update(Worker worker);
    boolean delete(int id);
    boolean deleteWorkersHardware(int hardwareId, int workerId);
    ObservableList<String> findNamesOfSoftwareOfWorker(int id);
    ObservableList<Software> findSoftwareOfWorker(int id);
    ObservableList<String> findNamesOfHardwareOfWorker(int id);

    boolean addSoftwareToWorker(int softwareId, int workerId);
    boolean addHardwareToWorker(int hardwareId, int workerId);
}
