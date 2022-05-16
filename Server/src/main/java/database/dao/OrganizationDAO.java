package database.dao;

import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizationDAO extends DAO<Organization, Long> {
    private enum SQL {
        CREATE("INSERT INTO organizations (id, name, creation_date, annual_turnover, employees_count, type, user_login) VALUES (NEXTVAL('organization_id_sequence'), ?, ?, ?, ?, ?, ?) RETURNING id"),
        CREATE_CORDS("INSERT INTO cords (x_cord, y_cord, org_id) VALUES (?, ?, ?) RETURNING id"),
        CREATE_ADDRESS("INSERT INTO address (street, zip_code, org_id) VALUES (?, ?, ?) RETURNING id"),
        READALL("SELECT id, name, (select x_cord from cords where org_id = organizations.id), (select y_cord from cords where org_id = organizations.id), creation_date, annual_turnover, employees_count, type, (select street from address where org_id = organizations.id), (select zip_code from address where org_id = organizations.id), user_login FROM organizations"),
        READ("SELECT id, name, (select x_cord from cords where org_id = organizations.id), (select y_cord from cords where org_id = organizations.id), creation_date, annual_turnover, employees_count, type, (select street from address where org_id = organizations.id), (select zip_code from address where org_id = organizations.id), user_login FROM organizations WHERE id = (?)"),
        UPDATE("UPDATE organizations SET name = ?, creation_date = ?, annual_turnover = ?, employees_count = ?, type = ?, user_login = ? WHERE id = ? RETURNING id"),
        UPDATE_CORDS("UPDATE cords SET x_cord = ?, y_cord = ? WHERE org_id = ? RETURNING id"),
        UPDATE_ADDRESS("UPDATE address SET street = ?, zip_code = ? WHERE org_id = ? RETURNING id"),
        DELETE("DELETE FROM organizations WHERE id = (?) RETURNING id"),
        DELETE_CORDS("DELETE FROM cords WHERE org_id = (?) RETURNING id"),
        DELETE_ADDRESS("DELETE FROM address WHERE org_id = (?) RETURNING id"),
        DELETEALL("DELETE FROM organizations RETURNING *"),
        DELETEALL_CORDS("DELETE FROM cords RETURNING *"),
        DELETEALL_ADDRESS("DELETE FROM address RETURNING *");
        final String QUERY;
        SQL(String QUERY) {this.QUERY = QUERY;}
    }
    public OrganizationDAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(Organization object) {
        boolean result = false;
        long org_id = 0L;
        try (PreparedStatement statement = connection.prepareStatement(SQL.CREATE.QUERY)) {
            statement.setString(1, object.getName());
            statement.setTimestamp(2, Timestamp.valueOf(object.getCreationDate()));
            if (object.getAnnualTurnover() == null) {
                statement.setNull(3, Types.INTEGER);
            } else {
                statement.setInt(3, object.getAnnualTurnover());
            }
            statement.setInt(4, object.getEmployeesCount());
            if (object.getType() == null) {
                statement.setNull(5, Types.VARCHAR);
            } else {
                statement.setString(5, object.getType().toString());
            }
            statement.setString(6, object.getAuthorLogin());
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next();
            org_id = resultSet.getLong("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(org_id);
        try (PreparedStatement statement = connection.prepareStatement(SQL.CREATE_ADDRESS.QUERY)) {
            if (object.getOfficialAddress().getStreet() == null) {
                statement.setNull(1, Types.VARCHAR);
            } else {
                statement.setString(1, object.getOfficialAddress().getStreet());
            }
            if (object.getOfficialAddress().getZipCode() == null) {
                statement.setNull(2, Types.VARCHAR);
            } else {
                statement.setString(2, object.getOfficialAddress().getZipCode());
            }
            statement.setLong(3, org_id);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement statement = connection.prepareStatement(SQL.CREATE_CORDS.QUERY)) {
            statement.setDouble(1, object.getCoordinates().getX());
            statement.setFloat(2, object.getCoordinates().getY());
            statement.setLong(3, org_id);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Organization> readAll() {
        ArrayList<Organization> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL.READALL.QUERY); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Integer annual_turnover = resultSet.getInt("annual_turnover");
                if (resultSet.wasNull()) annual_turnover = null;
                result.add(new Organization(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        new Coordinates(resultSet.getDouble("x_cord"), resultSet.getFloat("y_cord")),
                        resultSet.getTimestamp("creation_date").toLocalDateTime(),
                        annual_turnover,
                        resultSet.getInt("employees_count"),
                        resultSet.getString("type") == null ? null :OrganizationType.valueOf(resultSet.getString("type")),
                        new Address(resultSet.getString("street"), resultSet.getString("zip_code")),
                        resultSet.getString("user_login")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Organization read(Long key) {
        Organization result = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL.READ.QUERY)) {
            statement.setLong(1, key);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer annual_turnover = resultSet.getInt("annual_turnover");
                if (resultSet.wasNull()) annual_turnover = null;
                result = (new Organization(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        new Coordinates(resultSet.getDouble("x_cord"), resultSet.getFloat("y_cord")),
                        resultSet.getTimestamp("creation_date").toLocalDateTime(),
                        annual_turnover,
                        resultSet.getInt("employees_count"),
                        resultSet.getString("type") == null ? null :OrganizationType.valueOf(resultSet.getString("type")),
                        new Address(resultSet.getString("street"), resultSet.getString("zip_code")),
                        resultSet.getString("user_login")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Organization object) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL.UPDATE_CORDS.QUERY)) {
            statement.setDouble(1, object.getCoordinates().getX());
            statement.setFloat(2, object.getCoordinates().getY());
            statement.setLong(3, object.getId());
            statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement statement = connection.prepareStatement(SQL.UPDATE_ADDRESS.QUERY)) {
            if (object.getOfficialAddress().getStreet() == null) {
                statement.setNull(1, Types.VARCHAR);
            } else {
                statement.setString(1, object.getOfficialAddress().getStreet());
            }
            if (object.getOfficialAddress().getZipCode() == null) {
                statement.setNull(2, Types.VARCHAR);
            } else {
                statement.setString(2, object.getOfficialAddress().getZipCode());
            }
            statement.setLong(3, object.getId());
            statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement statement = connection.prepareStatement(SQL.UPDATE.QUERY)) {
            statement.setString(1, object.getName());
            statement.setTimestamp(2, Timestamp.valueOf(object.getCreationDate()));
            if (object.getAnnualTurnover() == null) {
                statement.setNull(3, Types.INTEGER);
            } else {
                statement.setInt(3, object.getAnnualTurnover());
            }
            statement.setInt(4, object.getEmployeesCount());
            if (object.getType() == null) {
                statement.setNull(5, Types.VARCHAR);
            } else {
                statement.setString(5, object.getType().toString());
            }
            statement.setString(6, object.getAuthorLogin());
            statement.setLong(7, object.getId());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(Long key) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL.DELETE_CORDS.QUERY)) {
            statement.setLong(1, key);
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }        try (PreparedStatement statement = connection.prepareStatement(SQL.DELETE_ADDRESS.QUERY)) {
            statement.setLong(1, key);
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement statement = connection.prepareStatement(SQL.DELETE.QUERY)) {
            statement.setLong(1, key);
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deleteAll() {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL.DELETEALL.QUERY)) {
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement statement = connection.prepareStatement(SQL.DELETEALL_CORDS.QUERY)) {
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement statement = connection.prepareStatement(SQL.DELETEALL_ADDRESS.QUERY)) {
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
