package whz.dbii.software_hardware_verwaltung;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.controller.*;
import whz.dbii.software_hardware_verwaltung.model.Worker;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setTitle("Software und Hardware Verwaltung");
//        initRootLayout();
        showAuthorizationPage();
    }

    public void showAuthorizationPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            rootLayout = (BorderPane) loader.load();

            LoginController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Authorization");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initRootLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("mainpage.fxml"));
            rootLayout = (BorderPane) loader.load();

            MainPageController controller = loader.getController();
            controller.setRootLayout(rootLayout);
            controller.setPrimaryStage(primaryStage);
            controller.setMainApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws JAXBException, JAXBException {
        launch();
    }
}