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
    public String findById(Integer id) {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM manufacturer WHERE id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                return String.valueOf(instantiateManufacturer(resultSet));
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
        return null;
    }

    @Override
    public int findByName(String name) {
        return 0;
    }

    private Manufacturer instantiateManufacturer(ResultSet resultSet) throws SQLException{
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getInt("manufacturer_id"));
        manufacturer.setName(resultSet.getString("vendor_name"));
        manufacturer.setEmail(resultSet.getString("email"));
        manufacturer.setMobileNumber(resultSet.getString("mobile_number"));

        return manufacturer;
    }
}
