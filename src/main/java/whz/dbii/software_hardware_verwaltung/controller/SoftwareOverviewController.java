package whz.dbii.software_hardware_verwaltung.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import whz.dbii.software_hardware_verwaltung.MainApp;
import whz.dbii.software_hardware_verwaltung.dao.software.SoftwareDao;
import whz.dbii.software_hardware_verwaltung.dao.software.impl.SoftwareDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.software.Software;

public class SoftwareOverviewController {
    @FXML
    public Label nameLabel;
    @FXML
    public Label versionLabel;
    @FXML
    public Label vendorLabel;
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

    @FXML
    public void handleNewSoftware(ActionEvent actionEvent) {
    }

    @FXML
    public void handleDeleteSoftware(ActionEvent actionEvent) {
    }

    @FXML
    public void handleEditSoftware(ActionEvent actionEvent) {
    }
}
