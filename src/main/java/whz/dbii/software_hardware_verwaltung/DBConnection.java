package whz.dbii.software_hardware_verwaltung;

import java.sql.*;

public class DBConnection {
    private static final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static Connection conn = null;
    private static final String connStr = "jdbc:sqlserver://localhost;database=DB_HSverwaltung;" +
            "encrypt=true;trustServerCertificate=true;authenticationScheme=NTLM;domain=myDomain;";
    public static final String USER = "sa";
    public static final String PASS = "1234";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e){
            System.out.println("No JDBC Driver found!");
            e.printStackTrace();
            throw e;
        }

        System.out.println("SQL Server JDBC registered");
        try{
            conn = DriverManager.getConnection(connStr, USER, PASS);
        }catch (SQLException e){
            System.out.println("Connection failed!");
            e.printStackTrace();
            throw e;
        }
        return conn;
    }

    public static void disconnect() throws SQLException{
        try{
            if (conn != null && !conn.isClosed()){
                conn.close();
            }
        }catch (Exception e){
            throw e;
        }
    }

    public static void closeStatement(Statement st) throws SQLException {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw e;
            }
        }
    }

    public static void closeResultSet(ResultSet rs) throws SQLException {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw e;
            }
        }
    }
}
