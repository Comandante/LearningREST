package ru.forxy.fraud.db.dao.cassandra.client;

import com.datastax.driver.core.Session;

import java.util.List;

/**
 * Cassandra DB client that uses Datastax Java driver to connect to NoSQL DB and map generic entity
 */
public interface ICassandraClient<T> {

    List<T> getAll();

    List<T> getPage(final long start, final long size);

    T getByKey(final Long key);

    boolean existsKey(Long key);

    boolean exists(final T entity);

    void add(final T entity);

    void delete(final T entity);

    void deleteByKey(final Long key);

    long count();

    public Session getSession();
}
