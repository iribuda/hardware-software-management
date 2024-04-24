package whz.dbii.software_hardware_verwaltung.dao.worker.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.dao.DBConnection;
import whz.dbii.software_hardware_verwaltung.dao.DBException;
import whz.dbii.software_hardware_verwaltung.dao.DateUtil;
import whz.dbii.software_hardware_verwaltung.dao.worker.WorkerDAO;
import whz.dbii.software_hardware_verwaltung.model.Worker;
import whz.dbii.software_hardware_verwaltung.model.software.Software;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkerDAOImpl implements WorkerDAO {

    @Override
    public Worker findById(Integer id) {
        return null;
    }

    @Override
    public ObservableList<Worker> findAll() {
        Connection conn = DBConnection.getConnection();
        Statement statement = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM worker";
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new DBException("Error by connecting while getting all the workers");
        }
        ObservableList<Worker> workers = FXCollections.observableArrayList();
        try{
            while (rs.next()){
                workers.add(extractWorker(rs));
            }
        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }finally {
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }
        return workers;
    }

    private Worker extractWorker(ResultSet rs) throws SQLException {
        Worker worker = new Worker();
        worker.setId(rs.getInt("worker_id"));
        worker.setName(rs.getString("worker_name"));
        worker.setSurname(rs.getString("worker_surname"));
        worker.setEmail(rs.getString("email"));
        return worker;
    }

    @Override
    public boolean save(Worker worker) {
        Connection conn = DBConnection.getConnection();
        String sql = "INSERT INTO worker(worker_name, worker_surname, email) VALUES(?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, worker.getName());
            preparedStatement.setString(2, worker.getSurname());
            preparedStatement.setString(3, worker.getEmail());
            int rs = preparedStatement.executeUpdate();
            if (rs==1)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e.getMessage());
        }finally {
            DBConnection.closeStatement(preparedStatement);
            DBConnection.disconnect();
        }
        return false;
    }

    @Override
    public boolean update(Worker worker) {
        Connection conn = DBConnection.getConnection();
        String sql = "UPDATE worker SET worker_name=?, worker_surname=?, email=? WHERE worker_id=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, worker.getName());
            preparedStatement.setString(2, worker.getSurname());
            preparedStatement.setString(3, worker.getEmail());
            preparedStatement.setInt(4, worker.getId());
            int rs = preparedStatement.executeUpdate();
            if (rs==1)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e.getMessage());
        }finally {
            DBConnection.closeStatement(preparedStatement);
            DBConnection.disconnect();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        Connection conn = DBConnection.getConnection();
        String sql = "DELETE FROM worker WHERE worker_id=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            int rs = preparedStatement.executeUpdate();
            if (rs==1)
                return true;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }finally {
            DBConnection.closeStatement(preparedStatement);
            DBConnection.disconnect();
        }
        return false;
    }

    @Override
    public ObservableList<Software> findSoftwareOfWorker(int id){
        Connection conn = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String sql = "SELECT software_id, software_name, software_version FROM software WHERE software_id IN (" +
                "    SELECT software_id FROM worker_software WHERE worker_id=?" +
                "    )";
        ObservableList<Software> software = FXCollections.observableArrayList();

        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                software.add(extractSoftware(rs));
            }
        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }finally {
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatement(preparedStatement);
            DBConnection.disconnect();
        }
        return software;
    }

    @Override
    public ObservableList<String> findNamesOfSoftwareOfWorker(int id) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String sql = "SELECT software_name FROM software WHERE software_id IN (" +
                "    SELECT software_id FROM worker_software WHERE worker_id=?" +
                "    )";
        ObservableList<String> software = FXCollections.observableArrayList();

        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                software.add(rs.getString("software_name"));
            }
        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }finally {
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatement(preparedStatement);
            DBConnection.disconnect();
        }
        return software;
    }

    @Override
    public ObservableList<String> findNamesOfHardwareOfWorker(int id) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String sql = "SELECT hardware_name FROM hardware WHERE hardware.hardware_id IN (" +
                "    SELECT hardware_id FROM worker_hardware WHERE worker_id=?" +
                "    )";
        ObservableList<String> hardware = FXCollections.observableArrayList();
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                hardware.add(rs.getString("hardware_name"));
            }
        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }finally {
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatement(preparedStatement);
            DBConnection.disconnect();
        }
        return hardware;
    }

    @Override
    public boolean addSoftwareToWorker(int softwareId, int workerId) {
        Connection conn = DBConnection.getConnection();
        String sql = "INSERT INTO worker_software(worker_id, software_id, usage_start_date) VALUES(?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, workerId);
            preparedStatement.setInt(2, softwareId);
            preparedStatement.setDate(3, new Date(System.currentTimeMillis()));
            int rs = preparedStatement.executeUpdate();
            if (rs==1)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e.getMessage());
        }finally {
            DBConnection.closeStatement(preparedStatement);
            DBConnection.disconnect();
        }
        return false;
    }

    private Software extractSoftware(ResultSet resultSet) throws SQLException {
        Software software = new Software();
        software.setId(resultSet.getInt("software_id"));
        software.setName(resultSet.getString("software_name"));
        software.setVersion(resultSet.getString("software_version"));
        return software;
    }
}
