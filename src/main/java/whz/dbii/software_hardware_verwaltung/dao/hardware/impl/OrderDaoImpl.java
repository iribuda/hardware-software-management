package whz.dbii.software_hardware_verwaltung.dao.hardware.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.dao.DBConnection;
import whz.dbii.software_hardware_verwaltung.dao.DBException;
import whz.dbii.software_hardware_verwaltung.dao.hardware.HardwareDao;
import whz.dbii.software_hardware_verwaltung.dao.hardware.OrderDao;
import whz.dbii.software_hardware_verwaltung.dao.software.SoftwareDao;
import whz.dbii.software_hardware_verwaltung.dao.software.impl.SoftwareDaoImpl;
import whz.dbii.software_hardware_verwaltung.model.hardware.Hardware;
import whz.dbii.software_hardware_verwaltung.model.hardware.Manufacturer;
import whz.dbii.software_hardware_verwaltung.model.hardware.Order;
import whz.dbii.software_hardware_verwaltung.model.software.License;

import java.sql.*;

public class OrderDaoImpl implements OrderDao {

    private final HardwareDao hardwareDao;

    public OrderDaoImpl() {
        this.hardwareDao = new HardwareDaoImpl();
    }

    @Override
    public Order findById(Integer id) {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM h_order " +
                "WHERE order_id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return instantiateOrder(resultSet);
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
    public ObservableList<Order> findAll() {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM vw_order_details";
        Statement statement = null;
        ResultSet resultSet = null;
        ObservableList<Order> orders = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            orders = FXCollections.observableArrayList();
            while (resultSet.next())
                orders.add(instantiateOrderDetails(resultSet));
        }catch (SQLException e) {
            throw new DBException("Error occurred while getting the orders list: " + e.getMessage());
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }
        return orders;
    }

    @Override
    public boolean insert(Order order) {
        Connection connection = DBConnection.getConnection();
        String query = "INSERT INTO h_order " +
                "(order_date, order_status, hardware_id) " +
                "VALUES (?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(order.getOrder_date()));
            statement.setString(2, order.getOrder_status());
            statement.setInt(3, order.getHardware().getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    order.setOrder_id(resultSet.getInt(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error occurred by connecting while adding the order: " + e.getMessage());
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return false;
    }

    @Override
    public boolean update(Order order) {
        Connection connection = DBConnection.getConnection();
        String query = "UPDATE order " +
                "SET order_date = ?, order_status = ?, hardware_id = ? " +
                "WHERE order_id = ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setDate(1, Date.valueOf(order.getOrder_date()));
            statement.setString(2, order.getOrder_status());
            statement.setInt(3, order.getHardware().getId());
            statement.setInt(4, order.getOrder_id());

            if (statement.executeUpdate() == 1)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Error occurred by connecting while updating the order.");
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        Connection connection = DBConnection.getConnection();
        String query = "DELETE FROM h_order WHERE order_id = ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            if (statement.executeUpdate() == 1)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Error occurred by connecting while deleting the order.");
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }

        return false;
    }

    private Order instantiateOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setOrder_id(resultSet.getInt("order_id"));
        order.setOrder_date(resultSet.getDate("order_date").toLocalDate());
        order.setOrder_status(resultSet.getString("order_status"));
        order.setHardware(hardwareDao.findById(resultSet.getInt("hardware_id")));

        return order;
    }

    private Order instantiateOrderDetails(ResultSet resultSet) throws SQLException{
        Order order = new Order();
        order.setOrder_id(resultSet.getInt("order_id"));
        order.setOrder_date(resultSet.getDate("order_date").toLocalDate());
        order.setOrder_status(resultSet.getString("order_status"));
        Hardware hardware = new Hardware();
        hardware.setId(resultSet.getInt("hardware_id"));
        hardware.setName(resultSet.getString("hardware_name"));
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getInt("manufacturer_id"));
        manufacturer.setName(resultSet.getString("manufacturer_name"));
        hardware.setManufacturer(manufacturer);
        order.setHardware(hardware);
        return order;
    }
}
