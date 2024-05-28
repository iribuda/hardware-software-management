package whz.dbii.software_hardware_verwaltung.controller.editview;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.controller.MainPageController;
import whz.dbii.software_hardware_verwaltung.dao.hardware.HardwareDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.ManufacturerDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.impl.HardwareDaoImpl;
import whz.dbii.software_hardware_verwaltung.dao.hardware.impl.ManufacturerDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.hardware.Hardware;
import whz.dbii.software_hardware_verwaltung.model.hardware.Warranty;

public class HardwareEditViewController {

    @FXML
    public TextField nameTextField;
    @FXML
    public ChoiceBox<String> manufacturerCheckbox;
    public DatePicker startDatePicker;
    public DatePicker expDatePicker;

    private Stage dialogStage;
    private Hardware hardware;
    private HardwareDao hardwareDao;
    private ManufacturerDao manufacturerDao;
    private boolean isOkClicked;

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
        manufacturerCheckbox.setItems(manufacturerDao.findAllManufacturerNames());
        if (hardware.getManufacturer() != null)
            manufacturerCheckbox.setValue(hardware.getManufacturer().getName());
        startDatePicker.setValue(hardware.getWarranty().getStartDate());
        expDatePicker.setValue(hardware.getWarranty().getExpirationDate());

    }
    private boolean isInputValid() {
        StringBuilder errowMessage = new StringBuilder();
        if (nameTextField.getText() == null || nameTextField.getText().isEmpty())
            errowMessage.append("Name cannot be empty\n");
        if (manufacturerCheckbox.getValue() == null || manufacturerCheckbox.getValue().isEmpty())
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
        return isOkClicked;
    }

    @FXML
    public void handleCancel(javafx.event.ActionEvent actionEvent) {
        dialogStage.close();
    }

    @FXML
    public void handleOk(javafx.event.ActionEvent actionEvent) {
        if (!isInputValid()) return;

        hardware.setName(nameTextField.getText());
        int manufacturerId = manufacturerDao.findByName(manufacturerCheckbox.getValue());
        hardware.setManufacturer(manufacturerDao.findById(manufacturerId));
        Warranty warranty = hardware.getWarranty();
        warranty.setStartDate(startDatePicker.getValue());
        warranty.setExpirationDate(expDatePicker.getValue());
        hardware.setWarranty(warranty);

        isOkClicked = true;
        dialogStage.close();
    }
}
