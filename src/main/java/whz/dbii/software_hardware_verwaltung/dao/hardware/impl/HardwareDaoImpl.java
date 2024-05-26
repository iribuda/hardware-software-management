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
        String query = "SELECT * FROM hardware WHERE hardware_id = ?";

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
            throw new RuntimeException("Error occurred by connecting while getting the hardware: " + e.getMessage());
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
//            DBConnection.disconnect();
        }

        return null;
    }

    @Override
    public int findIdByName(String name) {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT hardware_id FROM hardware WHERE hardware_name=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int id = 0;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("hardware_id");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }

        return id;
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

    @Override
    public ObservableList<String> findAllNamesOfHardware() {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT hardware_name FROM hardware";
        Statement statement = null;
        ResultSet resultSet = null;
        ObservableList<String> hardware = FXCollections.observableArrayList();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next())
                hardware.add(resultSet.getString("hardware_name"));

        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the software list.");
        } finally {
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
//        hardware.setVersion(resultSet.getString("software_version"));

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
