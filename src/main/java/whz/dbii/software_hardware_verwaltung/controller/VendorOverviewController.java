package whz.dbii.software_hardware_verwaltung.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import whz.dbii.software_hardware_verwaltung.MainApp;
import whz.dbii.software_hardware_verwaltung.dao.software.VendorDao;
import whz.dbii.software_hardware_verwaltung.dao.software.impl.VendorDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.Worker;
import whz.dbii.software_hardware_verwaltung.model.software.Vendor;

public class VendorOverviewController {
    @FXML
    public Label emailLabel;
    @FXML
    public Label mobileLabel;
    @FXML
    public Label nameLabel;
    @FXML
    private TableView<Vendor> vendorTable;
    @FXML
    private TableColumn<Vendor, String> nameColumn;

    private MainPageController mainPageController;
    private VendorDao vendorDao;

    @FXML
    private void initialize() {
        this.vendorDao = new VendorDaoImpl();
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        populateVendors();
        vendorTable.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> showVendorDetails((Vendor) newValue))
        );
    }
    private void populateVendors() {
        ObservableList<Vendor> vendors = vendorDao.findAll();
        vendorTable.setItems(vendors);
    }
    private void showVendorDetails(Vendor vendor) {
        if (vendor != null) {
            nameLabel.setText(vendor.getName());
            emailLabel.setText(vendor.getEmail());
            mobileLabel.setText(vendor.getMobileNumber());
        }
    }
    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }
    public void handleNewVendor(ActionEvent actionEvent) {
    }
    public void handleDeleteVendor(ActionEvent actionEvent) {
    }
    public void handleEditVendor(ActionEvent actionEvent) {
    }
}
