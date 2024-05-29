package whz.dbii.software_hardware_verwaltung.dao.hardware.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.dao.DBConnection;
import whz.dbii.software_hardware_verwaltung.dao.DBException;
import whz.dbii.software_hardware_verwaltung.dao.hardware.HardwareDao;
import whz.dbii.software_hardware_verwaltung.model.hardware.Hardware;
import whz.dbii.software_hardware_verwaltung.model.hardware.Manufacturer;
import whz.dbii.software_hardware_verwaltung.model.hardware.Warranty;

import java.sql.*;

public class HardwareDaoImpl implements HardwareDao {

    @Override
    public Hardware findById(Integer id) {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM vw_hardware_details WHERE hardware_id = ?";

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return instantiateHardwareDetails(resultSet);
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
        String query = "SELECT * FROM vw_hardware_details";
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
                hardware.add(instantiateHardwareDetails(resultSet));

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
        String query = "SELECT hardware_name FROM hardware";
        return getStringObservableList(query);
    }

    @Override
    public ObservableList<String> findAllNamesOfAvailableHardware() {
        String query = "SELECT h.hardware_name FROM hardware h\n" +
                "LEFT JOIN worker_hardware wh ON h.hardware_id = wh.hardware_id\n" +
                "LEFT JOIN failure f ON h.hardware_id = f.hardware_id\n" +
                "WHERE wh.worker_id IS NULL AND (f.hardware_id IS NULL OR f.failure_status IN ('repariert'))";
        return getStringObservableList(query);
    }

    private ObservableList<String> getStringObservableList(String query) {
        Connection connection = DBConnection.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        ObservableList<String> hardware = FXCollections.observableArrayList();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next())
                hardware.add(resultSet.getString("hardware_name"));

        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the hardware list: " + e.getMessage());
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return hardware;
    }

    private Hardware instantiateHardwareDetails(ResultSet resultSet) throws SQLException {
        Hardware hardware = new Hardware();
        Manufacturer manufacturer = new Manufacturer();
        Warranty warranty = new Warranty();
        hardware.setId(resultSet.getInt("hardware_id"));
        hardware.setName(resultSet.getString("hardware_name"));
        manufacturer.setId(resultSet.getInt("manufacturer_id"));
        manufacturer.setName(resultSet.getString("manufacturer_name"));
        manufacturer.setEmail(resultSet.getString("email"));
        manufacturer.setMobileNumber("mobile_number");
        warranty.setId(resultSet.getInt("warranty_id"));
        warranty.setStatus(resultSet.getString("warranty_status"));
        warranty.setExpirationDate(resultSet.getDate("expiration_date").toLocalDate());
        warranty.setStartDate(resultSet.getDate("w_start_date").toLocalDate());
        hardware.setManufacturer(manufacturer);
        hardware.setWarranty(warranty);
        return hardware;
    }


    @Override
    public boolean insert(Hardware hardware) {
        Connection connection = DBConnection.getConnection();
        String query = "{call sp_insert_hardware(?, ?, ?, ?)}";
        CallableStatement statement = null;
//        ResultSet resultSet = null;

        try {
            statement = connection.prepareCall(query);
            statement.setString(1, hardware.getName());
            statement.setDate(2, Date.valueOf(hardware.getWarranty().getStartDate()));
            statement.setDate(3, Date.valueOf(hardware.getWarranty().getExpirationDate()));
            statement.setString(4, hardware.getManufacturer().getName());
            statement.execute();


//            int rowsAffected = statement.executeUpdate();
//            if (rowsAffected > 0) {
//                resultSet = statement.getGeneratedKeys();
//                if (resultSet.next()) {
//                    hardware.setId(resultSet.getInt(1));
//                    return true;
//                }
//            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Error occurred by connecting while inserting hardware: " + e.getMessage());

        } finally {
//            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }
        return true;
    }

    @Override
    public boolean deleteById(Integer id) {
        Connection connection = DBConnection.getConnection();
        String query = "DELETE FROM hardware WHERE hardware_id = ?";
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
        String query = "UPDATE hardware SET hardware_name=?, manufacturer_id=? WHERE hardware_id=?;" +
                "UPDATE warranty SET warranty_status=?, w_start_date=?, expiration_date=? WHERE warranty_id=?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, hardware.getName());
            statement.setInt(2, hardware.getManufacturer().getId());
            statement.setInt(3, hardware.getId());
            statement.setString(4, hardware.getWarranty().getStatus());
            statement.setDate(5, Date.valueOf(hardware.getWarranty().getStartDate()));
            statement.setDate(6, Date.valueOf(hardware.getWarranty().getExpirationDate()));
            statement.setInt(7, hardware.getWarranty().getId());

            if (statement.executeUpdate() == 1) {
                return true;
            }
        }
        catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the hardware." + e.getMessage());
        }
        finally {
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }
        return false;
    }
}
