package whz.dbii.software_hardware_verwaltung.controller.editview;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.controller.MainPageController;
import whz.dbii.software_hardware_verwaltung.dao.hardware.HardwareDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.ManufacturerDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.impl.HardwareDaoImpl;
import whz.dbii.software_hardware_verwaltung.dao.hardware.impl.ManufacturerDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.hardware.Hardware;

import java.awt.event.ActionEvent;

public class HardwareEditViewController {

    @FXML
    public TextField nameTextField;
    @FXML
    public TextField versionTextField;
    @FXML
    public ChoiceBox<String> manufactuterCheckbox;

    private Stage dialogStage;
    private Hardware hardware;
    private HardwareDao hardwareDao;
    private ManufacturerDao manufacturerDao;


    private MainPageController mainPageController;
    private boolean isOkClicked;

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }


    @FXML
    private void initialize() {
        this.hardwareDao = new HardwareDaoImpl();
        this.manufacturerDao = new ManufacturerDaoImpl();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
        nameTextField.setText(hardware.getName());
        versionTextField.setText(hardware.getVersion());
        manufactuterCheckbox.setItems(manufacturerDao.findAllManufacturerNames());
        if (hardware.getManufacturer() != null)
            manufactuterCheckbox.setValue(hardware.getManufacturer().getName());

    }
    private boolean isInputValid() {
        StringBuilder errowMessage = new StringBuilder();
        if (nameTextField.getText() == null || nameTextField.getText().isEmpty())
            errowMessage.append("Name cannot be empty\n");
        if(versionTextField.getText() == null || versionTextField.getText().isEmpty())
            errowMessage.append("Version cannot be empty\n");
        if (manufactuterCheckbox.getValue() == null || manufactuterCheckbox.getValue().isEmpty())
            errowMessage.append("Manufacturer cannot be empty\n");
        
        if(errowMessage.isEmpty()) return true;

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(dialogStage);
        alert.setTitle("invalid");
        alert.setHeaderText("Invalid name");
        alert.setContentText(errowMessage.toString());
        alert.showAndWait();
        
        return false;
    }
    public boolean isOkClicked(){
        return isOkClicked();
    }

    @FXML
    public void handleCancel(javafx.event.ActionEvent actionEvent) {
        dialogStage.close();
    }

    @FXML
    public void handleOk(javafx.event.ActionEvent actionEvent) {
        if (isInputValid()) return;

        hardware.setName(nameTextField.getText());
        hardware.setVersion(versionTextField.getText());
        int manufacturerId = manufacturerDao.findByName(manufactuterCheckbox.getValue());
        hardware.setVersion(manufacturerDao.findById(manufacturerId));

        isOkClicked = true;
        dialogStage.close();
    }
}
