package ua.production.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.production.data.DBCPDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlParser {

    private static Logger logger = LoggerFactory.getLogger(SqlParser.class);

    public static void executeScriptUsingStatement(String scriptFilePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try (Connection connection = DBCPDataSource.getConnection();
             Statement statement = connection.createStatement();
             BufferedReader reader = new BufferedReader(new FileReader(scriptFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                if (line.endsWith(";")) {
                    statement.execute(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                }
            }

        } catch (IOException | SQLException e) {
            logger.error("Sql parsing exception ", e);
        }
    }
}
