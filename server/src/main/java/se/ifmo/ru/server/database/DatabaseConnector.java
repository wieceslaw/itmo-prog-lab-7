package se.ifmo.ru.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DatabaseConnector {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            connect();
        }
        return  connection;
    }

    private static void connect() {
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String driver = resource.getString("jdbc.driver");
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String password = resource.getString("db.password");
        String database = resource.getString("db.name");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер не найден");
        }
        try {
            connection = DriverManager.getConnection(url + database, user, password);
        } catch (SQLException e) {
            System.err.println("Не удается соединиться с базой данных.");
            e.printStackTrace();
        }
    }
}
