package whz.dbii.software_hardware_verwaltung.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.model.Worker;

public class WorkerEditController {
    
    @FXML
    private ChoiceBox softHardCheckbox;
    @FXML
    private ChoiceBox softwareCheckbox;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private ChoiceBox hardwareCheckbox;
    private Stage dialogStage;
    private Worker worker;
    private boolean okClicked = false;

    @FXML
    private void initialize(){}

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    public void setWorker(Worker worker){
        this.worker = worker;
        nameTextField.setText(worker.getName());
        surnameTextField.setText(worker.getSurname());
        emailTextField.setText(worker.getEmail());
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

}
