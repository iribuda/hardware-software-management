package whz.dbii.software_hardware_verwaltung.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import whz.dbii.software_hardware_verwaltung.MainApp;
import whz.dbii.software_hardware_verwaltung.controller.overview.*;

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
    @FXML
    public void handleShowVendor(ActionEvent actionEvent) { showVendorOverview(); }
    @FXML
    public void handleShowHardware(ActionEvent actionEvent) {
        showHardwareOverview();
    }

    @FXML
    public void handleShowOrders(ActionEvent actionEvent){ showOrderOverview();}
    @FXML
    public void handleShowFailure(ActionEvent actionEvent){
        showFailureOverview();
    }

    private BorderPane rootLayout;
    private Stage primaryStage;

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void showFailureOverview(){
        try{
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("failure-overview.fxml"));
            SplitPane workerOverview = (SplitPane) loader.load();
            rootLayout.setCenter(workerOverview);
            FailureOverviewController workerOverviewController = loader.getController();
            workerOverviewController.setMainPageController(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void showOrderOverview(){
        try{
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("order-overview.fxml"));
            SplitPane vendorOverview = (SplitPane) loader.load();
            rootLayout.setCenter(vendorOverview);
            OrderOverviewController orderOverviewController = loader.getController();
            orderOverviewController.setMainPageController(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void showVendorOverview(){
        try{
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("vendor-overview.fxml"));
            SplitPane vendorOverview = (SplitPane) loader.load();
            rootLayout.setCenter(vendorOverview);
            VendorOverviewController vendorOverviewController = loader.getController();
            vendorOverviewController.setMainPageController(this);
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
            softwareOverviewController.setMainPageController(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void showHardwareOverview(){
        try{
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("hardware-overview.fxml"));
            SplitPane hardwareOverview = (SplitPane) loader.load();
            rootLayout.setCenter(hardwareOverview);
            HardwareOverviewController hardwareOverviewController = loader.getController();
            hardwareOverviewController.setMainPageController(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void showLicenseOverview(){
        try{
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("license-overview.fxml"));
            SplitPane licenseOverview = (SplitPane) loader.load();
            rootLayout.setCenter(licenseOverview);
            LicenceOverviewController licenceOverviewController = loader.getController();
            licenceOverviewController.setMainPageController(this);
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
            workerOverviewController.setMainPageController(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
