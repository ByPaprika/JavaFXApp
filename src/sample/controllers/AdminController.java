package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.controllers.data_base.DataBaseHeandler;

public class AdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button tablePlanet;

    @FXML
    private Button ExitButton;

    @FXML
    private Button tableAccount;

    @FXML
    private Button logPacket;

    @FXML
    void initialize() {
        tablePlanet.setOnAction(actionEvent -> {
            openNewScene("/sample/resоurse/tablePlanet.fxml");
        });
        tableAccount.setOnAction(actionEvent -> {
            openNewScene("/sample/resоurse/tableAccount.fxml");
        });
        logPacket.setOnAction(actionEvent -> {
            openNewScene("/sample/resоurse/tableLog.fxml");
        });
        ExitButton.setOnAction(actionEvent -> {
            DataBaseHeandler dataBaseHeandler = new DataBaseHeandler();
            dataBaseHeandler.exitFromAccount();
            Stage stage = (Stage) ExitButton.getScene().getWindow();
            stage.close();
        });

    }
    private void openNewScene(String window) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}
