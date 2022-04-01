package com.repository;

public class SQLQueries {
    public static final String SELECT_ALL = "SELECT p FROM %s p";
    public static final String SELECT_BY_ID = "SELECT p FROM %s p WHERE p.flowerId = :id";
    public static final String SELECT_BY_NAME = "SELECT p FROM %s p WHERE p.name = :name";

}
