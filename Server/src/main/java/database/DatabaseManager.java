package database;

import database.dao.*;
import model.*;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DatabaseManager {
    private final Date createDate;
    private final Connection connection;
    private final UserDAO userDAO;
    private final OrganizationDAO organizationDAO;

    public DatabaseManager() {
        this.createDate = new Date();
        this.connection = DatabaseConnector.getConnection();
        this.userDAO = new UserDAO(this.connection);
        this.organizationDAO = new OrganizationDAO(this.connection);
    }

    public Date getCreationDate() {
        return createDate;
    }

    public boolean addUser(User user) {
        if (userDAO.read(user.getLogin()) == null) {
            return userDAO.create(user);
        }
        return false;
    }

    public User getUser(User user) {
        return userDAO.read(user);
    }

    public boolean userExists(User user) {
        return getUser(user) != null;
    }

    public boolean addOrganization(Organization organization, User user) {
        organization.setAuthorLogin(user.getLogin());
        return organizationDAO.create(organization);
    }

    public List<Organization> getOrganizations() {
        return organizationDAO.readAll();
    }

    public void clearOrganizations(User user) {
        for (Organization organization : getOrganizations()) {
            removeOrganization(organization, user);
        }
    }

    public Organization getOrganization(Long id) {
        return organizationDAO.read(id);
    }

    public void removeOrganization(Organization organization, User user) {
        if (getOrganization(organization.getId()) != null) {
            if (Objects.equals(getOrganization(organization.getId()).getAuthorLogin(), user.getLogin())) {
                organizationDAO.delete(organization.getId());
            }
        }
    }

    public List<Organization> getSortedOrganizations() {
        return getOrganizations().stream().sorted().collect(Collectors.toList());
    }

    public Organization getMaxOrganization() {
        List<Organization> list = getSortedOrganizations();
        if (list.size() == 0) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    public Organization getMinOrganization() {
        List<Organization> list = getSortedOrganizations();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    public boolean updateOrganization(Organization organization, User user, Long id) {
        organization.setId(id);
        organization.setAuthorLogin(user.getLogin());
        Organization updatableOrganization = organizationDAO.read(id);
        if (!updatableOrganization.getAuthorLogin().equals(organization.getAuthorLogin())) {
            throw new NullPointerException("Изменять организацию может только создатель.");
        }
        return organizationDAO.update(organization);
    }
}