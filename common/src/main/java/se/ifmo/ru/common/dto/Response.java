package se.ifmo.ru.common.dto;

import se.ifmo.ru.common.model.Organization;

import java.io.Serializable;
import java.util.List;

/**
 * Класс передачи данных ответа с сервера
 */
public class Response implements Serializable {
    private final String text;
    private final List<Organization> elements;
    private final Status status;

    public Response(String text, List<Organization> elements, Status status) {
        this.text = text;
        this.elements = elements;
        this.status = status;
    }

    public Response(String text, Status status) {
        this.text = text;
        this.elements = null;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Response{" +
                "text='" + text + '\'' +
                ", elements=" + elements +
                ", status=" + status +
                '}';
    }

    public String getText() {
        return text;
    }

    public List<Organization> getElements() {
        return elements;
    }

    public Status getStatus() {
        return status;
    }
}
