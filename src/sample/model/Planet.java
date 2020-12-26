package sample.model;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.sql.Blob;

public class Planet {
    private String id;
    private String name;
    private String typePlanet;
    private String diameter;
    private String mass;
    private String rad;
    private String period;
    private String day;
    private String density;
    private String satellites;
    private String description;
    private String image;
    private ImageView imageBLOB;
    private Button buttons;
    Blob blob;

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public Planet() {

    }

    public Button getButtons() {
        return buttons;
    }

    public void setButtons(Button buttons) {
        this.buttons = buttons;
    }

    public Planet(String id, String name, String typePlanet, String diameter, String mass, String rad, String period, String day, String density, String satellites, String description, Blob blob, Button buttons) {
        this.name = name;
        this.typePlanet = typePlanet;
        this.diameter = diameter;
        this.mass = mass;
        this.rad = rad;
        this.period = period;
        this.day = day;
        this.density = density;
        this.satellites = satellites;
        this.description = description;
        this.blob = blob;
        this.buttons = buttons;
        this.id = id;
    }


    public Planet(String name, String typePlanet, String diameter, String mass, String rad, String period, String day, String density, String satellites, String description, String image) {
        this.name = name;
        this.typePlanet = typePlanet;
        this.diameter = diameter;
        this.mass = mass;
        this.rad = rad;
        this.period = period;
        this.day = day;
        this.density = density;
        this.satellites = satellites;
        this.description = description;
        this.image = image;
    }

    public Planet(String id, String name, String typePlanet, String diameter, String mass, String rad, String period, String day, String density, String satellites, String description, ImageView imageBLOB) {
        this.id = id;
        this.name = name;
        this.typePlanet = typePlanet;
        this.diameter = diameter;
        this.mass = mass;
        this.rad = rad;
        this.period = period;
        this.day = day;
        this.density = density;
        this.satellites = satellites;
        this.description = description;
        this.imageBLOB = imageBLOB;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypePlanet() {
        return typePlanet;
    }

    public void setTypePlanet(String typePlanet) {
        this.typePlanet = typePlanet;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getRad() {
        return rad;
    }

    public void setRad(String rad) {
        this.rad = rad;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getSatellites() {
        return satellites;
    }

    public void setSatellites(String satellites) {
        this.satellites = satellites;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ImageView getImageBLOB() {
        return imageBLOB;
    }

    public void setImageBLOB(ImageView imageBLOB) {
        this.imageBLOB = imageBLOB;
    }
}
