package Src;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import oracle.jdbc.pool.OracleDataSource;
import oracle.jdbc.OracleConnection;

public class DbConnection {
    final static String DB_URL = "jdbc:oracle:thin:@vyomnathandb_tp?TNS_ADMIN=/Users/nathanmathew/Desktop/F23/MovieStarsDBProj/Wallet_vyomnathanDB";
    final static String DB_USER = "ADMIN";
    final static String DB_PASSWORD = "JaysonTatum18";

    public static Connection getConnection() throws SQLException {
        Properties info = new Properties();
        info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
        info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);
        info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");
        OracleDataSource ods = new OracleDataSource();
        ods.setURL(DB_URL);
        ods.setConnectionProperties(info);
        Connection connection;
        try
        {
            connection = (OracleConnection) ods.getConnection();
            return connection;

        } catch (Exception e) {
            System.out.println("CONNECTION ERROR:");
            System.out.println(e);
        }

        return null;
    }
}