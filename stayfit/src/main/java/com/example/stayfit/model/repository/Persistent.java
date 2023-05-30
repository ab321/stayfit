package com.example.stayfit.model.repository;

import java.util.List;

public interface Persistent<T> {
    public void save(T entity);
    public void insert(T entity);
    public void delete(T id);
    public List<T> findAll();
    public T findById(Long id);
    public void update(T entity);
}
