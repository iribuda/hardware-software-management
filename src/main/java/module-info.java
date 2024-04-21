module whz.dbii.software_hardware_verwaltung {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens whz.dbii.software_hardware_verwaltung to javafx.fxml;
    exports whz.dbii.software_hardware_verwaltung;
    exports whz.dbii.software_hardware_verwaltung.dao;
    exports whz.dbii.software_hardware_verwaltung.model;
    opens whz.dbii.software_hardware_verwaltung.dao to javafx.fxml;
}