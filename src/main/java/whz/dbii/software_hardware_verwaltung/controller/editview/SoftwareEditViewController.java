package whz.dbii.software_hardware_verwaltung.controller.editview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.dao.software.SoftwareDao;
import whz.dbii.software_hardware_verwaltung.dao.software.VendorDao;
import whz.dbii.software_hardware_verwaltung.dao.software.impl.SoftwareDaoImpl;
import whz.dbii.software_hardware_verwaltung.dao.software.impl.VendorDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.software.Software;

public class SoftwareEditViewController {
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField versionTextField;
    @FXML
    public ChoiceBox<String> vendorCheckbox;

    private Stage dialogStage;
    private Software software;
    private boolean isOkClicked = false;
    private SoftwareDao softwareDao;
    private VendorDao vendorDao;

    @FXML
    private void initialize() {
        this.softwareDao = new SoftwareDaoImpl();
        this.vendorDao = new VendorDaoImpl();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setSoftware(Software software) {
        this.software = software;
        nameTextField.setText(software.getName());
        versionTextField.setText(software.getVersion());
        vendorCheckbox.setItems(vendorDao.findAllVendorNames());
        if (software.getVendor() != null)
            vendorCheckbox.setValue(software.getVendor().getName());
    }

    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();
        if (nameTextField.getText() == null || nameTextField.getText().isEmpty())
            errorMessage.append("Bitte geben Sie einen gültigen Namen ein!\n");
        if (versionTextField.getText() == null || versionTextField.getText().isEmpty())
            errorMessage.append("Bitte geben Sie eine gültige Version ein!\n");
        if (vendorCheckbox.getValue() == null || vendorCheckbox.getValue().isEmpty())
            errorMessage.append("Bitte wählen Sie einen gültigen Anbieter aus!\n");

        if (errorMessage.isEmpty()) return true;

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(dialogStage);
        alert.setTitle("Ungültige Felder");
        alert.setHeaderText("Bitte korrigieren Sie die ungültigen Felder");
        alert.setContentText(errorMessage.toString());
        alert.showAndWait();

        return false;
    }

    public boolean isOkClicked() {
        return isOkClicked;
    }

    @FXML
    public void handleCancel(ActionEvent actionEvent) {
        dialogStage.close();
    }

    @FXML
    public void handleOk(ActionEvent actionEvent) {
        if (!isInputValid()) return;

        software.setName(nameTextField.getText());
        software.setVersion(versionTextField.getText());
        int vendorId = vendorDao.findIdByName(vendorCheckbox.getValue());
        software.setVendor(vendorDao.findById(vendorId));

        isOkClicked = true;
        dialogStage.close();
    }
}
