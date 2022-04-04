package utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Utilities {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/vehicledb?createDatabaseIfNotExist=true";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "admin";

    public static boolean tableExist(Connection conn, String tableName) throws SQLException {
        boolean tExists = false;
        try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equalsIgnoreCase(tableName)) {
                    tExists = true;
                    break;
                }
            }
        }
        return tExists;
    }
}
