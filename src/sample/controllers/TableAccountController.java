package sample.controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import sample.controllers.data_base.DataBaseHeandler;
import sample.model.MessageError;
import sample.model.User;

public class TableAccountController {

    private ObservableList<User> data = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User, String> idColumn1;

    @FXML
    private TableColumn<User, String> login;

    @FXML
    private TableColumn<User, String> password;

    @FXML
    private TableColumn<User, String> role;

    @FXML
    private TableColumn<User, String> status;

    @FXML
    private TextField idDelete;

    @FXML
    private Button remove;

    @FXML
    private TextField loginField;

    @FXML
    private TextField roleField;

    @FXML
    private Button Add;

    @FXML
    private TextField passwordField;

    @FXML
    void initialize() {
        idColumn1.setCellValueFactory(new PropertyValueFactory<User, String>("idAcc"));
        login.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        password.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        role.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
        status.setCellValueFactory(new PropertyValueFactory<User, String>("Status"));
        table.setItems(data);
        table.setEditable(true);
        initDataAccounts();
        login.setCellFactory(TextFieldTableCell.forTableColumn());
        login.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<User, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<User, String> t) {
                        ((User) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setLogin(t.getNewValue());
                    }
                }

        );
        password.setCellFactory(TextFieldTableCell.forTableColumn());
        password.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<User, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<User, String> t) {
                        ((User) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPassword(t.getNewValue());

                    }
                }

        );

        role.setCellFactory(TextFieldTableCell.forTableColumn());
        role.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<User, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<User, String> t) {
                        ((User) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setRole(t.getNewValue());
                    }
                }

        );
        status.setCellFactory(TextFieldTableCell.forTableColumn());
        status.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<User, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<User, String> t) {
                        ((User) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setRole(t.getNewValue());
                    }
                }
        );
        remove.setOnAction(actionEvent -> {
            DataBaseHeandler dataBaseHeandler = new DataBaseHeandler();
            dataBaseHeandler.deleteAccount(Integer.parseInt(idDelete.getText()));
            for (int i = 0; i < data.size(); i++) {
                if(data.get(i).getId().equals(idDelete.getText())){
                    data.remove(i);
                }
            }

        });

        Add.setOnAction(actionEvent -> {
            if (!loginField.equals("") && !passwordField.equals("") && !roleField.equals("")) {
                DataBaseHeandler dataBaseHeandler = new DataBaseHeandler();
                User user = new User(loginField.getText(), passwordField.getText(), "offline", roleField.getText());
                dataBaseHeandler.addAccount(user);
                data.add(new User(login.getText(), password.getText(), "offline", role.getText()));
            } else {
                MessageError messageError = new MessageError(Add);
                messageError.playMessagerError();
            }
        });


    }


    private void initDataAccounts() {
        DataBaseHeandler dataBaseHeandler = new DataBaseHeandler();
        ResultSet resultSet = dataBaseHeandler.getFullAccount();

        try {
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id"));
                data.add(new User(resultSet.getString("id"), resultSet.getString("login"), resultSet.getString("password"), resultSet.getString("status"), resultSet.getString("role")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
