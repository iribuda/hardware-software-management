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
import whz.dbii.software_hardware_verwaltung.controller.editview.LicenseEditViewController;
import whz.dbii.software_hardware_verwaltung.controller.MainPageController;
import whz.dbii.software_hardware_verwaltung.dao.software.LicenseDao;
import whz.dbii.software_hardware_verwaltung.dao.software.impl.LicenseDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.software.License;

import java.io.IOException;

public class LicenceOverviewController {
    @FXML
    public Label keyLabel;
    @FXML
    public Label startDateLabel;
    @FXML
    public Label expirationDateLabel;
    @FXML
    public Label purchaseDateLabel;
    @FXML
    public Label statusLabel;
    @FXML
    public Label priceLabel;
    @FXML
    public Label softwareLabel;
    @FXML
    private TableView<License> licenseTable;
    @FXML
    private TableColumn<License, String> keyColumn;

    private MainPageController mainPageController;
    private LicenseDao licenseDao;

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    @FXML
    private void initialize(){
        licenseDao = new LicenseDaoImpl();
        keyColumn.setCellValueFactory(cellData -> cellData.getValue().keyProperty().asString());
        populateLicenses();
        licenseTable.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> showLicenseDetails((License) newValue));
    }

    private void showLicenseDetails(License license) {
        if (license == null) return;
        keyLabel.setText(Integer.toString(license.getKey()));
        startDateLabel.setText(license.getStartDate().toString());
        expirationDateLabel.setText(license.getExpirationDate().toString());
        purchaseDateLabel.setText(license.getPurchaseDate().toString());
        statusLabel.setText(license.getStatus());
        priceLabel.setText(Float.toString(license.getPrice()));
        softwareLabel.setText(license.getSoftware().getName());
    }

    private void populateLicenses() {
        ObservableList<License> licenses = licenseDao.findAll();
        licenseTable.setItems(licenses);
    }

    private boolean showLicenseEditDialog(License license) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("license-edit-view.fxml"));
            SplitPane page = (SplitPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Adding/Editing the license");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainPageController.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            LicenseEditViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setLicense(license);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Alert getLicenseWasNotSelectedAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainPageController.getPrimaryStage());
        alert.setTitle("No Selection");
        alert.setHeaderText("No license was selected.");
        alert.setContentText("Please choose the license!");

        return alert;
    }

    @FXML
    public void handleNewLicense(ActionEvent actionEvent) {
        License license = new License();
        if (showLicenseEditDialog(license)) {
            licenseDao.insert(license);
            licenseTable.getItems().add(license);
        }
    }
    @FXML
    public void handleDeleteLicense(ActionEvent actionEvent) {
        int selectedIdx = licenseTable.getSelectionModel().getSelectedIndex();
        if (selectedIdx >= 0) {
            int licenseId = licenseTable.getSelectionModel().getSelectedItem().getId();
            licenseTable.getItems().remove(selectedIdx);
            licenseDao.deleteById(licenseId);
        } else {
            getLicenseWasNotSelectedAlert().showAndWait();
        }
    }
    @FXML
    public void handleEditLicense(ActionEvent actionEvent) {
        License selectedLicense = licenseTable.getSelectionModel().getSelectedItem();
        if (selectedLicense != null) {
            if (showLicenseEditDialog(selectedLicense)) {
                licenseDao.update(selectedLicense);
                showLicenseDetails(selectedLicense);
            }
        } else {
            getLicenseWasNotSelectedAlert().showAndWait();
        }
    }
}
