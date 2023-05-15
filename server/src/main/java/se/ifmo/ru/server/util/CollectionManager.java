package se.ifmo.ru.server.util;

import java.util.*;
import se.ifmo.ru.common.model.Organization;
import se.ifmo.ru.server.util.exceptions.UniqueElementException;

/**
 * Класс управляющий коллекцией
 */
public class CollectionManager {
    private static CollectionManager collectionManager;
    private final HashSet<Organization> collection;
    private final Date createDate;
    private final HashSet<Long> idSet;

    /**
     * Конструктор класса
     * <p>
     * Устанавливает в {@code createDate} текущее время
     */
    private CollectionManager() {
        this.collection = new HashSet<>();
        this.createDate = new Date();
        this.idSet = new HashSet<>();
    }

    /**
     * Получить ссылку
     */
    public static CollectionManager getInstance() {
        if (collectionManager == null) {
            collectionManager = new CollectionManager();
        }
        return collectionManager;
    }

    /**
     * @param element элемент класса Organisation, который надо добавить в коллекцию, может содержать ID, или не содержать
     */
    public void add(Organization element) {
        if (idSet.contains(element.getId())) throw new UniqueElementException("Элемент с таким id уже есть.");
        Long id = element.getId();
        if (id == null) {
            Random random = new Random();
            do {
                id = Math.abs(random.nextLong());
            } while (this.idSet.contains(id));
            element.setId(id);
        }
        this.idSet.add(id);
        collection.add(element);
    }

    /**
     * Получить объект коллекции
     *
     * @return collection объект коллекции, с которым ведется работа
     */
    public HashSet<Organization> getCollection() {
        return collection;
    }

    /**
     * Получить дату создания обработчика и коллекции
     *
     * @return createDate
     */
    public Date getCreationDate() {
        return createDate;
    }

    /**
     * @return size число элементов коллекции
     */
    public int getSize() {
        return collection.size();
    }

    /**
     * Очистить коллекцию
     */
    public void clear() {
        this.collection.clear();
        this.idSet.clear();
    }

    /**
     * Получить элемент по ID
     *
     * @param id элемента, по которому ведется поиск
     * @return элемент коллекции с переданным {@code ID}, {@code null}, если элемент не найден
     */
    public Organization getElementById(Long id) {
        for (Organization organization : this.collection) {
            if (Objects.equals(organization.getId(), id)) {
                return organization;
            }
        }
        return null;
    }

    /**
     * Получить отсортированный список с элементами коллекции
     *
     * @return {@code List<Organisation>} - отсортированный список с элементами коллекции
     */
    public List<Organization> getSortedList() {
        List<Organization> list = new ArrayList<>(this.collection);
        Collections.sort(list);
        return list;
    }

    /**
     * Получить список с элементами коллекции
     *
     * @return {@code List<Organisation>} - список с элементами коллекции
     */
    public List<Organization> getList() {
        List<Organization> list = new ArrayList<>(this.collection);
        return list;
    }

    /**
     * Проверка на пустоту коллекции
     *
     * @return {@code true} если коллекция не содержит элементов
     */
    public boolean isEmpty() {
        return this.collection.isEmpty();
    }

    /**
     * Получить минимальный элемент коллекции
     *
     * @return минимальный элемент коллекции, {@code null}, если элемент не найден
     */
    public Organization getMin() {
        if (!this.isEmpty()) {
            return this.getSortedList().get(0);
        } else {
            return null;
        }
    }

    /**
     * Получить максимальный элемент коллекции
     *
     * @return максимальный элемент коллекции, {@code null}, если элемент не найден
     */
    public Organization getMax() {
        if (!this.isEmpty()) {
            return this.getSortedList().get(this.getSize() - 1);
        } else {
            return null;
        }
    }

    /**
     * Удалить элемент из коллекции
     *
     * @param element удаляемый элемент из коллекции
     */
    public void remove(Organization element) {
        this.idSet.remove(element.getId());
        this.collection.remove(element);
    }
}
