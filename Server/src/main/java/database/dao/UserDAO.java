package database.dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAO<User, String> {
    private enum SQL {
        CREATE("INSERT INTO users (login, password) VALUES (?, ?) RETURNING login"),
        READALL("SELECT * FROM users"),
        READ("SELECT * FROM users WHERE login = (?) AND password = (?)"),
        READBYID("SELECT * FROM users WHERE login = (?)"),
        UPDATE("UPDATE users SET password = (?) WHERE login = (?) RETURNING login"),
        DELETE("DELETE FROM users WHERE login = (?) RETURNING login"),
        DELETEALL("DELETE FROM users");
        final String QUERY;
        SQL(String QUERY) {this.QUERY = QUERY;}
    }

    public UserDAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(User user) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL.CREATE.QUERY)) {
            statement.setString(1, user.getLogin());
            statement.setBytes(2, user.getPassword().getBytes());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<User> readAll() {
        ArrayList<User> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL.READALL.QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(new User(resultSet.getString("login"), new String(resultSet.getBytes("password"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public User read(User user) {
        User result = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL.READ.QUERY)) {
            statement.setString(1, user.getLogin());
            statement.setBytes(2, user.getPassword().getBytes());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = new User(resultSet.getString("login"), new String(resultSet.getBytes("password")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public User read(String login) {
        User result = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL.READBYID.QUERY)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = new User(resultSet.getString("login"), new String(resultSet.getBytes("password")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(User user) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL.UPDATE.QUERY)) {
            statement.setBytes(1, user.getPassword().getBytes());
            statement.setString(2, user.getLogin());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(String login) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL.DELETE.QUERY)) {
            statement.setString(1, login);
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteAll() {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL.DELETEALL.QUERY)) {
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}