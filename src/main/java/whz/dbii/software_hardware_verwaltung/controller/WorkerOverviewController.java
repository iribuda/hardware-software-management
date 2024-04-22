package whz.dbii.software_hardware_verwaltung.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import whz.dbii.software_hardware_verwaltung.MainApp;
import whz.dbii.software_hardware_verwaltung.dao.WorkerDAO;
import whz.dbii.software_hardware_verwaltung.dao.WorkerDAOImpl;
import whz.dbii.software_hardware_verwaltung.model.Worker;

import java.sql.SQLException;

public class WorkerOverviewController {

    @FXML
    private TableView<Worker> workerTable;
    @FXML
    private TableColumn<Worker, String> surnameColumn;
    @FXML
    private TableColumn<Worker, String> nameColumn;
    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private ChoiceBox<String> hardwareCheckbox;
    @FXML
    private ChoiceBox<String> softwareCheckbox;

    private MainApp mainApp;
    private WorkerDAO workerDAO;

    @FXML
    private void initialize(){
        workerDAO = new WorkerDAOImpl();
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        populateWorkers();
        workerTable.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> showWorkerDetails((Worker) newValue));
    }

    private void populateWorkers(){
        ObservableList<Worker> workers = workerDAO.findAll();
        workerTable.setItems(workers);
    }

    private void showWorkerDetails(Worker worker) {
        if (worker != null){
            nameLabel.setText(worker.getName());
            emailLabel.setText(worker.getEmail());
        }
    }

}
