package whz.dbii.software_hardware_verwaltung.dao.software.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.dao.DBConnection;
import whz.dbii.software_hardware_verwaltung.dao.DBException;
import whz.dbii.software_hardware_verwaltung.dao.software.SoftwareDao;
import whz.dbii.software_hardware_verwaltung.dao.software.VendorDao;
import whz.dbii.software_hardware_verwaltung.model.software.Software;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SoftwareDaoImpl implements SoftwareDao {

    private final VendorDao vendorDao;

    public SoftwareDaoImpl() {
        this.vendorDao = new VendorDaoImpl();
    }
    @Override
    public Software findById(Integer id) {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM software " +
                "WHERE software_id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return instantiateSoftware(resultSet);
            }
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the software.");
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
//            DBConnection.disconnect();
        }

        return null;
}

    @Override
    public ObservableList<Software> findAll() {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM software";
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the software list.");
        }

        ObservableList<Software> software = FXCollections.observableArrayList();
        try {
            while (resultSet.next())
                software.add(instantiateSoftware(resultSet));
        } catch (SQLException e) {
            throw new DBException("Error occurred while getting the software list.");
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return software;
    }

    @Override
    public boolean insert(Software software) {
        Connection connection = DBConnection.getConnection();
        String query = "INSERT INTO software " +
                "(software_name, software_version, vendor_id) " +
                "VALUES (?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, software.getName());
            statement.setString(2, software.getVersion());
            statement.setInt(3, software.getVendor().getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    software.setId(resultSet.getInt(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while adding the software.");
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return false;
    }

    @Override
    public boolean update(Software software) {
        Connection connection = DBConnection.getConnection();
        String query = "UPDATE software " +
                "SET software_name = ?, software_version = ?, vendor_id = ? " +
                "WHERE software_id = ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, software.getName());
            statement.setString(2, software.getVersion());
            statement.setInt(3, software.getVendor().getId());
            statement.setInt(4, software.getId());

            if (statement.executeUpdate() == 1)
                return true;
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while updating the software.");
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        Connection connection = DBConnection.getConnection();
        String query = "DELETE FROM software WHERE software_id = ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            if (statement.executeUpdate() == 1)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Error occurred by connecting while deleting the software:" + e.getMessage());
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return false;
    }

    @Override
    public ObservableList<String> findAllNameOfSoftware() {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT software_name FROM software";
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the software list.");
        }

        ObservableList<String> software = FXCollections.observableArrayList();
        try {
            while (resultSet.next())
                software.add(resultSet.getString("software_name"));
        } catch (SQLException e) {
            throw new DBException("Error occurred while getting the software list.");
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return software;
    }

    @Override
    public int findIdByName(String name) {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT software_id FROM software WHERE software_name=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int id = 0;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("software_id");
            }
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the software id by name.");
        }

        return id;
    }

    private Software instantiateSoftware(ResultSet resultSet) throws SQLException {
        Software software = new Software();
        software.setId(resultSet.getInt("software_id"));
        software.setName(resultSet.getString("software_name"));
        software.setVersion(resultSet.getString("software_version"));
        software.setVendor(vendorDao.findById(resultSet.getInt("vendor_id")));

        return software;
    }
}