package whz.dbii.software_hardware_verwaltung.dao.hardware.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.dao.DBConnection;
import whz.dbii.software_hardware_verwaltung.dao.DBException;
import whz.dbii.software_hardware_verwaltung.dao.hardware.FailureDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.HardwareDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.OrderDao;
import whz.dbii.software_hardware_verwaltung.model.hardware.Failure;
import whz.dbii.software_hardware_verwaltung.model.hardware.Hardware;
import whz.dbii.software_hardware_verwaltung.model.hardware.Manufacturer;
import whz.dbii.software_hardware_verwaltung.model.hardware.Order;

import java.sql.*;

public class FailureDaoImpl implements FailureDao {

    private final HardwareDao hardwareDao;

    public FailureDaoImpl() {
        this.hardwareDao = new HardwareDaoImpl();
    }

    @Override
    public Failure findById(Integer id) {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM vw_failure_details " +
                "WHERE failure_id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return instantiateFailureDetails(resultSet);
            }
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the failure" + e.getMessage());
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return null;
    }

    @Override
    public ObservableList<Failure> findAll() {
        Connection connection = DBConnection.getConnection();
        String query = "EXECUTE  sp_refreshview vw_failure_details; SELECT * FROM vw_failure_details";
        Statement statement = null;
        ResultSet resultSet = null;
        ObservableList<Failure> failures = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            failures = FXCollections.observableArrayList();
            while (resultSet.next())
                failures.add(instantiateFailureDetails(resultSet));
        }catch (SQLException e) {
            throw new DBException("Error occurred while getting the failure list: " + e.getMessage());
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }
        return failures;
    }

    @Override
    public boolean insert(Failure failure) {
        Connection connection = DBConnection.getConnection();
        String query = "INSERT INTO failure " +
                "(failure_type, failure_status, failure_date, hardware_id) " +
                "VALUES (?, ?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, failure.getType());
            statement.setString(2, failure.getStatus().toString());
            statement.setDate(3, Date.valueOf(failure.getDate()));
            statement.setInt(4, failure.getHardwareId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    failure.setId(resultSet.getInt(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while adding the failure: " + e.getMessage());
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return false;
    }

    @Override
    public boolean update(Failure failure) {
        Connection connection = DBConnection.getConnection();
        String query = "UPDATE failure " +
                "SET failure_date = ?, failure_status = ?, failure_type = ? " +
                "WHERE failure_id = ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setDate(1, Date.valueOf(failure.getDate()));
            statement.setString(2, failure.getStatus().toString());
            statement.setString(3, failure.getType());
            statement.setInt(4, failure.getId());

            if (statement.executeUpdate() == 1)
                return true;
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while updating the failure." + e.getMessage());
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        Connection connection = DBConnection.getConnection();
        String query = "DELETE FROM failure WHERE failure_id = ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            if (statement.executeUpdate() == 1)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Error occurred by connecting while deleting the failure.");
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return false;
    }

    private Failure instantiateFailureDetails(ResultSet resultSet) throws SQLException{
        Failure failure = new Failure();
        failure.setId(resultSet.getInt("failure_id"));
        failure.setType(resultSet.getString("failure_type"));
        failure.setDate(resultSet.getDate("failure_date").toLocalDate());
        failure.setStatus(resultSet.getString("failure_status"));
        failure.setHardwareId(resultSet.getInt("hardware_id"));
        failure.setHardwareName(resultSet.getString("hardware_name"));
        failure.setWorkerName(resultSet.getString("worker_name"), resultSet.getString("worker_surname"));
        return failure;
    }
}
