package whz.dbii.software_hardware_verwaltung.controller.editview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.dao.hardware.HardwareDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.OrderDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.impl.HardwareDaoImpl;
import whz.dbii.software_hardware_verwaltung.dao.hardware.impl.OrderDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.hardware.Order;
import whz.dbii.software_hardware_verwaltung.model.hardware.OrderStatus;
import whz.dbii.software_hardware_verwaltung.model.software.LicenseStatus;

public class OrderEditViewController {


    public DatePicker orderDatePicker;
    public ChoiceBox<String> statusCheckbox;
    public ChoiceBox<String> hardwareCheckbox;
    private Stage dialogStage;
    private Order order;
    private boolean isOkClicked = false;
    private OrderDao orderDao;
    private HardwareDao hardwareDao;

    @FXML
    private void initialize() {
        this.orderDao = new OrderDaoImpl();
        this.hardwareDao = new HardwareDaoImpl();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setOrder(Order order) {
        this.order = order;
        orderDatePicker.setValue(order.getOrder_date());
        ObservableList<String> statuses = FXCollections.observableArrayList();
        for (OrderStatus status : OrderStatus.values())
            statuses.add(status.toString());
        statusCheckbox.setItems(statuses);
        if (order.getOrder_status() != null)
            statusCheckbox.setValue(order.getOrder_status());
        hardwareCheckbox.setItems(hardwareDao.findAllNamesOfHardware());
        if (order.getHardware() != null)
            hardwareCheckbox.setValue(order.getHardware().getName());
    }
    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();
        if (orderDatePicker.getValue() == null)
            errorMessage.append("Please provide order date!\n");
        if (statusCheckbox.getValue() == null || statusCheckbox.getValue().isEmpty())
            errorMessage.append("Please provide order status!\n");
        if (hardwareCheckbox.getValue() == null || hardwareCheckbox.getValue().isEmpty())
            errorMessage.append("Please provide the hardware for order!\n");

        if (errorMessage.isEmpty()) return true;

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(dialogStage);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please correct invalid fields");
        alert.setContentText(errorMessage.toString());
        alert.showAndWait();

        return false;
    }

    public boolean isOkClicked() {
        return isOkClicked;
    }
    @FXML
    public void handleCancel(ActionEvent actionEvent) {
        dialogStage.close();
    }

    @FXML
    public void handleOk(ActionEvent actionEvent) {
        if (!isInputValid()) return;

        order.setOrder_date(orderDatePicker.getValue());
        order.setOrder_status(statusCheckbox.getValue());
        int hardwareId = hardwareDao.findIdByName(hardwareCheckbox.getValue());
        order.setHardware(hardwareDao.findById(hardwareId));

        isOkClicked = true;
        dialogStage.close();
    }
}