package whz.dbii.software_hardware_verwaltung.dao.hardware.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.dao.DBConnection;
import whz.dbii.software_hardware_verwaltung.dao.DBException;
import whz.dbii.software_hardware_verwaltung.dao.hardware.HardwareDao;
import whz.dbii.software_hardware_verwaltung.model.hardware.Hardware;

import java.sql.*;

public class HardwareDaoImpl implements HardwareDao {

    @Override
    public Hardware findById(Integer id) {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM hardware WHERE id = ?";

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return instantiateHardware(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred by connecting while getting the hardware.");
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return null;
    }

    @Override
    public ObservableList<Hardware> findAll(){
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM hardware";
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the hardware.");
        }

        ObservableList<Hardware> hardware = FXCollections.observableArrayList();
        try {
            while (resultSet.next())
                hardware.add(instantiateHardware(resultSet));

        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the hardware.");
        }
        finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }
        return hardware;
    }

    private Hardware instantiateHardware(ResultSet resultSet) throws SQLException {
        Hardware hardware = new Hardware();
        hardware.setId(resultSet.getInt("hardware_id"));
        hardware.setName(resultSet.getString("hardware_name"));
        hardware.setVersion(resultSet.getString("software_version"));

        return hardware;
    }


    @Override
    public boolean insert(Hardware hardware) {
        Connection connection = DBConnection.getConnection();
        String query = "";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, hardware.getName());
            statement.setString(2, hardware.getVersion());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    hardware.setId(resultSet.getInt(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the hardware.");
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        Connection connection = DBConnection.getConnection();
        String query = "DELETE FROM hardware WHERE id = ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the hardware.");
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }
        return false;
    }

    @Override
    public boolean update(Hardware hardware) {
        Connection connection = DBConnection.getConnection();
        String query = "";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, hardware.getName());
            statement.setString(2, hardware.getVersion());
            statement.setInt(3, hardware.getManufacturer().getId());
            statement.setInt(4, hardware.getId());

            if (statement.executeUpdate() == 1) {
                return true;
            }
        }
        catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the hardware.");
        }
        finally {
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }
        return false;
    }
}
