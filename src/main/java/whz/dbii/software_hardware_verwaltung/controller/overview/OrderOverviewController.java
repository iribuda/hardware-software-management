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
import whz.dbii.software_hardware_verwaltung.controller.editview.LicenseEditViewController;
import whz.dbii.software_hardware_verwaltung.controller.editview.OrderEditViewController;
import whz.dbii.software_hardware_verwaltung.dao.DBConnection;
import whz.dbii.software_hardware_verwaltung.dao.hardware.OrderDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.impl.OrderDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.hardware.Order;

import java.io.IOException;

public class OrderOverviewController {

    public TableView<Order> orderTable;
    public TableColumn<Order, String> hardwareColumn;
    public TableColumn<Order, String> dateColumn;
    public Label hardwareLabel;
    public Label manufacturerLabel;
    public Label orderDateLabel;
    public Label statusLabel;
    @FXML
    public Button btn_new;
    @FXML
    public Button btn_delete;
    @FXML
    public Button btn_edit;
    private MainPageController mainPageController;
    private OrderDao orderDao;

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    @FXML
    private void initialize(){
        orderDao = new OrderDaoImpl();
        hardwareColumn.setCellValueFactory(cellData -> cellData.getValue().getHardware().nameProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().order_dateProperty().asString());
        populateLicenses();
        orderTable.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> showOrderDetails((Order) newValue));

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

    private void showOrderDetails(Order order) {
        if (order == null) return;
        hardwareLabel.setText(order.getHardware().getName());
        manufacturerLabel.setText(order.getHardware().getManufacturer().getName());
        orderDateLabel.setText(order.getOrder_date().toString());
        statusLabel.setText(order.getOrder_status());
    }

    private void populateLicenses() {
        ObservableList<Order> orders = orderDao.findAll();
        orderTable.setItems(orders);
    }

    private boolean showOrderEditDialog(Order order) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("order-edit-view.fxml"));
            SplitPane page = (SplitPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Adding/Editing the order");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainPageController.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            OrderEditViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setOrder(order);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Alert getOrderWasNotSelectedAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainPageController.getPrimaryStage());
        alert.setTitle("No Selection");
        alert.setHeaderText("No order was selected.");
        alert.setContentText("Please choose the order!");

        return alert;
    }

    @FXML
    public void handleNewOrder(ActionEvent actionEvent) {
        Order order = new Order();
        if (showOrderEditDialog(order)) {
            orderDao.insert(order);
            orderTable.getItems().add(order);
            populateLicenses();
        }
    }
    @FXML
    public void handleDeleteOrder(ActionEvent actionEvent) {
        int selectedIdx = orderTable.getSelectionModel().getSelectedIndex();
        if (selectedIdx >= 0) {
            int order_id = orderTable.getSelectionModel().getSelectedItem().getOrder_id();
            orderTable.getItems().remove(selectedIdx);
            orderDao.deleteById(order_id);
        } else {
            getOrderWasNotSelectedAlert().showAndWait();
        }
    }
    @FXML
    public void handleEditOrder(ActionEvent actionEvent) {
        Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            if (showOrderEditDialog(selectedOrder)) {
                orderDao.update(selectedOrder);
                showOrderDetails(selectedOrder);
            }
        } else {
            getOrderWasNotSelectedAlert().showAndWait();
        }
    }

    @FXML
    public void handleExport(){}
}
