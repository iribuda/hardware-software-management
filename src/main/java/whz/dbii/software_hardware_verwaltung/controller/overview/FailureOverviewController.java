package whz.dbii.software_hardware_verwaltung.controller.overview;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.MainApp;
import whz.dbii.software_hardware_verwaltung.controller.MainPageController;
import whz.dbii.software_hardware_verwaltung.controller.editview.FailureEditViewController;
import whz.dbii.software_hardware_verwaltung.controller.editview.OrderEditViewController;
import whz.dbii.software_hardware_verwaltung.dao.DBConnection;
import whz.dbii.software_hardware_verwaltung.dao.hardware.FailureDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.OrderDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.impl.FailureDaoImpl;
import whz.dbii.software_hardware_verwaltung.dao.hardware.impl.OrderDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.hardware.Failure;
import whz.dbii.software_hardware_verwaltung.model.hardware.Order;

import java.io.IOException;

public class FailureOverviewController {


    public TableView<Failure> failureTable;
    public TableColumn<Failure, String> hardwareColumn;
    public TableColumn<Failure, String> statusColumn;
    public TableColumn<Failure, String> typeColumn;

    public Label hardwareLabel;
    public Label failureLabel;
    public Label statusLabel;
    public Label workerLabel;
    public Label dateLabel;
    @FXML
    public Button btn_new;
    @FXML
    public Button btn_delete;
    @FXML
    public Button btn_edit;

    private MainPageController mainPageController;
    private FailureDao failureDao;

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    @FXML
    private void initialize(){
        failureDao = new FailureDaoImpl();
        hardwareColumn.setCellValueFactory(cellData -> cellData.getValue().hardwareNameProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        populateFailures();
        failureTable.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> showFailureDetails((Failure) newValue));

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

    private void showFailureDetails(Failure failure) {
        if (failure == null) return;
        hardwareLabel.setText(failure.getHardwareName());
        failureLabel.setText(failure.getType());
        statusLabel.setText(failure.getStatus().toString());
        dateLabel.setText(failure.getDate().toString());
        workerLabel.setText(failure.getWorkerName());
    }

    private void populateFailures() {
        ObservableList<Failure> failures = failureDao.findAll();
        failureTable.setItems(failures);
    }

    private boolean showFailureEditDialog(Failure failure) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("failure-edit-view.fxml"));
            SplitPane page = (SplitPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Adding/Editing the failure");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainPageController.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            FailureEditViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setFailure(failure);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Alert getFailureWasNotSelectedAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainPageController.getPrimaryStage());
        alert.setTitle("Keine Auswahl");
        alert.setHeaderText("Es wurde kein Fehler ausgewählt.");
        alert.setContentText("Bitte wählen Sie den Fehler!");

        return alert;
    }

    @FXML
    public void handleNewFailure(ActionEvent actionEvent) {
        Failure failure = new Failure();
        if (showFailureEditDialog(failure)) {
            failureDao.insert(failure);
            failureTable.getItems().add(failure);
//            populateFailures();
        }
    }
    @FXML
    public void handleDeleteFailure(ActionEvent actionEvent) {
        int selectedIdx = failureTable.getSelectionModel().getSelectedIndex();
        if (selectedIdx >= 0) {
            int failure_id = failureTable.getSelectionModel().getSelectedItem().getId();
            failureTable.getItems().remove(selectedIdx);
            failureDao.deleteById(failure_id);
        } else {
            getFailureWasNotSelectedAlert().showAndWait();
        }
    }
    @FXML
    public void handleEditFailure(ActionEvent actionEvent) {
        Failure selectedFailure = failureTable.getSelectionModel().getSelectedItem();
        if (selectedFailure != null) {
            if (showFailureEditDialog(selectedFailure)) {
                failureDao.update(selectedFailure);
                showFailureDetails(selectedFailure);
            }
        } else {
            getFailureWasNotSelectedAlert().showAndWait();
        }
    }

    @FXML
    private void handleExport(){}
}
