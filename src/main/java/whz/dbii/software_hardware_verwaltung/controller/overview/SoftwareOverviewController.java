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
import whz.dbii.software_hardware_verwaltung.controller.editview.SoftwareEditViewController;
import whz.dbii.software_hardware_verwaltung.dao.DBConnection;
import whz.dbii.software_hardware_verwaltung.dao.software.SoftwareDao;
import whz.dbii.software_hardware_verwaltung.dao.software.impl.SoftwareDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.software.Software;

import java.io.IOException;

public class SoftwareOverviewController {
    @FXML
    public Label nameLabel;
    @FXML
    public Label versionLabel;
    @FXML
    public Label vendorLabel;
    @FXML
    public Button btn_new;
    @FXML
    public Button btn_delete;
    @FXML
    public Button btn_edit;
    @FXML
    private TableView<Software> softwareTable;
    @FXML
    private TableColumn<Software, String> nameColumn;

    private MainPageController mainPageController;
    private SoftwareDao softwareDao;

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    @FXML
    private void initialize(){
        softwareDao = new SoftwareDaoImpl();
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        populateSoftware();
        softwareTable.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> showSoftwareDetails((Software) newValue));

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

    private void showSoftwareDetails(Software software) {
        if (software == null) return;
        nameLabel.setText(software.getName());
        versionLabel.setText(software.getVersion());
        vendorLabel.setText(software.getVendor().getName());
    }

    private void populateSoftware() {
        ObservableList<Software> software = softwareDao.findAll();
        softwareTable.setItems(software);
    }

    private boolean showSoftwareEditDialog(Software software) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("software-edit-view.fxml"));
            SplitPane page = (SplitPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Adding/Editing the software");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainPageController.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            SoftwareEditViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSoftware(software);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Alert getSoftwareWasNotSelectedAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainPageController.getPrimaryStage());
        alert.setTitle("Keine Auswahl");
        alert.setHeaderText("Es wurde keine Software ausgewählt.");
        alert.setContentText("Bitte wählen Sie die Software!");

        return alert;
    }

    @FXML
    public void handleNewSoftware(ActionEvent actionEvent) {
        Software software = new Software();
        // If the ok button is clicked
        if (showSoftwareEditDialog(software)) {
            softwareDao.insert(software);
            softwareTable.getItems().add(software);
        }
    }

    @FXML
    public void handleDeleteSoftware(ActionEvent actionEvent) {
        int selectedIdx = softwareTable.getSelectionModel().getSelectedIndex();
        if (selectedIdx >= 0) {
            int softwareId = softwareTable.getSelectionModel().getSelectedItem().getId();
            softwareTable.getItems().remove(selectedIdx);
            softwareDao.deleteById(softwareId);
        } else {
            getSoftwareWasNotSelectedAlert().showAndWait();
        }
    }

    @FXML
    public void handleEditSoftware(ActionEvent actionEvent) {
        Software selectedSoftware = softwareTable.getSelectionModel().getSelectedItem();
        if (selectedSoftware != null) {
            if (showSoftwareEditDialog(selectedSoftware)) {
                softwareDao.update(selectedSoftware);
                showSoftwareDetails(selectedSoftware);
            }
        } else {
            getSoftwareWasNotSelectedAlert().showAndWait();
        }
    }

    @FXML
    public void handleExport(){}
}
