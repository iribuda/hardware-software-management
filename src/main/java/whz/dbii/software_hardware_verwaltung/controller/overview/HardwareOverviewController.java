package whz.dbii.software_hardware_verwaltung.controller.overview;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.MainApp;
import whz.dbii.software_hardware_verwaltung.controller.MainPageController;
import whz.dbii.software_hardware_verwaltung.controller.editview.HardwareEditViewController;
import whz.dbii.software_hardware_verwaltung.dao.DBConnection;
import whz.dbii.software_hardware_verwaltung.dao.hardware.HardwareDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.impl.HardwareDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.hardware.Hardware;

public class HardwareOverviewController {

    @FXML
    public TableView<Hardware> hardwareTable;
    @FXML
    public Label nameLabel;
    @FXML
    public Label manufacturerLabel;
    @FXML
    public Label warrantyStatusLabel;
    @FXML
    public Label warrantyStartDateLabel;
    @FXML
    public Label warrantyExpirationDateLabel;
    @FXML
    public TableColumn<Hardware, String> nameColumn;
    @FXML
    public Button btn_new;
    @FXML
    public Button btn_delete;
    @FXML
    public Button btn_edit;

    private MainPageController mainPageController;
    private HardwareDao hardwareDao;

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    @FXML
    private void initialize() {
        hardwareDao = new HardwareDaoImpl();
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        populateHardware();
        hardwareTable.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> showHardwareDetails((Hardware) newValue));
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

    private void showHardwareDetails(Hardware hardware) {
        if (hardware == null) return;
        nameLabel.setText(hardware.getName());
        manufacturerLabel.setText(hardware.getManufacturer().getName());
        warrantyStatusLabel.setText(hardware.getWarranty().getStatus());
        warrantyStartDateLabel.setText(hardware.getWarranty().getStartDate().toString());
        warrantyExpirationDateLabel.setText(hardware.getWarranty().getExpirationDate().toString());
    }

    private void populateHardware() {
        ObservableList<Hardware> hardware = hardwareDao.findAll();
        hardwareTable.setItems(hardware);
    }

    private boolean showHardwareEditDialog(Hardware hardware) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("hardware-edit-view.fxml"));
            SplitPane page = (SplitPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit hardware");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainPageController.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            HardwareEditViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setHardware(hardware);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Alert getHardwareWasNotSelectedAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainPageController.getPrimaryStage());
        alert.setTitle("No selection");
        alert.setHeaderText("no hardware was selected");
        alert.setContentText("Please select a hardware");

        return alert;
    }

    @FXML
    public void handleNewHardware(ActionEvent event) {
        Hardware hardware = new Hardware();

        if (showHardwareEditDialog(hardware)){
            hardwareDao.insert(hardware);
            hardwareTable.getItems().add(hardware);
            populateHardware();
        }
    }

    @FXML
    public void handleDeleteHardware(ActionEvent actionEvent) {
        int selectedIdx = hardwareTable.getSelectionModel().getSelectedIndex();
        if (selectedIdx >= 0) {
            int hardwareId = hardwareTable.getSelectionModel().getSelectedItem().getId();
            hardwareDao.deleteById(hardwareId);
            hardwareTable.getItems().remove(selectedIdx);
        } else {
            getHardwareWasNotSelectedAlert().showAndWait();
        }
    }

    @FXML
    public void handleEditHardware(ActionEvent actionEvent){
        Hardware selectedHardware = hardwareTable.getSelectionModel().getSelectedItem();
        if (selectedHardware != null){
            if (showHardwareEditDialog(selectedHardware)){
                hardwareDao.update(selectedHardware);
                showHardwareDetails(selectedHardware);
            }
        } else {
            getHardwareWasNotSelectedAlert().showAndWait();
        }
    }
}
