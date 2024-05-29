package whz.dbii.software_hardware_verwaltung.controller.editview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.dao.hardware.HardwareDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.OrderDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.impl.HardwareDaoImpl;
import whz.dbii.software_hardware_verwaltung.dao.hardware.impl.OrderDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.hardware.Order;
import whz.dbii.software_hardware_verwaltung.model.hardware.OrderStatus;

public class OrderEditViewController {

    @FXML
    public DatePicker orderDatePicker;
    @FXML
    public ChoiceBox<String> statusCheckbox;
    @FXML
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
            statuses.add(status.getStatus());
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
            errorMessage.append("Bitte geben Sie das Bestelldatum an!\n");
        if (statusCheckbox.getValue() == null || statusCheckbox.getValue().isEmpty())
            errorMessage.append("Bitte geben Sie den Bestellstatus an!\n");
        if (hardwareCheckbox.getValue() == null || hardwareCheckbox.getValue().isEmpty())
            errorMessage.append("Bitte geben Sie die Hardware für die Bestellung an!\n");

        if (errorMessage.isEmpty()) return true;

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(dialogStage);
        alert.setTitle("Ungültige Felder");
        alert.setHeaderText("Bitte korrigieren Sie die ungültigen Felder");
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
