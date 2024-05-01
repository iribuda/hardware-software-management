package whz.dbii.software_hardware_verwaltung.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.MainApp;
import whz.dbii.software_hardware_verwaltung.model.Worker;

import java.io.IOException;

public class MainPageController {
    @FXML
    public void handleShowSoftware(ActionEvent actionEvent) {
        showSoftwareOverview();
    }
    @FXML
    public void handleShowLicense(ActionEvent actionEvent) {
        showLicenseOverview();
    }
    @FXML
    public void handleShowWorkers(ActionEvent actionEvent) {
        showWorkerOverview();
    }

    private BorderPane rootLayout;
    private MainApp mainApp;
    private Stage primaryStage;

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void showVendorOverview(){
        try{
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("vendor-overview.fxml"));
            SplitPane vendorOverview = (SplitPane) loader.load();
            rootLayout.setCenter(vendorOverview);
            VendorOverviewController vendorOverviewController = loader.getController();
            vendorOverviewController.setMainApp(mainApp);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void showSoftwareOverview(){
        try{
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("software-overview.fxml"));
            SplitPane softwareOverview = (SplitPane) loader.load();
            rootLayout.setCenter(softwareOverview);
            SoftwareOverviewController softwareOverviewController = loader.getController();
            softwareOverviewController.setMainApp(mainApp);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void showLicenseOverview(){
        try{
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("license-overview.fxml"));
            SplitPane softwareOverview = (SplitPane) loader.load();
            rootLayout.setCenter(softwareOverview);
            LicenceOverviewController licenceOverviewController = loader.getController();
            licenceOverviewController.setMainApp(mainApp);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showWorkerOverview(){
        try{
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("worker-overview.fxml"));
            SplitPane workerOverview = (SplitPane) loader.load();
            rootLayout.setCenter(workerOverview);
            WorkerOverviewController workerOverviewController = loader.getController();
            workerOverviewController.setMainApp(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
