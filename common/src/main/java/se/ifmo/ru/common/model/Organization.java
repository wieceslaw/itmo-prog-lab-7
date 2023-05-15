package se.ifmo.ru.common.model;

import se.ifmo.ru.common.model.exceptions.ElementConstructorException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс Organisation - элементы коллекции
 *
 * @author Лебедев Вячеслав
 */
public class Organization implements Comparable<Organization>, Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private final LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer annualTurnover; //Поле может быть null, Значение поля должно быть больше 0
    private int employeesCount; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле может быть null
    private Address officialAddress; //Поле может быть null
    private String authorLogin;

    public Organization(Long id, String name, Coordinates coordinates, LocalDateTime creationDate, Integer annualTurnover, int employeesCount, OrganizationType type, Address officialAddress, String authorLogin) throws ElementConstructorException {
        setId(id);
        setName(name);
        setCoordinates(coordinates);
        this.creationDate = creationDate;
        setAnnualTurnover(annualTurnover);
        setEmployeesCount(employeesCount);
        setType(type);
        setOfficialAddress(officialAddress);
        setAuthorLogin(authorLogin);
    }

    public Organization(String name, Coordinates coordinates, Integer annualTurnover, int employeesCount, OrganizationType type, Address officialAddress) throws ElementConstructorException {
        setName(name);
        setCoordinates(coordinates);
        this.creationDate = LocalDateTime.now();
        setAnnualTurnover(annualTurnover);
        setEmployeesCount(employeesCount);
        setType(type);
        setOfficialAddress(officialAddress);
    }

    @Override
    public String toString() {
        return "Организация " +
                "(" + id + ") " + name +
                ", координаты " + coordinates +
                ", создана " + creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss")) +
                " c годовым оборотом " + (annualTurnover == null ? "(отсутствует)" : annualTurnover)+
                " и числом сотрудников " + employeesCount +
                ", тип " + (type == null ? "(отсутствует)" : type) +
                ", расположенная по адресу: улица " +
                (officialAddress.getStreet() == null ? "(отсутствует)" : officialAddress.getStreet()) + ", " +
                (officialAddress.getZipCode() == null ? "(отсутствует)" : officialAddress.getZipCode()) + ", " +
                "создана пользователем " + authorLogin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws ElementConstructorException {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        } else {
            throw new ElementConstructorException("имя");
        }
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) throws ElementConstructorException {
        if (coordinates != null) {
            this.coordinates = coordinates;
        } else {
            throw new ElementConstructorException("координаты");
        }
    }

    public Integer getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(Integer annualTurnover) throws ElementConstructorException {
        if (annualTurnover == null || annualTurnover > 0) {
            this.annualTurnover = annualTurnover;
        } else {
            throw new ElementConstructorException("годовой оборот");
        }
    }

    public int getEmployeesCount() {
        return employeesCount;
    }

    public void setEmployeesCount(int employeesCount) throws ElementConstructorException {
        if (employeesCount > 0) {
            this.employeesCount = employeesCount;
        } else {
            throw new ElementConstructorException("число работников");
        }
    }

    public OrganizationType getType() {
        return type;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public Address getOfficialAddress() {
        return officialAddress;
    }

    public void setOfficialAddress(Address officialAddress) {
        this.officialAddress = officialAddress;
    }

    public String getAuthorLogin() {
        return authorLogin;
    }

    public void setAuthorLogin(String authorLogin) {
        this.authorLogin = authorLogin;
    }

    @Override
    public int compareTo(Organization o) {
        return this.getEmployeesCount() - o.getEmployeesCount();
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
