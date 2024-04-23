package whz.dbii.software_hardware_verwaltung.dao.software.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.dao.DBConnection;
import whz.dbii.software_hardware_verwaltung.dao.DBException;
import whz.dbii.software_hardware_verwaltung.dao.software.VendorDao;
import whz.dbii.software_hardware_verwaltung.model.software.Vendor;

import java.sql.*;

public class VendorDaoImpl implements VendorDao {
    @Override
    public Vendor findById(Integer id) {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM vendor " +
                "WHERE vendor_id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return instantiateVendor(resultSet);
            }
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the vendor.");
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return null;
    }

    @Override
    public ObservableList<Vendor> findAll() {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM vendor";
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the vendor list.");
        }

        ObservableList<Vendor> vendors = FXCollections.observableArrayList();
        try {
            while (resultSet.next())
                vendors.add(instantiateVendor(resultSet));
        } catch (SQLException e) {
            throw new DBException("Error occurred while getting the vendor list.");
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return vendors;
    }

    @Override
    public boolean insert(Vendor vendor) {
        Connection connection = DBConnection.getConnection();
        String query = "INSERT INTO vendor " +
                "(vendor_name, email, mobile_number) " +
                "VALUES (?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, vendor.getName());
            statement.setString(2, vendor.getEmail());
            statement.setString(3, vendor.getMobileNumber());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    vendor.setId(resultSet.getInt(1));
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
    public boolean update(Vendor vendor) {
        Connection connection = DBConnection.getConnection();
        String query = "UPDATE vendor " +
                "SET vendor_name = ?, email = ?, mobile_number = ? " +
                "WHERE vendor_id = ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, vendor.getName());
            statement.setString(2, vendor.getEmail());
            statement.setString(3, vendor.getMobileNumber());
            statement.setInt(4, vendor.getId());

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
    public boolean deleteById(Integer id) {
        Connection connection = DBConnection.getConnection();
        String query = "DELETE FROM vendor WHERE vendor_id = ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            if (statement.executeUpdate() == 1)
                return true;
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while deleting the vendor.");
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return true;
    }

    private Vendor instantiateVendor(ResultSet resultSet) throws SQLException {
        Vendor vendor = new Vendor();
        vendor.setId(resultSet.getInt("vendor_id"));
        vendor.setName(resultSet.getString("vendor_name"));
        vendor.setEmail(resultSet.getString("email"));
        vendor.setMobileNumber(resultSet.getString("mobile_number"));

        return vendor;
    }
}
