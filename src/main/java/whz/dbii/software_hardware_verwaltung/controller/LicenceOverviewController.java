package whz.dbii.software_hardware_verwaltung.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import whz.dbii.software_hardware_verwaltung.MainApp;
import whz.dbii.software_hardware_verwaltung.dao.software.LicenseDao;
import whz.dbii.software_hardware_verwaltung.dao.software.impl.LicenseDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.software.License;

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

    private MainApp mainApp;
    private LicenseDao licenseDao;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
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

    @FXML
    public void handleNewLicense(ActionEvent actionEvent) {
    }
    @FXML
    public void handleDeleteLicense(ActionEvent actionEvent) {
    }
    @FXML
    public void handleEditLicense(ActionEvent actionEvent) {
    }
}
