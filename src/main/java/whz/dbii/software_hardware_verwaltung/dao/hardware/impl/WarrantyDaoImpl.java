package whz.dbii.software_hardware_verwaltung.dao.hardware.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.dao.DBConnection;
import whz.dbii.software_hardware_verwaltung.dao.DBException;
import whz.dbii.software_hardware_verwaltung.dao.hardware.WarrantyDao;
import whz.dbii.software_hardware_verwaltung.model.hardware.Warranty;
import whz.dbii.software_hardware_verwaltung.model.software.Vendor;

import java.sql.*;

public class WarrantyDaoImpl implements WarrantyDao {

    @Override
    public Warranty findById(Integer id){
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM Warranty WHERE id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if(resultSet.next()) {
                return instantiateWarranty(resultSet);

            }
        } catch (SQLException e){
            throw new DBException("Error occurred by connecting while getting the vendor.");
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
//            DBConnection.disconnect();
        }

        return null;
    }

    @Override
    public ObservableList<Vendor> findAll() {
        return null;
    }

    private Warranty instantiateWarranty(ResultSet resultSet) throws SQLException {
        Warranty warranty = new Warranty();
//        warranty.setId(resultSet.getInt("warranty_id"));
//        warranty.setName(resultSet.getString("warranty_name"));
//        warranty.setEmail(resultSet.getString("warranty_email"));
//
        return warranty;
    }

    public Warranty findbyId(Integer id) {
        return null;
    }

//    @Override
//    public ObservableList<Warranty> findAll() {
//        Connection connection = DBConnection.getConnection();
//        String query = "SELECT * FROM Warranty";
//        Statement statement = null;
//        ResultSet resultSet = null;
//
//        try {
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(query);
//        } catch (SQLException e) {
//            throw new DBException("Error occurred by connecting while getting the vendor list.");
//        }
//
//        ObservableList<Warranty> warranties = FXCollections.observableArrayList();
//        try {
//            while (resultSet.next())
//                warranties.add(instantiateWarranty(resultSet));
//        } catch (SQLException e){
//            throw new DBException("Error occurred by connecting while getting the vendor list.");
//        } finally {
//            DBConnection.closeResultSet(resultSet);
//            DBConnection.closeStatement(statement);
//            DBConnection.disconnect();
//        }
//
//        return warranties;
//
//    }

    @Override
    public boolean insert(Warranty warranty) {
        Connection connection = DBConnection.getConnection();
        String query = "INSERT INTO Warranty VALUES (warranty_name, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//            statement.setString(1, warranty.getName());
//            statement.setString(2, warranty.getEmail());
//            statement.setString(3, warranty.getMobileNumber());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
//                    warranty.serId(resultSet.getInt(1));
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
    public boolean update(Warranty warranty) {
        Connection connection = DBConnection.getConnection();
        String query = "UPDATE vendor " +
                "SET warranty_name = ?, email = ?, mobile_number = ? " +
                "WHERE warranty_id = ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
//            statement.setString(1, warranty.getName());
//            statement.setString(2, warranty.getEmail());
//            statement.setString(3, warranty.getMobileNumber());
            statement.setInt(4, warranty.getId());

            if (statement.executeUpdate() == 1)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Error occurred by connecting while updating the vendor.");
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }
        return false;
    }

    @Override
    public boolean deletyById(Integer id) {
        Connection connection = DBConnection.getConnection();
        String query = "DELETE FROM vendor WHERE warranty_id = ?";
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

    @Override
    public ObservableList<String> findAllWarrantyNames() {
        return null;
    }


    @Override
    public int findIdByName(String name) {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT warranty_id FROM vendor WHERE warranty_name=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int id = 0;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("warranty_id");
            }
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while getting the vendor id by name.");
        }

        return id;
    }


}
