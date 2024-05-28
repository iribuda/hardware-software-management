module whz.dbii.software_hardware_verwaltung {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    exports whz.dbii.software_hardware_verwaltung;
    exports whz.dbii.software_hardware_verwaltung.dao;
    exports whz.dbii.software_hardware_verwaltung.model;
    exports whz.dbii.software_hardware_verwaltung.controller;
    opens whz.dbii.software_hardware_verwaltung.dao to javafx.fxml;
    opens whz.dbii.software_hardware_verwaltung.controller to javafx.fxml;
    opens whz.dbii.software_hardware_verwaltung to javafx.fxml;
    exports whz.dbii.software_hardware_verwaltung.controller.overview;
    opens whz.dbii.software_hardware_verwaltung.controller.overview to javafx.fxml;
    exports whz.dbii.software_hardware_verwaltung.controller.editview;
    opens whz.dbii.software_hardware_verwaltung.controller.editview to javafx.fxml;
    exports whz.dbii.software_hardware_verwaltung.security;
    opens whz.dbii.software_hardware_verwaltung.security to javafx.fxml;
}