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
import whz.dbii.software_hardware_verwaltung.dao.hardware.FailureDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.HardwareDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.impl.FailureDaoImpl;
import whz.dbii.software_hardware_verwaltung.dao.hardware.impl.HardwareDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.hardware.Failure;
import whz.dbii.software_hardware_verwaltung.model.hardware.FailureStatus;

public class FailureEditViewController {

    @FXML
    public DatePicker failureDatePicker;
    @FXML
    public ChoiceBox<String> statusCheckbox;
    @FXML
    public ChoiceBox<String> hardwareCheckbox;
    @FXML
    public TextField typeTextField;

    private Stage dialogStage;
    private Failure failure;
    private boolean isOkClicked = false;
    private FailureDao failureDao;
    private HardwareDao hardwareDao;

    @FXML
    private void initialize() {
        this.failureDao = new FailureDaoImpl();
        this.hardwareDao = new HardwareDaoImpl();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setFailure(Failure failure) {
        this.failure = failure;
        typeTextField.setText(failure.getType());
        failureDatePicker.setValue(failure.getDate());
        ObservableList<String> statuses = FXCollections.observableArrayList();
        for (FailureStatus status : FailureStatus.values())
            statuses.add(status.getStatus());
        statusCheckbox.setItems(statuses);
        if (failure.getStatus() != null)
            statusCheckbox.setValue(failure.getStatus().toString());
        hardwareCheckbox.setItems(hardwareDao.findAllNamesOfHardware());
        if (failure.getHardwareId() != 0)
            hardwareCheckbox.setValue(failure.getHardwareName());
    }

    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();
        if (typeTextField.getText() == null || typeTextField.getText().isEmpty())
            errorMessage.append("Bitte geben Sie den Fehlertyp an!\n");
        if (failureDatePicker.getValue() == null)
            errorMessage.append("Bitte geben Sie das Fehlerdatum an!\n");
        if (statusCheckbox.getValue() == null || statusCheckbox.getValue().isEmpty())
            errorMessage.append("Bitte geben Sie den Fehlerstatus an!\n");
        if (hardwareCheckbox.getValue() == null || hardwareCheckbox.getValue().isEmpty())
            errorMessage.append("Bitte geben Sie die Hardware für den Fehler an!\n");

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

        failure.setType(typeTextField.getText());
        failure.setDate(failureDatePicker.getValue());
        failure.setStatus(statusCheckbox.getValue());
        int hardwareId = hardwareDao.findIdByName(hardwareCheckbox.getValue());
        failure.setHardwareId(hardwareId);
        failure.setHardwareName(hardwareCheckbox.getValue());

        isOkClicked = true;
        dialogStage.close();
    }
}
