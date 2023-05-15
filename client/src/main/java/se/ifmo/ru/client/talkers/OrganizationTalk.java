package se.ifmo.ru.client.talkers;

import java.util.NoSuchElementException;
import se.ifmo.ru.client.exceptions.WrongCommandLineArgumentsException;
import se.ifmo.ru.common.model.Address;
import se.ifmo.ru.common.model.Coordinates;
import se.ifmo.ru.common.model.Organization;
import se.ifmo.ru.common.model.OrganizationType;
import se.ifmo.ru.common.model.exceptions.ElementConstructorException;

/**
 * Класс для получения и валидации полей класса {@link Organization Organization}
 */
public class OrganizationTalk implements Talk<Organization> {
    private final Talker talker;

    public OrganizationTalk(Talker talker) {
        this.talker = talker;
    }

    /**
     * Собрать объект {@link Organization организации}
     *
     * @return собранный объект {@link Organization Organisation}
     * @throws ElementConstructorException ошибка сборки объекта класса {@link Organization Organisation}
     */
    public Organization talk() throws ElementConstructorException {
        return new Organization(
                getName(),
                new Coordinates(getX(), getY()),
                getAnnualTurnover(),
                getEmployeesCount(),
                getType(),
                new Address(getStreet(), getZipCode())
        );
    }

    /**
     * Получить имя
     *
     * @return имя {@link Organization Organisation}
     */
    private String getName() {
        talker.print("Введите название организации (непустая, строка): ");
        String name;
        try {
            String line = talker.read().trim();
            line = line.isEmpty() ? null : line;
            name = line;
            if (name == null) {
                throw new WrongCommandLineArgumentsException();
            }
        } catch (NoSuchElementException e) {
            name = "";
            System.exit(1);
        } catch (Exception e) {
            talker.println("Ошибка ввода: введите корректное значение.");
            return getName();
        }
        return name;
    }

    /**
     * Получить координату X
     *
     * @return координата X {@link Coordinates Coordinates}
     */
    private Double getX() {
        talker.print("Введите координату x организации (непустая, double, не больше 76): ");
        Double x;
        try {
            String line = talker.read().trim();
            line = line.isEmpty() ? null : line;
            x = Double.parseDouble(line.replace(',', '.'));
            if (x > 76) {
                throw new WrongCommandLineArgumentsException();
            }
        } catch (NoSuchElementException e) {
            x = null;
            System.exit(1);
        } catch (Exception e) {
            talker.println("Ошибка ввода: введите корректное значение. ");
            return getX();
        }
        return x;
    }

    /**
     * Получить координату Y
     *
     * @return координата Y {@link Coordinates Coordinates}
     */
    private Float getY() {
        talker.print("Введите координату y организации (непустая, float): ");
        Float y;
        try {
            String line = talker.read().trim();
            line = line.isEmpty() ? null : line;
            y = Float.parseFloat(line.replace(',', '.'));
        } catch (NoSuchElementException e) {
            y = null;
            System.exit(1);
        } catch (Exception e) {
            talker.println("Ошибка ввода: введите корректное значение.");
            return getY();
        }
        return y;
    }

    /**
     * Получить годовой оборот
     *
     * @return годовой оборот {@link Organization Organisation}
     */
    private Integer getAnnualTurnover() {
        talker.print("Введите годовой оборот annualTurnover организации (integer, больше 0): ");
        Integer annualTurnover;
        try {
            String line = talker.read().trim();
            line = line.isEmpty() ? null : line;
            if (line == null) {
                return null;
            }
            annualTurnover = Integer.parseInt(line);
            if (annualTurnover <= 0) {
                throw new WrongCommandLineArgumentsException();
            }
        } catch (NoSuchElementException e) {
            annualTurnover = null;
            System.exit(1);
        } catch (Exception e) {
            talker.println("Ошибка ввода: введите корректное значение.");
            return getAnnualTurnover();
        }
        return annualTurnover;
    }

    /**
     * Получить количество сотрудников
     *
     * @return количество сотрудников {@link Organization Organisation}
     */
    private int getEmployeesCount() {
        talker.print("Введите количество сотрудников employeesCount организации (int, больше 0): ");
        int employeesCount;
        try {
            String line = talker.read().trim();
            line = line.isEmpty() ? null : line;
            employeesCount = Integer.parseInt(line);
            if (employeesCount <= 0) {
                throw new WrongCommandLineArgumentsException();
            }
        } catch (NoSuchElementException e) {
            employeesCount = 0;
            System.exit(1);
        } catch (Exception e) {
            talker.println("Ошибка ввода: введите корректное значение.");
            return getEmployeesCount();
        }
        return employeesCount;
    }

    /**
     * Получить тип организации
     *
     * @return тип организации {@link OrganizationType OrganisationType}
     */
    private OrganizationType getType() {
        talker.print("Введите тип организации type организации (один из: COMMERCIAL, PUBLIC, TRUST, PRIVATE_LIMITED_COMPANY, OPEN_JOINT_STOCK_COMPANY): ");
        OrganizationType type;
        try {
            String line = talker.read().trim();
            line = line.isEmpty() ? null : line;
            if (line == null) {
                return null;
            }
            type = OrganizationType.valueOf(line);
        } catch (NoSuchElementException e) {
            type = null;
            System.exit(1);
        } catch (Exception e) {
            talker.println("Ошибка ввода: введите корректное значение.");
            return getType();
        }
        return type;
    }

    /**
     * Получить имя улицы
     *
     * @return имя улицы {@link Address Address}
     */
    private String getStreet() {
        talker.print("Введите название улицы street организации (строка): ");
        String street;
        try {
            String line = talker.read().trim();
            line = line.isEmpty() ? null : line;
            street = line;
        } catch (NoSuchElementException e) {
            street = null;
            System.exit(1);
        } catch (Exception e) {
            talker.println("Ошибка ввода: введите корректное значение.");
            return getStreet();
        }
        return street;
    }

    /**
     * Получить зип код
     *
     * @return зип код {@link Address Address}
     */
    private String getZipCode() {
        talker.print("Введите зип код zipCode организации (строка): ");
        String zipCode;
        try {
            String line = talker.read().trim();
            line = line.isEmpty() ? null : line;
            zipCode = line;
        } catch (NoSuchElementException e) {
            zipCode = null;
            System.exit(1);
        } catch (Exception e) {
            talker.println("Ошибка ввода: введите корректное значение.");
            return getZipCode();
        }
        return zipCode;
    }
}
