package whz.dbii.software_hardware_verwaltung.controller.overview;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.MainApp;
import whz.dbii.software_hardware_verwaltung.controller.MainPageController;
import whz.dbii.software_hardware_verwaltung.controller.editview.WorkerEditController;
import whz.dbii.software_hardware_verwaltung.dao.worker.WorkerDAO;
import whz.dbii.software_hardware_verwaltung.dao.worker.impl.WorkerDAOImpl;
import whz.dbii.software_hardware_verwaltung.model.Worker;

import java.io.IOException;

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

    private MainPageController mainPageController;
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
            int workerId = workerTable.getSelectionModel().getSelectedItem().getId();
            workerTable.getItems().remove(selectedIndex);
            workerDAO.delete(workerId);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainPageController.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("Keine Mitarbeiter wurde gewählt");
            alert.setContentText("Wählen Sie bitte einen Mitarbeiter");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewWorker(){
        Worker worker = new Worker();
        boolean okClicked = showWorkerEditDialog(worker);
        if (okClicked){
            workerDAO.save(worker);
            workerTable.getItems().add(worker);
        }
    }

    @FXML
    private void handleEditWorker(){
        Worker selectedWorker = workerTable.getSelectionModel().getSelectedItem();
        if (selectedWorker != null){
            boolean okClicked = showWorkerEditDialog(selectedWorker);
            if (okClicked){
                workerDAO.update(selectedWorker);
                showWorkerDetails(selectedWorker);
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainPageController.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    public boolean showWorkerEditDialog(Worker worker){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("worker-edit-view.fxml"));
            SplitPane page = (SplitPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Bearbeitung des Mitarbeiters");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainPageController.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            WorkerEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setWorker(worker);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

}
