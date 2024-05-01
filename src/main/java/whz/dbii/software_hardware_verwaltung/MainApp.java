package whz.dbii.software_hardware_verwaltung;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.controller.*;
import whz.dbii.software_hardware_verwaltung.model.Worker;

import java.io.IOException;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setTitle("Software und Hardware Verwaltung");
        initRootLayout();
    }

    public void initRootLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("mainpage.fxml"));
            rootLayout = (BorderPane) loader.load();

            MainPageController controller = loader.getController();
            controller.setRootLayout(rootLayout);
            controller.setMainApp(this);
            controller.setPrimaryStage(primaryStage);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }
}