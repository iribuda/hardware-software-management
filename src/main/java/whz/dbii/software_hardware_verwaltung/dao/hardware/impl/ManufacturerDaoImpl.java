package whz.dbii.software_hardware_verwaltung.dao.hardware.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.dao.DBConnection;
import whz.dbii.software_hardware_verwaltung.dao.DBException;
import whz.dbii.software_hardware_verwaltung.dao.hardware.ManufacturerDao;
import whz.dbii.software_hardware_verwaltung.model.hardware.Manufacturer;

import java.sql.*;

public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer findById(Integer id) {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM manufacturer WHERE manufacturer_id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                return instantiateManufacturer(resultSet);
            }
        } catch (SQLException e) {
            throw new DBException("Error occured by connecting while getting the manufacturer.");
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return null;
    }

    @Override
    public ObservableList<Manufacturer> findAll() {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * " +
                "FROM manufacturer";
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the vendor list.");
        }

        ObservableList<Manufacturer> manufacturers = FXCollections.observableArrayList();
        try {
            while (resultSet.next())
                manufacturers.add(instantiateManufacturer(resultSet));
        } catch (SQLException e) {
            throw new DBException("Error occurred while getting the vendor list.");
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return manufacturers;
    }


    @Override
    public boolean insert(Manufacturer manufacturer) {
        Connection connection = DBConnection.getConnection();
        String query = "";

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getEmail());
            statement.setString(3, manufacturer.getMobileNumber());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    manufacturer.setId(resultSet.getInt(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while adding the vendor.");
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return false;
    }

    @Override
    public boolean update(Manufacturer manufacturer) {
        Connection connection = DBConnection.getConnection();
        String query = "UPDATE vendor " +
                "SET vendor_name = ?, email = ?, mobile_number = ? " +
                "WHERE vendor_id = ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getEmail());
            statement.setString(3, manufacturer.getMobileNumber());
            statement.setInt(4, manufacturer.getId());

            if (statement.executeUpdate() == 1)
                return true;
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while updating the vendor.");
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return false;
    }

    @Override
    public boolean delete(Manufacturer manufacturer) {
        return false;
    }

    @Override
    public ObservableList<String> findAllManufacturerNames() {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT manufacturer_name FROM manufacturer";
        Statement statement = null;
        ResultSet resultSet = null;
        ObservableList<String> manufacturers = FXCollections.observableArrayList();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next())
                manufacturers.add(resultSet.getString("manufacturer_name"));

        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the manufacturer list." + e.getMessage());
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return manufacturers;
    }

    @Override
    public int findByName(String name) {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT manufacturer_id FROM manufacturer WHERE manufacturer_name=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int id = 0;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("manufacturer_id");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }

        return id;
    }

    private Manufacturer instantiateManufacturer(ResultSet resultSet) throws SQLException{
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getInt("manufacturer_id"));
        manufacturer.setName(resultSet.getString("manufacturer_name"));
        manufacturer.setEmail(resultSet.getString("email"));
        manufacturer.setMobileNumber(resultSet.getString("mobile_number"));

        return manufacturer;
    }
}
