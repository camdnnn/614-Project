package com.flightreservation.data.repository;

import java.util.List;

public interface Repository<T> {
    public void create(T data);
    public T read(int id);
    public void update(int id, T data);
    public void delete(int id);
    public List<T> getAll();
}
