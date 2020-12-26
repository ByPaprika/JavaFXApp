package sample.controllers;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sample.controllers.data_base.DataBaseHeandler;
import sample.model.Planet;


public class UserController {
    private ObservableList<Planet> data = FXCollections.observableArrayList();
    private int clothes = 0;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane loginSign;

    @FXML
    private TableView<Planet> table;

    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private TableColumn<Planet, String> nameCol;

    @FXML
    private TableColumn<Planet, String> typePlanetCol;

    @FXML
    private TextField typePlanet;

    @FXML
    private TableColumn<Planet, Button> button;

    @FXML
    private Button ExitButton;

    @FXML
    private Button buttonSearch;

    @FXML
    private Button filter;

    @FXML
    private TextField search;

    @FXML
    private Label type;

    private Button cellButton;

    @FXML
    private Label description;

    @FXML
    private Label diameter;

    @FXML
    private Label mass;

    @FXML
    private Label rad;

    @FXML
    private Label period;

    @FXML
    private Label day;

    @FXML
    private Label density;

    @FXML
    private Label satellites;

    @FXML
    void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<Planet, String>("name"));
        typePlanetCol.setCellValueFactory(new PropertyValueFactory<Planet, String>("typePlanet"));
        button.setCellValueFactory(new PropertyValueFactory<Planet, Button>("buttons"));
        table.setItems(data);
        DataBaseHeandler dataBaseHeandler = new DataBaseHeandler();
        ResultSet resultSet = dataBaseHeandler.getFullInfoPlanet();
        initData(resultSet);
        ExitButton.setOnAction(actionEvent -> {
            dataBaseHeandler.exitFromAccount();
            Stage stage = (Stage) ExitButton.getScene().getWindow();
            stage.close();
        });

        filter.setOnAction(actionEvent -> {
            ResultSet result = dataBaseHeandler.getFilterInfoPlanet(typePlanet.getText().trim());
            data.clear();
            clothes = 0;
            initData(result);
            if (typePlanet.getText().trim().equals("")) {
                ResultSet result3 = dataBaseHeandler.getFullInfoPlanet();
                initData(result3);
            }
        });

        buttonSearch.setOnAction(actionEvent -> {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getName().equals(search.getText())) {
                    setName(data.get(i).getName());
                    setType(data.get(i).getTypePlanet());
                    setDescription(data.get(i).getDescription());
                    setDiameter(data.get(i).getDiameter());
                    setMass(data.get(i).getMass());
                    setRad(data.get(i).getRad());
                    setPeriod(data.get(i).getPeriod());
                    setDay(data.get(i).getDay());
                    setDensity(data.get(i).getDensity());
                    setSatellites(data.get(i).getSatellites());
                    setImage(data.get(i).getBlob());
                }
            }
        });

    }


    private void setName(String get) {
        nameLabel.setText("Название: " + get);
    }

    private void setType(String get) {
        type.setText("Тип: " + get);
    }

    private void setDescription(String get) {
        description.setWrapText(true);
        description.setText("Описание: " + get);
    }

    private void setDiameter(String get) {
        diameter.setText("Диаметр," + "относительно: " + get);
    }

    private void setMass(String get) {
        mass.setText("Масса," + "относительно: " + get);
    }

    private void setRad(String get) {
        rad.setText("Орбитальный радиус: " + get);
    }

    private void setPeriod(String get) {
        period.setText("Период обращения, земных лет: " + get);
    }

    private void setDay(String get) {
        day.setText("Сутки," + "относительно: " + get);
    }

    private void setDensity(String get) {
        density.setText("Плотность, кг/м: " + get);
    }

    private void setSatellites(String get) {
        satellites.setText("Спутники: " + get);
    }

    private void setImage(Blob blob) {
        try {
            byte[] ndata = blob.getBytes(1, (int) blob.length());
            Image image2 = new Image(new ByteArrayInputStream(ndata));
            img.setImage(image2);
            img.setFitWidth(202);
            img.setFitHeight(201);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private void clickToButton(int i) {
        int finalI = i;
        cellButton.setOnAction(actionEvent -> {
            Planet planet = table.getItems().get(finalI);
            setName(planet.getName());
            setType(planet.getTypePlanet());
            setDescription(planet.getDescription());
            setDiameter(planet.getDiameter());
            setMass(planet.getMass());
            setRad(planet.getRad());
            setPeriod(planet.getPeriod());
            setDay(planet.getDay());
            setDensity(planet.getDensity());
            setSatellites(planet.getSatellites());
            setImage(planet.getBlob());
        });
    }


    private void initData(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                cellButton = new Button("Подробнее");
                clickToButton(clothes);
                clothes++;
                data.add(new Planet(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("typePlanet"), resultSet.getString("diameter"), resultSet.getString("mass"), resultSet.getString("rad"), resultSet.getString("period"), resultSet.getString("day"), resultSet.getString("density"), resultSet.getString("satellites"), resultSet.getString("description"), resultSet.getBlob("image"), cellButton));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
