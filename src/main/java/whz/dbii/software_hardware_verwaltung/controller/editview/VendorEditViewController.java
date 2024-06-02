package whz.dbii.software_hardware_verwaltung.controller.editview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.dao.software.VendorDao;
import whz.dbii.software_hardware_verwaltung.dao.software.impl.VendorDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.software.Vendor;

public class VendorEditViewController {
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField emailTextField;
    @FXML
    public TextField mobileTextField;
    private Stage dialogStage;
    private Vendor vendor;
    private boolean isOkClicked = false;
    private VendorDao vendorDao;

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();
        if (nameTextField.getText() == null || nameTextField.getText().isEmpty())
            errorMessage.append("Bitte geben Sie einen gültigen Namen ein!\n");
        if (emailTextField.getText() == null || emailTextField.getText().isEmpty())
            errorMessage.append("Bitte geben Sie eine gültige E-Mail ein!\n");
        if (mobileTextField.getText() == null || mobileTextField.getText().isEmpty())
            errorMessage.append("Bitte geben Sie eine gültige Mobilnummer ein!");

        if (errorMessage.isEmpty()) return true;

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(dialogStage);
        alert.setTitle("Ungültige Felder");
        alert.setHeaderText("Bitte korrigieren Sie die ungültigen Felder");
        alert.setContentText(errorMessage.toString());
        alert.showAndWait();

        return false;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
        nameTextField.setText(vendor.getName());
        emailTextField.setText(vendor.getEmail());
        mobileTextField.setText(vendor.getMobileNumber());
    }

    public boolean isOkClicked() {
        return this.isOkClicked;
    }

    @FXML
    public void handleCancel(ActionEvent actionEvent) {
        dialogStage.close();
    }
    @FXML
    public void handleOk(ActionEvent actionEvent) {
        if (!isInputValid()) return;

        vendor.setName(nameTextField.getText());
        vendor.setEmail(emailTextField.getText());
        vendor.setMobileNumber(mobileTextField.getText());
        isOkClicked = true;
        dialogStage.close();
    }

    @FXML
    private void initialize() {
        this.vendorDao = new VendorDaoImpl();
    }
}
