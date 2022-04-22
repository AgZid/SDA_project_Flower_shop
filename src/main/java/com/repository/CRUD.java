package com.repository;

import com.service.customExceptions.IncorrectArgument;

import java.util.List;

public interface CRUD<T> {

    void createOrUpdate(T recordToCreateOrUpdate) throws IncorrectArgument;

    List<T> findAll();

    T findById(Integer id);

    void deleteRecord(T recordToDelete);

    void deleteAll();


}
