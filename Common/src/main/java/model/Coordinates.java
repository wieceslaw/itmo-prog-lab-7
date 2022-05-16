package model;

import model.exceptions.ElementConstructorException;

import java.io.Serializable;

/**
 * Класс с координатами Organisation
 *
 * @author Лебедев Вячеслав
 */
public class Coordinates implements Serializable {
    private Double x; //Максимальное значение поля: 76, Поле не может быть null
    private Float y; //Поле не может быть null

    /**
     * Конструктор
     *
     * @param x координата x
     * @param y координата y
     */
    public Coordinates(Double x, Float y) throws ElementConstructorException {
        setX(x);
        setY(y);
    }

    /**
     * @return строковое представление объекта
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /**
     * @return координату x
     */
    public Double getX() {
        return x;
    }

    /**
     * @param x параметр сеттера координаты x
     */
    public void setX(Double x) throws ElementConstructorException {
        if (x != null && x <= 76) {
            this.x = x;
        } else {
            throw new ElementConstructorException("координата x");
        }
    }

    /**
     *
     * @return координату y
     */
    public Float getY() {
        return y;
    }

    /**
     * @param y параметр сеттера координаты y
     */
    public void setY(Float y) throws ElementConstructorException {
        if (y != null) {
            this.y = y;
        } else {
            throw new ElementConstructorException("координата y");
        }
    }
}
