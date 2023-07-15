package com.example.demo.Service.Interfaces;

public interface IBaseService<T> {
    T add(T t);
    T findById(Long id);
    T deleteById(Long id);
}
