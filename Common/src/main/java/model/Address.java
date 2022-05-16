package model;


import model.exceptions.ElementConstructorException;

import java.io.Serializable;

/**
 * Класс с адресом Organisation
 *
 * @author Лебедев Вячеслав
 */
public class Address implements Serializable {
    private String street; //Строка не может быть пустой, Поле может быть null
    private String zipCode; //Поле может быть null

    /**
     * @param street улица
     * @param zipCode зип код
     */
    public Address(String street, String zipCode) throws ElementConstructorException {
        setStreet(street);
        setZipCode(zipCode);
    }

    /**
     * @return строковое представление объекта
     */
    @Override
    public String toString() {
        return "Адрес " +
                "улица " + street +
                ", зип код " + zipCode;
    }


    /**
     * @param street параметр сеттера улицы
     */
    public void setStreet(String street) throws ElementConstructorException {
        if (street == null || !street.isEmpty()) {
            this.street = street;
        } else {
            throw new ElementConstructorException("улица");
        }
    }

    /**
     * @return улица
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param zipCode параметр сеттера зап кода
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return зип код
     */
    public String getZipCode() {
        return zipCode;
    }
}