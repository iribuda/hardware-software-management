package whz.dbii.software_hardware_verwaltung.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.MainApp;
import whz.dbii.software_hardware_verwaltung.security.Credentials;
import whz.dbii.software_hardware_verwaltung.dao.DBConnection;

import java.sql.SQLException;

public class LoginController {
    @FXML
    public TextField login;
    @FXML
    public PasswordField password;

    private MainApp mainApp;
    private Stage primaryStage;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    private boolean authenticate(String username, String password) {
        try {
            DBConnection.authenticate(username, password);
            Credentials credentials = new Credentials(username, password);
            DBConnection.setCredentials(credentials);
            credentials.loadUserRoles();
            return true;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(primaryStage);
            alert.setTitle("Authentication failed");
            alert.setHeaderText("Please provide a valid credentials!");
            alert.showAndWait();
        }

        return false;
    }
    private boolean isInputValid() {
        String username = login.getText();
        String password = this.password.getText();
        StringBuilder errorMessage = new StringBuilder();

        if (username == null || username.isEmpty())
            errorMessage.append("Username must not be empty!\n");
        if (password == null || password.isEmpty())
            errorMessage.append("Password must not be empty!\n");

        if (errorMessage.isEmpty()) return true;

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(primaryStage);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please correct invalid fields");
        alert.setContentText(errorMessage.toString());
        alert.showAndWait();

        return false;
    }
    @FXML
    public void handleAuthorisation(ActionEvent actionEvent) {
        String username = login.getText();
        String password = this.password.getText();

        if (!isInputValid()) return;

        if (authenticate(username, password)) {
            mainApp.initRootLayout();
        }
    }
}
