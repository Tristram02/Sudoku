package sudoku;

import sudoku.exceptions.DaoException;

public interface Dao<T> {
    T read() throws DaoException;

    void write(T obj) throws DaoException;
}
