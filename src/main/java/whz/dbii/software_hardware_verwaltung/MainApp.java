package whz.dbii.software_hardware_verwaltung;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.dao.DBConnection;
import whz.dbii.software_hardware_verwaltung.dao.WorkerDAO;
import whz.dbii.software_hardware_verwaltung.dao.WorkerDAOImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setTitle("Software und Hardware Verwaltung");
        initRootLayout();
        showWorkerOverview();
    }

    public void initRootLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("mainpage.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showWorkerOverview(){
        try{
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("worker-overview.fxml"));
            SplitPane workerOverview = (SplitPane) loader.load();
            rootLayout.setCenter(workerOverview);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}