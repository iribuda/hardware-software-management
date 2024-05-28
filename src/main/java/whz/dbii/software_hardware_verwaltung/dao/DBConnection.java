package whz.dbii.software_hardware_verwaltung.dao;

import whz.dbii.software_hardware_verwaltung.security.Credentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBConnection {
    private static Connection conn = null;
    private static final String connStr = "jdbc:sqlserver://localhost;database=DB_HSverwaltung;" +
            "encrypt=true;trustServerCertificate=true;authenticationScheme=NTLM;domain=myDomain;";

    private static Credentials credentials;

//    public static Connection getConnection(){
//        if (conn == null || connectionIsClosed()){
//            try {
//                Properties properties = loadProperties();
//                //later: setProperties with the input from login
//                String user = properties.getProperty("user");
//                String password = properties.getProperty("password");
//                conn = DriverManager.getConnection(connStr, user, password);
//            }catch (SQLException e){
//                throw new DBException(e.getMessage());
//            }
//        }
//        return conn;
//    }

    public static void setCredentials(Credentials c) {
        credentials = c;
    }

    public static boolean hasWriteRights() {
        if (credentials != null) {
            return credentials.canWrite();
        }

        return false;
    }

    public static boolean hasDeleteRights() {
        if (credentials != null) {
            return credentials.canDelete();
        }

        return false;
    }

    public static Connection getConnection(){
        if (credentials == null)
            throw new IllegalStateException("Connection has not been authenticated.");;

        if (conn == null || connectionIsClosed()){
            try {
                String user = credentials.getUsername();
                String password = credentials.getPassword();
                conn = DriverManager.getConnection(connStr, user, password);
            }catch (SQLException e){
                throw new DBException(e.getMessage());
            }
        }

        return conn;
    }

    public static void authenticate(String username, String password) throws SQLException {
        conn = DriverManager.getConnection(connStr, username, password);
    }

    public static void logout() {
        credentials = null;
    }

    private static Properties loadProperties(){
        try(FileInputStream fs = new FileInputStream("src/main/resources/whz/dbii/software_hardware_verwaltung/db.properties")){
            Properties properties = new Properties();
            properties.load(fs);
            return properties;
        }catch (IOException e){
            throw new DBException(e.getMessage());
        }
    }

    public static void disconnect(){
        try{
            if (conn != null && !conn.isClosed()){
                conn.close();
            }
        }catch (Exception e){
            throw new DBException(e.getMessage());
        }
    }

    private static boolean connectionIsClosed() {
        boolean connectionIsClosed=true;
        try {
            if (conn != null)
                connectionIsClosed = conn.isClosed();
        } catch (SQLException e) {
            throw new DBException("Error by checking whether the connection is closed");
        }
        return connectionIsClosed;
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
    }
}
