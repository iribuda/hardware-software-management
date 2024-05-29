package whz.dbii.software_hardware_verwaltung.dao.software.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.dao.DBConnection;
import whz.dbii.software_hardware_verwaltung.dao.DBException;
import whz.dbii.software_hardware_verwaltung.dao.software.LicenseDao;
import whz.dbii.software_hardware_verwaltung.dao.software.SoftwareDao;
import whz.dbii.software_hardware_verwaltung.model.software.License;
import whz.dbii.software_hardware_verwaltung.model.software.LicenseStatus;

import java.sql.*;
import java.time.LocalDate;

public class LicenseDaoImpl implements LicenseDao {

    private final SoftwareDao softwareDao;

    public LicenseDaoImpl() {
        this.softwareDao = new SoftwareDaoImpl();
    }

    @Override
    public License findById(Integer id) {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM license " +
                "WHERE license_id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return instantiateLicense(resultSet);
            }
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the license.");
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return null;
    }

    @Override
    public ObservableList<License> findAll() {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM license";
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the license list.");
        }

        ObservableList<License> licenses = FXCollections.observableArrayList();
        try {
            while (resultSet.next())
                licenses.add(instantiateLicense(resultSet));
        } catch (SQLException e) {
            throw new DBException("Error occurred while getting the license list.");
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return licenses;
    }

    @Override
    public boolean insert(License license) {
        Connection connection = DBConnection.getConnection();
        String query = "INSERT INTO license " +
                "(license_key, license_start_date, expiration_date, purchase_date, license_status, price, software_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, license.getKey());
            statement.setDate(2, java.sql.Date.valueOf(license.getStartDate()));
            statement.setDate(3, java.sql.Date.valueOf(license.getExpirationDate()));
            statement.setDate(4, java.sql.Date.valueOf(license.getPurchaseDate()));
            statement.setString(5, license.getStatus());
            statement.setFloat(6, license.getPrice());
            statement.setInt(7, license.getSoftware().getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    license.setId(resultSet.getInt(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while adding the license.");
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return false;
    }

    @Override
    public boolean update(License license) {
        Connection connection = DBConnection.getConnection();
        String query = "UPDATE license " +
                "SET license_key = ?, license_start_date = ?, expiration_date = ?, purchase_date = ?, license_status = ?, price = ?, software_id = ? " +
                "WHERE license_id = ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, license.getKey());
            statement.setDate(2, java.sql.Date.valueOf(license.getStartDate()));
            statement.setDate(3, java.sql.Date.valueOf(license.getExpirationDate()));
            statement.setDate(4, java.sql.Date.valueOf(license.getPurchaseDate()));
            statement.setString(5, license.getStatus());
            statement.setFloat(6, license.getPrice());
            statement.setInt(7, license.getSoftware().getId());
            statement.setInt(8, license.getId());

            if (statement.executeUpdate() == 1)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Error occurred by connecting while updating the license.");
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        Connection connection = DBConnection.getConnection();
        String query = "DELETE FROM license WHERE license_id = ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            if (statement.executeUpdate() == 1)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Error occurred by connecting while deleting the license.");
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return false;
    }

    @Override
    public boolean renewLicense(License license) {
        Connection connection = DBConnection.getConnection();
        String query = "UPDATE license " +
                "SET license_start_date = ?, expiration_date = ?, license_status = ? " +
                "WHERE license_id = ?";
        PreparedStatement statement = null;

        LocalDate currDate = java.time.LocalDate.now();
        license.setStartDate(currDate);
        license.setExpirationDate(currDate.plusMonths(1));
        license.setStatus(LicenseStatus.ACTIVE.getStatus());

        try {
            statement = connection.prepareStatement(query);
            statement.setDate(1, java.sql.Date.valueOf(license.getStartDate()));
            statement.setDate(2, java.sql.Date.valueOf(license.getExpirationDate()));
            statement.setString(3, license.getStatus());
            statement.setInt(4, license.getId());

            if (statement.executeUpdate() == 1)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Error occurred by connecting while renewing the license.");
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return false;
    }

    private License instantiateLicense(ResultSet resultSet) throws SQLException {
        License license = new License();
        license.setId(resultSet.getInt("license_id"));
        license.setKey(resultSet.getInt("license_key"));
        license.setStartDate(resultSet.getDate("license_start_date").toLocalDate());
        license.setExpirationDate(resultSet.getDate("expiration_date").toLocalDate());
        license.setPurchaseDate(resultSet.getDate("purchase_date").toLocalDate());
        license.setStatus(resultSet.getString("license_status"));
        license.setPrice(resultSet.getFloat("price"));
        license.setSoftware(softwareDao.findById(resultSet.getInt("software_id")));

        return license;
    }
}
