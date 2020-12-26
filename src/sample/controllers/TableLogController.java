package sample.controllers;


import java.net.URL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.controllers.data_base.DataBaseHeandler;
import sample.model.LogPacket;


public class TableLogController {

    private ObservableList<LogPacket> data = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<LogPacket> table;

    @FXML
    private TableColumn<LogPacket, String> id;

    @FXML
    private TableColumn<LogPacket, String> account;

    @FXML
    private TableColumn<LogPacket, String> request;

    @FXML
    private TableColumn<LogPacket, String> tableChange;

    @FXML
    private TableColumn<LogPacket, String> idChange;

    @FXML
    private TextField idDelete;

    @FXML
    private Button remove;

    @FXML
    void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<LogPacket, String>("id"));
        account.setCellValueFactory(new PropertyValueFactory<LogPacket, String>("account"));
        request.setCellValueFactory(new PropertyValueFactory<LogPacket, String>("request"));
        tableChange.setCellValueFactory(new PropertyValueFactory<LogPacket, String>("tableChange"));
        idChange.setCellValueFactory(new PropertyValueFactory<LogPacket, String>("idChange"));
        table.setItems(data);
        table.setEditable(true);
        initDatalog();
        remove.setOnAction(actionEvent -> {
            DataBaseHeandler dataBaseHeandler = new DataBaseHeandler();
            dataBaseHeandler.deleteLog(Integer.parseInt(idDelete.getText()));
            for (int i = 0; i < data.size(); i++) {
                if(data.get(i).getId().equals(idDelete.getText())){
                    data.remove(i);
                }
            }

        });
    }
    private void initDatalog() {
        DataBaseHeandler dataBaseHeandler = new DataBaseHeandler();
        ResultSet resultSet = dataBaseHeandler.GetLogPacket();

        try {
            while (resultSet.next()) {
                data.add(new LogPacket(resultSet.getString("id"), resultSet.getString("account"), resultSet.getString("request"), resultSet.getString("tableChange"), resultSet.getInt("idChange")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
