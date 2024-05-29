package whz.dbii.software_hardware_verwaltung.controller.editview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.dao.software.LicenseDao;
import whz.dbii.software_hardware_verwaltung.dao.software.SoftwareDao;
import whz.dbii.software_hardware_verwaltung.dao.software.impl.LicenseDaoImpl;
import whz.dbii.software_hardware_verwaltung.dao.software.impl.SoftwareDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.software.License;
import whz.dbii.software_hardware_verwaltung.model.software.LicenseStatus;

public class LicenseEditViewController {
    @FXML
    public TextField keyTextField;
    @FXML
    public ChoiceBox<String> statusCheckbox;
    @FXML
    public TextField priceTextField;
    @FXML
    public ChoiceBox<String> softwareCheckbox;
    @FXML
    public DatePicker startDatePicker;
    @FXML
    public DatePicker expirationDatePicker;
    @FXML
    public DatePicker purchaseDatePicker;

    private Stage dialogStage;
    private License license;
    private boolean isOkClicked = false;
    private LicenseDao licenseDao;
    private SoftwareDao softwareDao;

    @FXML
    private void initialize() {
        this.licenseDao = new LicenseDaoImpl();
        this.softwareDao = new SoftwareDaoImpl();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setLicense(License license) {
        this.license = license;
        keyTextField.setText(String.valueOf(license.getKey()));
        startDatePicker.setValue(license.getStartDate());
        expirationDatePicker.setValue(license.getExpirationDate());
        purchaseDatePicker.setValue(license.getPurchaseDate());
        ObservableList<String> statuses = FXCollections.observableArrayList();
        for (LicenseStatus status : LicenseStatus.values())
            statuses.add(status.toString());
        statusCheckbox.setItems(statuses);
        if (license.getStatus() != null)
            statusCheckbox.setValue(license.getStatus());
        priceTextField.setText(String.valueOf(license.getPrice()));
        softwareCheckbox.setItems(softwareDao.findAllNameOfSoftware());
        if (license.getSoftware() != null)
            softwareCheckbox.setValue(license.getSoftware().getName());
    }
    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();
        if (keyTextField.getText() == null || keyTextField.getText().isEmpty())
            errorMessage.append("Bitte geben Sie einen gültigen Lizenzschlüssel ein!\n");
        if (startDatePicker.getValue() == null)
            errorMessage.append("Bitte geben Sie ein Startdatum an!\n");
        if (expirationDatePicker.getValue() == null)
            errorMessage.append("Bitte geben Sie ein Ablaufdatum an!\n");
        if (purchaseDatePicker.getValue() == null)
            errorMessage.append("Bitte geben Sie ein Kaufdatum an!\n");
        if (statusCheckbox.getValue() == null || statusCheckbox.getValue().isEmpty())
            errorMessage.append("Bitte geben Sie den Lizenzstatus an!\n");
        if (priceTextField.getText() == null || priceTextField.getText().isEmpty() || !isNumeric(priceTextField.getText()))
            errorMessage.append("Bitte geben Sie einen gültigen Lizenzpreis ein!\n");
        if (softwareCheckbox.getValue() == null || softwareCheckbox.getValue().isEmpty())
            errorMessage.append("Bitte geben Sie die Software für die Lizenz an!\n");

        if (errorMessage.isEmpty()) return true;

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(dialogStage);
        alert.setTitle("Ungültige Felder");
        alert.setHeaderText("Bitte korrigieren Sie die ungültigen Felder");
        alert.setContentText(errorMessage.toString());
        alert.showAndWait();

        return false;
    }

    private static boolean isNumeric(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
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

        license.setKey(Integer.parseInt(keyTextField.getText()));
        license.setStartDate(startDatePicker.getValue());
        license.setExpirationDate(expirationDatePicker.getValue());
        license.setPurchaseDate(purchaseDatePicker.getValue());
        license.setStatus(statusCheckbox.getValue());
        license.setPrice(Float.parseFloat(priceTextField.getText()));
        int softwareId = softwareDao.findIdByName(softwareCheckbox.getValue());
        license.setSoftware(softwareDao.findById(softwareId));

        isOkClicked = true;
        dialogStage.close();
    }
}
