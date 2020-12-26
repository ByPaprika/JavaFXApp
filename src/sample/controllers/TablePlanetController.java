package sample.controllers;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.controllers.data_base.DataBaseHeandler;
import sample.model.MessageError;
import sample.model.Planet;
import sample.model.User;

import javax.swing.text.html.ImageView;

public class TablePlanetController {

    private ObservableList<Planet> data = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Planet> table;

    @FXML
    private TableColumn<Planet, String> idColumn1;

    @FXML
    private TableColumn<Planet, String> name;

    @FXML
    private TableColumn<Planet, String> typePlanet;

    @FXML
    private TableColumn<Planet, String> diameter;

    @FXML
    private TableColumn<Planet, String> mass;

    @FXML
    private TableColumn<Planet, String> rad;

    @FXML
    private TableColumn<Planet, String> period;

    @FXML
    private TableColumn<Planet, String> day;

    @FXML
    private TableColumn<Planet, String> density;

    @FXML
    private TableColumn<Planet, String> satellites;

    @FXML
    private TableColumn<Planet, String> description;

    @FXML
    private TableColumn<Planet, ImageView> image;

    @FXML
    private TextField idSupreme;

    @FXML
    private TextField diameterField;

    @FXML
    private Button Add;

    @FXML
    private TextField imageBLOB;

    @FXML
    private TextField typePlanetField;

    @FXML
    private TextField idField;

    @FXML
    private TextField massField;

    @FXML
    private Button remove2;

    @FXML
    private TextField nameField;

    @FXML
    private TextField radField;

    @FXML
    private TextField periodField;

    @FXML
    private TextField dayField;

    @FXML
    private TextField densityField;

    @FXML
    private TextField satellitesField;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button openButton;

    @FXML
    void initialize() {


        openButton.setOnAction((final ActionEvent e) -> {
            Stage stage = new Stage();
            final FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            imageBLOB.setText(file.getPath());
        });
        remove2.setOnAction(actionEvent -> {
            DataBaseHeandler dataBaseHeandler = new DataBaseHeandler();
            dataBaseHeandler.deletePlanet(Integer.parseInt(idField.getText()));
            for (int i = 0; i < data.size(); i++) {
                if(data.get(i).getId().equals(idField.getText())){
                    data.remove(i);
                }
            }
        });

        table.setEditable(true);
        idColumn1.setCellValueFactory(new PropertyValueFactory<Planet, String>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Planet, String>("name"));
        typePlanet.setCellValueFactory(new PropertyValueFactory<Planet, String>("typePlanet"));
        diameter.setCellValueFactory(new PropertyValueFactory<Planet, String>("diameter"));
        mass.setCellValueFactory(new PropertyValueFactory<Planet, String>("mass"));
        rad.setCellValueFactory(new PropertyValueFactory<Planet, String>("rad"));
        period.setCellValueFactory(new PropertyValueFactory<Planet, String>("period"));
        day.setCellValueFactory(new PropertyValueFactory<Planet, String>("day"));
        density.setCellValueFactory(new PropertyValueFactory<Planet, String>("density"));
        satellites.setCellValueFactory(new PropertyValueFactory<Planet, String>("satellites"));
        description.setCellValueFactory(new PropertyValueFactory<Planet, String>("description"));
        image.setCellValueFactory(new PropertyValueFactory<Planet, ImageView>("imageBLOB"));
        table.setItems(data);
        name.setCellFactory(TextFieldTableCell.forTableColumn());

        name.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Planet, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Planet, String> t) {
                        ((Planet) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                    }
                }

        );
        typePlanet.setCellFactory(TextFieldTableCell.forTableColumn());
        typePlanet.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Planet, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Planet, String> t) {
                        ((Planet) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setTypePlanet(t.getNewValue());
                    }
                }

        );
        diameter.setCellFactory(TextFieldTableCell.forTableColumn());
        diameter.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Planet, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Planet, String> t) {
                        ((Planet) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDiameter(t.getNewValue());
                    }
                }

        );
        mass.setCellFactory(TextFieldTableCell.forTableColumn());
        mass.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Planet, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Planet, String> t) {
                        ((Planet) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setMass(t.getNewValue());
                    }
                }

        );
        rad.setCellFactory(TextFieldTableCell.forTableColumn());
        rad.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Planet, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Planet, String> t) {
                        ((Planet) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setRad(t.getNewValue());
                    }
                }

        );
        period.setCellFactory(TextFieldTableCell.forTableColumn());
        period.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Planet, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Planet, String> t) {
                        ((Planet) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPeriod(t.getNewValue());
                    }
                }

        );

        day.setCellFactory(TextFieldTableCell.forTableColumn());
        day.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Planet, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Planet, String> t) {
                        ((Planet) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDay(t.getNewValue());
                    }
                }

        );
        density.setCellFactory(TextFieldTableCell.forTableColumn());
        density.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Planet, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Planet, String> t) {
                        ((Planet) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDensity(t.getNewValue());
                    }
                }

        );
        satellites.setCellFactory(TextFieldTableCell.forTableColumn());
        satellites.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Planet, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Planet, String> t) {
                        ((Planet) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setSatellites(t.getNewValue());
                    }
                }

        );
        description.setCellFactory(TextFieldTableCell.forTableColumn());
        description.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Planet, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Planet, String> t) {
                        ((Planet) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDescription(t.getNewValue());
                    }
                }

        );

        Add.setOnAction(actionEvent -> {
            if (!description.equals("") && !satellites.equals("") && !period.equals("")) {
                DataBaseHeandler dataBaseHeandler = new DataBaseHeandler();
                Planet planet = new Planet(nameField.getText(), typePlanetField.getText(), diameterField.getText(), massField.getText(), radField.getText(), periodField.getText(), dayField.getText(), densityField.getText(), satellitesField.getText(), descriptionField.getText(), imageBLOB.getText());
                try {
                    dataBaseHeandler.addItem(planet);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                data.add(new Planet(nameField.getText(), typePlanetField.getText(), diameterField.getText(), massField.getText(), radField.getText(), periodField.getText(), dayField.getText(), densityField.getText(), satellitesField.getText(), descriptionField.getText(), imageBLOB.getText()));
            } else {
                MessageError messageError = new MessageError(Add);
                messageError.playMessagerError();
            }
        });
        initDataPlanet();
    }

    private void initDataPlanet() {
        DataBaseHeandler dataBaseHeandler = new DataBaseHeandler();
        ResultSet resultSet = dataBaseHeandler.getFullInfoPlanet();

        try {
            while (resultSet.next()) {
                Blob blob = resultSet.getBlob("image");
                byte[] ndata = blob.getBytes(1, (int) blob.length());
                Image image2 = new Image(new ByteArrayInputStream(ndata));
                javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView();
                imageView.setImage(image2);
                imageView.setFitWidth(170);
                imageView.setFitHeight(180);
                data.add(new Planet(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("typePlanet"), resultSet.getString("diameter"), resultSet.getString("mass"), resultSet.getString("rad"), resultSet.getString("period"), resultSet.getString("day"), resultSet.getString("density"), resultSet.getString("satellites"), resultSet.getString("description"), imageView));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
