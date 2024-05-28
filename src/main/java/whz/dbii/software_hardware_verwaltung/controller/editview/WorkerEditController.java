package whz.dbii.software_hardware_verwaltung.controller.editview;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.dao.hardware.HardwareDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.impl.HardwareDaoImpl;
import whz.dbii.software_hardware_verwaltung.dao.software.SoftwareDao;
import whz.dbii.software_hardware_verwaltung.dao.software.impl.SoftwareDaoImpl;
import whz.dbii.software_hardware_verwaltung.dao.worker.WorkerDAO;
import whz.dbii.software_hardware_verwaltung.dao.worker.impl.WorkerDAOImpl;
import whz.dbii.software_hardware_verwaltung.model.Worker;
import whz.dbii.software_hardware_verwaltung.model.software.Software;

public class WorkerEditController {

    @FXML
    private GridPane hardwareGrid;
    @FXML
    private GridPane softwareGrid;
    @FXML
    private VBox rightPane;
    @FXML
    private TableView<Software> softwareTable;
    @FXML
    private TableColumn<Software, String> softwareNameColumn;
    @FXML
    private TableColumn<Software, String> softwareVersionColumn;
    @FXML
    private TableView<String> hardwareTable;
    @FXML
    private TableColumn<String, String> hardwareNameColumn;
    @FXML
    private ChoiceBox<String> softwareCheckbox;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private ChoiceBox<String> hardwareCheckbox;
    private Stage dialogStage;
    private Worker worker;
    private boolean okClicked = false;
    private SoftwareDao softwareDao;
    private WorkerDAO workerDAO;
    private HardwareDao hardwareDao;

    @FXML
    private void initialize(){
        softwareDao = new SoftwareDaoImpl();
        workerDAO = new WorkerDAOImpl();
        hardwareDao = new HardwareDaoImpl();
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    public void setWorker(Worker worker){
        this.worker = worker;
        nameTextField.setText(worker.getName());
        surnameTextField.setText(worker.getSurname());
        emailTextField.setText(worker.getEmail());
        softwareCheckbox.setItems(softwareDao.findAllNameOfSoftware());
        hardwareCheckbox.setItems(hardwareDao.findAllNamesOfAvailableHardware());
        System.out.println(worker.getId());
        if (worker.getId() != 0){
            softwareNameColumn.setCellValueFactory(cellDate -> cellDate.getValue().nameProperty());
            softwareVersionColumn.setCellValueFactory(cellDate -> cellDate.getValue().versionProperty());
            populateSoftware();

            hardwareNameColumn.setCellValueFactory((data -> new SimpleStringProperty(data.getValue())));
            populateHardware();
        }
        else{
            rightPane.setMaxWidth(0);
            softwareGrid.setVisible(false);
            hardwareGrid.setVisible(false);
        }

    }

    private void populateSoftware(){
        ObservableList<Software> software = workerDAO.findSoftwareOfWorker(worker.getId());
        softwareTable.setItems(software);
    }

    private void populateHardware(){
        ObservableList<String> hardware = workerDAO.findNamesOfHardwareOfWorker(worker.getId());
        hardwareTable.setItems(hardware);
    }

    public boolean isOkClicked(){
        return okClicked;
    }

    @FXML
    private void handleOk(){
        if (isInputValid()){
            worker.setName(nameTextField.getText());
            worker.setSurname(surnameTextField.getText());
            worker.setEmail(emailTextField.getText());
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    private boolean isInputValid(){
        String errorMsg = "";
        if (nameTextField.getText() == null || nameTextField.getText().isEmpty()){
            errorMsg += "Kein gültiger Vorname!\n";
        }
        if (surnameTextField.getText()==null || surnameTextField.getText().isEmpty()){
            errorMsg += "Kein gültiger Name!\n";
        }
        if (emailTextField.getText()==null || emailTextField.getText().isEmpty() || !emailTextField.getText().contains("@")){
            errorMsg += "Kein gültige E-Mail!\n";
        }
        if(errorMsg.isEmpty())
            return true;
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMsg);

            alert.showAndWait();

            return false;
        }
    }

    @FXML
    private void handleAddSoftware(){
        String softwareName = softwareCheckbox.getValue();
        if (!softwareName.isEmpty()){
            int softwareId = softwareDao.findIdByName(softwareName);
            if (workerDAO.addSoftwareToWorker(softwareId, worker.getId())){
                populateSoftware();
            }
        }
    }

    @FXML
    private void handleDeleteHaving(){}

    @FXML
    private void handleAddHardware(){
        String hardwareName = hardwareCheckbox.getValue();
        if (!hardwareName.isEmpty()){
            int hardwareId = hardwareDao.findIdByName(hardwareName);
            if (workerDAO.addHardwareToWorker(hardwareId, worker.getId())){
                populateHardware();
            }
        }
    }
}
