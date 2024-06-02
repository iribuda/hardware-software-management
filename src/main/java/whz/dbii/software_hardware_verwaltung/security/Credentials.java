package whz.dbii.software_hardware_verwaltung.security;

import whz.dbii.software_hardware_verwaltung.dao.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Credentials {
    private final String username;
    private final String password;
    private final Set<String> userRoles = new HashSet<>();

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void loadUserRoles() {
        userRoles.clear();
        String query = "SELECT dp.name AS DatabaseRoleName "
                + "FROM sys.database_role_members drm "
                + "JOIN sys.database_principals dp ON dp.principal_id = drm.role_principal_id "
                + "JOIN sys.database_principals dp2 ON dp2.principal_id = drm.member_principal_id "
                + "WHERE dp2.name = USER_NAME()";

        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                userRoles.add(rs.getString("DatabaseRoleName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean canDelete() {
        return userRoles.contains(Roles.Admin.toString());
    }

    public boolean canWrite() {
        return userRoles.contains(Roles.ReadWriter.toString());
    }
}
