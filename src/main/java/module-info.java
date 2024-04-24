module whz.dbii.software_hardware_verwaltung {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports whz.dbii.software_hardware_verwaltung;
    exports whz.dbii.software_hardware_verwaltung.dao;
    exports whz.dbii.software_hardware_verwaltung.model;
    exports whz.dbii.software_hardware_verwaltung.model.software;
    exports whz.dbii.software_hardware_verwaltung.controller;
    exports whz.dbii.software_hardware_verwaltung.dao.worker;
    exports whz.dbii.software_hardware_verwaltung.dao.worker.impl;
    opens whz.dbii.software_hardware_verwaltung.dao.worker.impl to javafx.fxml;
    opens whz.dbii.software_hardware_verwaltung.dao to javafx.fxml;
    opens whz.dbii.software_hardware_verwaltung.controller to javafx.fxml;
    opens whz.dbii.software_hardware_verwaltung.dao.worker to javafx.fxml;
    opens whz.dbii.software_hardware_verwaltung to javafx.fxml;
}