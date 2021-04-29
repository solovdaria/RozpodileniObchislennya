package ua.production.data;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static ua.production.constants.ConnectionConstants.*;

public class DBCPDataSource {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl(JDBC_URL);
        ds.setUsername(USER);
        ds.setPassword(PASSWORD);
        ds.setMinIdle(MIN_IDLE);
        ds.setMaxIdle(MAX_IDLE);
        ds.setMaxWaitMillis(5000);
        ds.setDriverClassName(DRIVER_CLASS_NAME);
        ds.setMaxOpenPreparedStatements(MAX_OPEN_PREPARED_STATEMENTS);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private DBCPDataSource() {
    }
}
