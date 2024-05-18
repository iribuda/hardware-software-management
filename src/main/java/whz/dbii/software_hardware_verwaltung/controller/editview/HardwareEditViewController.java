package whz.dbii.software_hardware_verwaltung.controller.editview;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.controller.MainPageController;
import whz.dbii.software_hardware_verwaltung.dao.hardware.HardwareDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.impl.HardwareDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.hardware.Hardware;

public class HardwareEditViewController {

    @FXML
    public TextField nameTextField;
    @FXML
    public TextField versionTextField;
    @FXML
    public ChoiceBox<String> warrantyCheckbox;

    private Stage dialogStage;
    private Hardware hardware;
    private HardwareDao hardwareDao;

    private MainPageController mainPageController;

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }


    @FXML
    private void initialize() {
        hardwareDao = new HardwareDaoImpl();

    }
}
