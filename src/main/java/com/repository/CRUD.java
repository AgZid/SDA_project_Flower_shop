package com.repository;

import java.util.List;

public interface CRUD<T> {

    void createOrUpdate(T recordToCreateOrUpdate);

    List<T> findAll();

    T findById(Integer id);

    void deleteRecord(T recordToDelete);

    void deleteAll();


}
