package whz.dbii.software_hardware_verwaltung.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import whz.dbii.software_hardware_verwaltung.model.Worker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            e.printStackTrace();
            throw new DBException("Error by getting all the workers");
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
    public void save(Worker worker) {

    }

    @Override
    public void update(Worker worker) {

    }

    @Override
    public void delete(Worker worker) {

    }
}
