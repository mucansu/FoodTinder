package com.example.demo.Service.Interfaces;

import java.util.Optional;

public interface IBaseService<T> {
    void add(T t);
    T findById(Long id);
    void deleteById(Long id);

}
