package com.repository;

public class SQLQueries {
    public static final String SELECT_ALL = "SELECT p FROM %s p";
    public static final String SELECT_BY_ID = "SELECT p FROM %s p WHERE p.id = :id";
    public static final String SELECT_BY_FOREIGN_KEY = "SELECT p FROM %s p join p.%s c WHERE c.id =:id";
    public static final String SELECT_BY_NAME = "SELECT p FROM %s p WHERE p.name = :name";
    public static final String DELETE_ALL = "DELETE FROM %s";

}
