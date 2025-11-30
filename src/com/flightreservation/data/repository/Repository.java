package com.flightreservation.data.repository;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;

public abstract class Repository<T> {
    public abstract void create(T data);
    public abstract T read(int id);
    public abstract void update(int id, T data);
    public abstract void delete(int id);
    public abstract List<T> getAll();
    protected abstract T mapRow(ResultSet rs) throws SQLException;
}
