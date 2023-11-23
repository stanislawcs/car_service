package com.example.carservice.mappers;

public interface Mapper<T,E> {
    T toEntity(E dto);
    E toDTO(T entity);
}
