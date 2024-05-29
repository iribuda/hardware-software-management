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
import whz.dbii.software_hardware_verwaltung.controller.editview.VendorEditViewController;
import whz.dbii.software_hardware_verwaltung.dao.DBConnection;
import whz.dbii.software_hardware_verwaltung.dao.software.VendorDao;
import whz.dbii.software_hardware_verwaltung.dao.software.impl.VendorDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.software.Vendor;

import java.io.IOException;

public class VendorOverviewController {
    @FXML
    public Label emailLabel;
    @FXML
    public Label mobileLabel;
    @FXML
    public Label nameLabel;
    @FXML
    public Button btn_new;
    @FXML
    public Button btn_delete;
    @FXML
    public Button btn_edit;
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
    private boolean showVendorEditDialog(Vendor vendor) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("vendor-edit-view.fxml"));
            SplitPane page = (SplitPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Adding/Editing the vendor");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainPageController.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            VendorEditViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setVendor(vendor);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private Alert getVendorWasNotSelectedAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainPageController.getPrimaryStage());
        alert.setTitle("No Selection");
        alert.setHeaderText("No vendor was selected.");
        alert.setContentText("Please choose the vendor to delete!");

        return alert;
    }
    @FXML
    public void handleNewVendor(ActionEvent actionEvent) {
        Vendor vendor = new Vendor();
        // If the ok button is clicked
        if (showVendorEditDialog(vendor)) {
            vendorDao.insert(vendor);
            vendorTable.getItems().add(vendor);
        }
    }
    @FXML
    public void handleDeleteVendor(ActionEvent actionEvent) {
        int selectedIdx = vendorTable.getSelectionModel().getSelectedIndex();
        if (selectedIdx >= 0) {
            int vendorId = vendorTable.getSelectionModel().getSelectedItem().getId();
            vendorTable.getItems().remove(selectedIdx);
            vendorDao.deleteById(vendorId);
        } else {
            getVendorWasNotSelectedAlert().showAndWait();
        }
    }
    @FXML
    public void handleEditVendor(ActionEvent actionEvent) {
        Vendor selectedVendor = vendorTable.getSelectionModel().getSelectedItem();
        if (selectedVendor != null) {
            if (showVendorEditDialog(selectedVendor)) {
                vendorDao.update(selectedVendor);
                showVendorDetails(selectedVendor);
            }
        } else {
            getVendorWasNotSelectedAlert().showAndWait();
        }
    }

    @FXML
    public void handleExport(){}
}
