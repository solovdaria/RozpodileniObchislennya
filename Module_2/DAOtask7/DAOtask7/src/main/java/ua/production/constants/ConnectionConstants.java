package ua.production.constants;

public class ConnectionConstants {
    public static final String JDBC_URL = "jdbc:postgresql://127.0.0.1:5432/production";
    public static final String USER = "postgres";
    public static final String PASSWORD = "root";
    public static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    public static final int MIN_IDLE = 5;
    public static final int MAX_IDLE = 10;
    public static final int MAX_OPEN_PREPARED_STATEMENTS = 100;
}
