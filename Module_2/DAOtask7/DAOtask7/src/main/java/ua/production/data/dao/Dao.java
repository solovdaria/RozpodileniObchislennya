package ua.production.data.dao;

import ua.production.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(int id) throws DaoException;

    List<T> getAll() throws DaoException;

    boolean save(T t) throws DaoException;

    T update(T t) throws DaoException;

    boolean delete(T t) throws DaoException;

}
