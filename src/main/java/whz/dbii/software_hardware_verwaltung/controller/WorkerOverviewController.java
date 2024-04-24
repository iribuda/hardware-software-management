package whz.dbii.software_hardware_verwaltung.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import whz.dbii.software_hardware_verwaltung.MainApp;
import whz.dbii.software_hardware_verwaltung.dao.worker.WorkerDAO;
import whz.dbii.software_hardware_verwaltung.dao.worker.impl.WorkerDAOImpl;
import whz.dbii.software_hardware_verwaltung.model.Worker;
import whz.dbii.software_hardware_verwaltung.model.software.Software;

import java.util.List;

public class WorkerOverviewController {

    @FXML
    private Label surnameLabel;
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
            surnameLabel.setText(worker.getSurname());
            emailLabel.setText(worker.getEmail());
            ObservableList<String> software = workerDAO.findNamesOfSoftwareOfWorker(worker.getId());
            softwareCheckbox.setItems(software);
            softwareCheckbox.setValue(softwareCheckbox.getItems().get(0));
            ObservableList<String> hardware = workerDAO.findNamesOfHardwareOfWorker(worker.getId());
            hardwareCheckbox.setItems(hardware);
            hardwareCheckbox.setValue(hardwareCheckbox.getItems().get(0));
        }
    }

    @FXML
    private void handleDeleteWorker() {
        int selectedIndex = workerTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            workerTable.getItems().remove(selectedIndex);
            workerDAO.delete(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("Keine Mitarbeiter wurde gewählt");
            alert.setContentText("Wählen Sie bitte einen Mitarbeiter");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewWorker(){
        Worker worker = new Worker();
        boolean okClicked = mainApp.showWorkerEditDialog(worker);
        if (okClicked){
            workerDAO.save(worker);
            workerTable.getItems().add(worker);
        }
    }

    @FXML
    private void handleEditWorker(){
        Worker selectedWorker = workerTable.getSelectionModel().getSelectedItem();
        if (selectedWorker != null){
            boolean okClicked = mainApp.showWorkerEditDialog(selectedWorker);
            if (okClicked){
                workerDAO.update(selectedWorker);
                showWorkerDetails(selectedWorker);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("No Selection");
                alert.setHeaderText("No Person Selected");
                alert.setContentText("Please select a person in the table.");

                alert.showAndWait();
            }
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
