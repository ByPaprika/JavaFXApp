package sample.controllers.data_base;

import com.sun.net.httpserver.Authenticator;
import sample.model.LogPacket;
import sample.model.Planet;
import sample.model.User;

import javax.imageio.ImageIO;
import javax.xml.transform.Result;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;


public class DataBaseHeandler extends Configs {
    Connection connection;

    public Connection connnectToDataBase() throws ClassCastException, SQLException {
        connection = DriverManager.getConnection(connectUrl, username, password);
        System.out.println("Connect to database");
        return connection;
    }

    public void addAccount(User user) {
        String insertAccount = "INSERT INTO" + " " + Const.USER_TABLE + "(" + Const.USER_LOGIN + "," + Const.USER_PASSWORD + "," + Const.USER_ROLE + "," + Const.USER_STATUS + ")" + "VALUES(?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connnectToDataBase().prepareStatement(insertAccount);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.setString(4, user.getStatus());
            preparedStatement.executeUpdate();
            addLogPacket(new LogPacket(user.getLogin(), "INSERT", Const.USER_TABLE, 1));
            System.out.println("add accounts");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ResultSet AccountLogin(User user) {
        ResultSet resultSet = null;
        String update = "UPDATE " + Const.USER_TABLE + " SET " + Const.USER_STATUS + "=(?)" + " WHERE " + Const.USER_LOGIN + "=(?)";
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_LOGIN + "=? AND " + Const.USER_PASSWORD + "=?";
        try {
            PreparedStatement preparedStatement = connnectToDataBase().prepareStatement(select);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                PreparedStatement StateUpdata = connnectToDataBase().prepareStatement(update);
                StateUpdata.setString(1, "Online");
                StateUpdata.setString(2, user.getLogin());
                StateUpdata.executeUpdate();
                addLogPacket(new LogPacket(user.getLogin(), "UPDATE", Const.USER_TABLE, 1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    public void exitFromAccount() {
        try {
            String update = "UPDATE " + Const.USER_TABLE + " SET " + Const.USER_STATUS + "=(?)" + " WHERE " + Const.USER_STATUS + "=(?)";

            PreparedStatement StateUpdata = connnectToDataBase().prepareStatement(update);
            StateUpdata.setString(1, "Offline");
            StateUpdata.setString(2, "Online");
            addLogPacket(new LogPacket("3", "UPDATE", Const.USER_TABLE, 10003));

            StateUpdata.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteAccount(int id) {
        String delete = "DELETE FROM " + Const.USER_TABLE + " WHERE " + Const.USER_ID + "=?";
        try {
            PreparedStatement preparedStatement = connnectToDataBase().prepareStatement(delete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            addLogPacket(new LogPacket("1", "delete", Const.USER_TABLE, id));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deletePlanet(int id) {
        String delete = "DELETE FROM " + Const.DESCRIPTION_TABLE + " WHERE " + Const.DESCRIPTION_ID + "=?";
        String delete2 = "DELETE FROM " + Const.IMAGE_TABLE + " WHERE " + Const.IMAGE_ID + "=?";
        String delete3 = "DELETE FROM " + Const.OPTIONS_TABLE + " WHERE " + Const.OPTIONS_ID + "=?";
        String delete4 = "DELETE FROM " + Const.PLANET_TABLE + " WHERE " + Const.PLANET_ID + "=?";
        String select = "SELECT * FROM " + Const.PLANET_TABLE + " WHERE " + Const.PLANET_ID + "=?";
        try {
            PreparedStatement preparedStatementSelect = connnectToDataBase().prepareStatement(select);
            preparedStatementSelect.setInt(1, id);
            ResultSet resultSet = preparedStatementSelect.executeQuery();
            while(resultSet.next()){
                PreparedStatement preparedStatement4 = connnectToDataBase().prepareStatement(delete4);
                preparedStatement4.setInt(1, id);
                preparedStatement4.executeUpdate();

                int description = resultSet.getInt(Const.PLANET_DESCRIPTION);
                PreparedStatement preparedStatement = connnectToDataBase().prepareStatement(delete);
                preparedStatement.setInt(1, description);
                preparedStatement.executeUpdate();

                int image = resultSet.getInt(Const.IMAGE_ID);
                PreparedStatement preparedStatement2 = connnectToDataBase().prepareStatement(delete2);
                preparedStatement2.setInt(1, image);
                preparedStatement2.executeUpdate();

                int option = resultSet.getInt(Const.OPTIONS_ID);
                PreparedStatement preparedStatement3 = connnectToDataBase().prepareStatement(delete3);
                preparedStatement3.setInt(1, option);
                preparedStatement3.executeUpdate();

            }

            addLogPacket(new LogPacket("1", "delete", Const.PLANET_TABLE, id));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteLog(int id) {
        String delete = "DELETE FROM " + Const.LOGPACKET_TABLE + " WHERE " + Const.LOGPACKET_ID + "=?";
        try {
            PreparedStatement preparedStatement = connnectToDataBase().prepareStatement(delete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            //addLogPacket(new LogPacket("1", "delete", Const.LOGPACKET_TABLE, id));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ResultSet getFullAccount() {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE;
        try {
            PreparedStatement preparedStatement = connnectToDataBase().prepareStatement(select);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getFullInfoPlanet() {
        ResultSet resultSet = null;
        String select = "SELECT " + "Planet.id, Planet.name, Planet.typePlanet, OprionsPlanet.diameter, OprionsPlanet.mass, OprionsPlanet.rad, OprionsPlanet.period, OprionsPlanet.day, OprionsPlanet.density, OprionsPlanet.satellites, DescriptionPlanet.description, ImagePlanet.image " + "FROM " + Const.PLANET_TABLE + " " +
                "JOIN " + Const.OPTIONS_TABLE + " ON " + Const.PLANET_OPTIONS + "=" + Const.OPTIONS_TABLE + (".") + Const.OPTIONS_ID + " " +
                "JOIN " + Const.DESCRIPTION_TABLE + " ON " + Const.PLANET_DESCRIPTION + "=" + Const.DESCRIPTION_TABLE + "." + Const.DESCRIPTION_ID + " " +
                "JOIN " + Const.IMAGE_TABLE + " ON " + Const.PLANET_IMGE + "=" + Const.IMAGE_TABLE + "." + Const.IMAGE_ID + " ";

        try {
            PreparedStatement preparedStatement = connnectToDataBase().prepareStatement(select);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getFilterInfoPlanet(String filter) {
        ResultSet resultSet = null;
        String select = "SELECT " + "Planet.id, Planet.name, Planet.typePlanet, OprionsPlanet.diameter, OprionsPlanet.mass, OprionsPlanet.rad, OprionsPlanet.period, OprionsPlanet.day, OprionsPlanet.density, OprionsPlanet.satellites, DescriptionPlanet.description, ImagePlanet.image " + "FROM " + Const.PLANET_TABLE + " " +
                "JOIN " + Const.OPTIONS_TABLE + " ON " + Const.PLANET_OPTIONS + "=" + Const.OPTIONS_TABLE + (".") + Const.OPTIONS_ID + " " +
                "JOIN " + Const.DESCRIPTION_TABLE + " ON " + Const.PLANET_DESCRIPTION + "=" + Const.DESCRIPTION_TABLE + "." + Const.DESCRIPTION_ID + " " +
                "JOIN " + Const.IMAGE_TABLE + " ON " + Const.PLANET_IMGE + "=" + Const.IMAGE_TABLE + "." + Const.IMAGE_ID + " " + "WHERE Planet.typePlanet=?";

        try {
            PreparedStatement preparedStatement = connnectToDataBase().prepareStatement(select);
            preparedStatement.setString(1, filter);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet GetLogPacket() {
        ResultSet resultSet = null;
        try {
            String select = "SELECT * FROM " + Const.LOGPACKET_TABLE;
            PreparedStatement preparedStatement = connnectToDataBase().prepareStatement(select);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void addItem(Planet planet) throws SQLException, IOException {
        String insertPlanet = "INSERT INTO" + " " + Const.PLANET_TABLE + "(" + Const.PLANET_NAME + "," + Const.PLANET_TYPE + "," + Const.PLANET_OPTIONS + "," + Const.PLANET_DESCRIPTION + "," + Const.PLANET_IMGE + ")" + "VALUES(?, ?, ?, ?, ?)";
        String insertDescription = "INSERT INTO" + " " + Const.DESCRIPTION_TABLE + "(" + Const.DESCRIPTION_INFO + ")" + "VALUES(?)";
        String insertOptions = "INSERT INTO" + " " + Const.OPTIONS_TABLE + "(" + Const.OPTIONS_DIAMETR + "," + Const.OPTIONS_MASS + "," + Const.OPTIONS_RAD + "," + Const.OPTIONS_PERIOD + "," + Const.OPTIONS_DAY + "," + Const.OPTIONS_DENSITY + "," + Const.OPTIONS_SATELLITES + ")" + " VALUES(?, ?, ?, ?, ?, ?, ?)";
        String insertImage = "INSERT INTO" + " " + Const.IMAGE_TABLE + "(" + Const.IMAGE_IMG + ")" + "VALUES(?)";
        int idDescription = 0;
        int idImage = 0;
        int idOption = 0;
        String selectGetId = "SELECT * FROM " + Const.DESCRIPTION_TABLE + " WHERE " + Const.DESCRIPTION_INFO + "=?";
        String selectGetId1 = "SELECT * FROM " + Const.IMAGE_TABLE + " WHERE " + Const.IMAGE_IMG + "=?";
        String selectGetId3 = "SELECT * FROM " + Const.OPTIONS_TABLE + " WHERE " + Const.OPTIONS_DIAMETR + "=?";

        try {
            BufferedImage image = ImageIO.read(new File(planet.getImage()));
            Blob blob = connnectToDataBase().createBlob();
            OutputStream outputStream = blob.setBinaryStream(1);
            ImageIO.write(image, "jpg", outputStream);
            PreparedStatement preparedStatement1 = connnectToDataBase().prepareStatement(insertImage);
            preparedStatement1.setBlob(1, blob);
            preparedStatement1.executeUpdate();

            PreparedStatement getIdImage = connnectToDataBase().prepareStatement(selectGetId1);
            getIdImage.setBlob(1, blob);
            ResultSet resultSet = getIdImage.executeQuery();
            while (resultSet.next()) {
                idImage = resultSet.getInt("id");
            }

            PreparedStatement preparedStatement2 = connnectToDataBase().prepareStatement(insertDescription);
            preparedStatement2.setString(1, planet.getDescription());
            preparedStatement2.executeUpdate();


            PreparedStatement getIdDescription = connnectToDataBase().prepareStatement(selectGetId);
            getIdDescription.setString(1, planet.getDescription());
            ResultSet resultSet1 = getIdDescription.executeQuery();
            while (resultSet1.next()) {
                idDescription = resultSet1.getInt("id");
            }
            PreparedStatement preparedStatement3 = connnectToDataBase().prepareStatement(insertOptions);
            preparedStatement3.setFloat(1, Float.parseFloat(planet.getDiameter()));
            preparedStatement3.setFloat(2, Float.parseFloat(planet.getMass()));
            preparedStatement3.setFloat(3, Float.parseFloat(planet.getRad()));
            preparedStatement3.setFloat(4, Float.parseFloat(planet.getPeriod()));
            preparedStatement3.setFloat(5, Float.parseFloat(planet.getDay()));
            preparedStatement3.setInt(6, Integer.parseInt(planet.getDensity()));
            preparedStatement3.setInt(7, Integer.parseInt(planet.getSatellites()));

            preparedStatement3.executeUpdate();

            PreparedStatement getIdOptions = connnectToDataBase().prepareStatement(selectGetId3);
            getIdOptions.setFloat(1, Float.parseFloat(planet.getDiameter()));
            ResultSet resultSet3 = getIdOptions.executeQuery();
            while (resultSet3.next()) {
                idOption = resultSet3.getInt("id");
            }


            PreparedStatement preparedStatement = connnectToDataBase().prepareStatement(insertPlanet);
            preparedStatement.setString(1, planet.getName());
            preparedStatement.setString(2, planet.getTypePlanet());
            preparedStatement.setInt(3, idOption);
            preparedStatement.setInt(4, idDescription);
            preparedStatement.setInt(5, idImage);
            preparedStatement.executeUpdate();


            addLogPacket(new LogPacket("1", "insert", Const.PLANET_TABLE + " " + Const.DESCRIPTION_TABLE + " " + Const.IMAGE_TABLE + " " + Const.OPTIONS_TABLE, idOption));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addLogPacket(LogPacket logPacket) {
        try {
            String insert = "INSERT INTO" + " " + Const.LOGPACKET_TABLE + "(" + Const.LOGPACKET_ACCOUNT + "," + Const.LOGPACKET_REQUEST + "," + Const.LOGPACKET_TABLECHANGE + "," + Const.LOGPACKET_IDCHANGE + ")" + "VALUES(?, ?, ?, ?)";
            PreparedStatement preparedStatement = connnectToDataBase().prepareStatement(insert);
            preparedStatement.setString(1, logPacket.getAccount());
            preparedStatement.setString(2, logPacket.getRequest());
            preparedStatement.setString(3, logPacket.getTableChange());
            System.out.println(logPacket.getIdChange());
            preparedStatement.setInt(4, logPacket.getIdChange());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
