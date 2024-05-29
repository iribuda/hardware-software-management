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
import whz.dbii.software_hardware_verwaltung.dao.DBConnection;
import whz.dbii.software_hardware_verwaltung.dao.worker.WorkerDAO;
import whz.dbii.software_hardware_verwaltung.dao.worker.impl.WorkerDAOImpl;
import whz.dbii.software_hardware_verwaltung.model.Worker;
import whz.dbii.software_hardware_verwaltung.model.xml.WorkerList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkerOverviewController {

    @FXML
    public Button btn_new;
    @FXML
    public Button btn_delete;
    @FXML
    public Button btn_edit;
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

        controlRights();
    }

    private void controlRights() {
        if (DBConnection.hasDeleteRights()) {
            btn_delete.setVisible(true);
            btn_edit.setVisible(true);
            btn_new.setVisible(true);
        } else {
            btn_delete.setVisible(false);
            btn_edit.setVisible(DBConnection.hasWriteRights());
            btn_new.setVisible(DBConnection.hasWriteRights());
        }
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
            if (!softwareCheckbox.getItems().isEmpty())
                softwareCheckbox.setValue(softwareCheckbox.getItems().get(0));
            ObservableList<String> hardware = workerDAO.findNamesOfHardwareOfWorker(worker.getId());
            hardwareCheckbox.setItems(hardware);
            if (!hardwareCheckbox.getItems().isEmpty())
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
            alert.setTitle("Keine Auswahl");
            alert.setHeaderText("Keine Person ausgewählt");
            alert.setContentText("Bitte wählen Sie eine Person in der Tabelle aus.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleExport(){
        WorkerList workerList = new WorkerList(workerTable.getItems());
        OutputStream outputStream = null;
        try {
            JAXBContext context = JAXBContext.newInstance(WorkerList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            File file = new File("outputXML/workers.xml");
            outputStream = new FileOutputStream(file);
            marshaller.marshal(workerList, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainPageController.getPrimaryStage());
            alert.setTitle("XML fehlgeschlagen");
            alert.setHeaderText("XML wurde nicht erstellt");
            alert.setContentText("Bitte wählen Sie eine Person in der Tabelle aus.");

            alert.showAndWait();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(mainPageController.getPrimaryStage());
        alert.setTitle("XML Saved");
        alert.setHeaderText("XML was successfully created");
        alert.setContentText("You can read it after closing the program");

        alert.showAndWait();
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
