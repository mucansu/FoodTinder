package com.example.demo.Service;

import com.example.demo.Exceptions.RecordNotFoundException;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public abstract class BaseService<T, ID> {

    protected abstract CrudRepository<T, ID> getRepository();

    public T findById(ID id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Not found"));
    }

    public Iterable<T> findAll() {
        return getRepository().findAll();
    }

    public T save(T entity) {
        return getRepository().save(entity);
    }

    public T deleteById(ID id) {
        T entity = findById(id);
        getRepository().delete(entity);
        return entity;
    }

}

