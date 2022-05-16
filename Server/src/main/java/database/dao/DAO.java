package database.dao;

import java.sql.Connection;
import java.util.List;

public abstract class DAO<T, K> {
    protected final Connection connection;
    public DAO(Connection connection) {
        this.connection = connection;
    }

    abstract boolean create(T object);
    abstract List<T> readAll();
    abstract T read(K key);
    abstract boolean update(T object);
    abstract boolean delete(K key);
    abstract boolean deleteAll();
}