package com.javafx.workshopjavafxjdbc;

import com.javafx.workshopjavafxjdbc.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemSeller;
    @FXML
    private MenuItem menuItemDepartment;
    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemSellerAction() {
        System.out.println("onMenuItemSellerAction");
    }

    @FXML
    public void onMenuItemDepartmentAction() {
        System.out.println("onMenuItemDepartmentAction");
    }

    @FXML
    public void onMenuItemAboutAction() {
        loadView();
    }

    @Override
    public void initialize(URL uri, ResourceBundle resources) {
    }

    private synchronized void loadView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javafx/workshopjavafxjdbc/About.fxml"));
            VBox newVbox = loader.load();

            Scene mainScene = Main.getMainScene();
            VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

            Node mainMenu = mainVbox.getChildren().get(0);
            mainVbox.getChildren().clear();
            mainVbox.getChildren().add(mainMenu);
            mainVbox.getChildren().addAll(newVbox.getChildren());
        } catch (IOException e) {
            Alerts.showAlert("IOException", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}