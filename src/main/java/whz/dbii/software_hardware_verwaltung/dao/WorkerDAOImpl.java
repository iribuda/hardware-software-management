package whz.dbii.software_hardware_verwaltung.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.model.Worker;

import java.sql.*;

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
}
