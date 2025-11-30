package com.flightreservation.data.repository.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import com.flightreservation.data.repository.Repository;

public class InMemoryRepository<T> implements Repository<T> {
    private final Map<Integer, T> store = new HashMap<>();
    private final Function<T, Integer> idExtractor;

    public InMemoryRepository(Function<T, Integer> idExtractor) {
        this.idExtractor = Objects.requireNonNull(idExtractor);
    }

    @Override
    public void create(T data) {
        store.put(idExtractor.apply(data), data);
    }

    @Override
    public T read(int id) {
        return store.get(id);
    }

    @Override
    public void update(int id, T data) {
        store.put(id, data);
    }

    @Override
    public void delete(int id) {
        store.remove(id);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(store.values());
    }
}
