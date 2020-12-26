package sample.controllers;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.controllers.data_base.DataBaseHeandler;
import sample.model.MessageError;
import sample.model.User;

import static java.lang.Thread.sleep;


public class LoginPageController {
    private int errorCount = 0;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane loginSign;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginSignIn;

    @FXML
    private Button loginSignUp;

    @FXML
    private TextField login1;

    @FXML
    private PasswordField password1;

    @FXML
    private Label error;

    @FXML
    void initialize() {
        loginSignIn.setOnAction(actionEvent -> {
            if (!login.getText().equals("") && !password.getText().equals("")) {
                User user = new User(login.getText().trim(), password.getText().trim());
                loginUser(user);
            } else
                System.out.println("ERROR");
        });

        loginSignUp.setOnAction(actionEvent -> {
            DataBaseHeandler dataBaseHeandler = new DataBaseHeandler();
            User user = new User(login1.getText(), password1.getText(), "offline", "user");
            dataBaseHeandler.addAccount(user);
        });

    }

    private void loginUser(User user) {
        DataBaseHeandler dataBaseHeandler = new DataBaseHeandler();
        ResultSet resultSet = dataBaseHeandler.AccountLogin(user);

        int count = 0;
        try {
            while (resultSet.next()) {
                count++;
                if (count >= 1 && (resultSet.getString(4).equals("User") || resultSet.getString(4).equals("user"))) {
                    openNewScene("/sample/resоurse/user.fxml");
                } else if (count >= 1 && (resultSet.getString(4).equals("Admin") || resultSet.getString(4).equals("admin"))) {
                    openNewScene("/sample/resоurse/admin.fxml");
                }
            }
            if (count == 0) {
                MessageError messageError = new MessageError(login);
                MessageError messageError2 = new MessageError(password);
                messageError.playMessagerError();
                messageError2.playMessagerError();
                errorCount++;
                error.setText("Неправельный пароль, попытка " + errorCount);
                if (errorCount == 3) {
                    login.setEditable(false);
                    password.setEditable(false);
                    sleep(5000);
                    errorCount = 0;
                    login.setEditable(true);
                    password.setEditable(true);
                }
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void openNewScene(String window) {
        loginSignUp.getScene().getWindow().hide();
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
