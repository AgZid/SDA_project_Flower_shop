package com.repository;

import com.util.HibernateUtil;
import lombok.Data;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

@Data
public class Repository<T> implements CRUD<T>{

    private static final Logger LOGGER = Logger.getLogger(Repository.class);
    protected final Session session;
    private String tableName;
    private Class<T> queryClass;

    public Repository(Class<T> queryClass) {
        this(queryClass, queryClass.getSimpleName());
    }

    public Repository(Class<T> queryClass, String tableName) {
        this.queryClass = queryClass;
        this.tableName = tableName;
        this.session = HibernateUtil.getSessionFactory().openSession();
    }


    @Override
    public List<T> findAll() {
        LOGGER.info("Find all from " + queryClass);
        return session.createQuery(String.format(SQLQueries.SELECT_ALL, tableName), queryClass)
                .getResultList();
    }
    @Override
    public T findById(Integer id) {
        LOGGER.info("Find by id from " + queryClass + " where id = " + id);
        return session.createQuery(String.format(SQLQueries.SELECT_BY_ID, tableName), queryClass)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void createAndUpdate(T recordToCreateOrUpdate) {
        LOGGER.info("Create or update " + recordToCreateOrUpdate);
        if (recordToCreateOrUpdate != null) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(recordToCreateOrUpdate);
            transaction.commit();
            System.out.println(recordToCreateOrUpdate + " was saved");
        } else {
            System.out.println(recordToCreateOrUpdate + "was not saved!");
        }
    }

    @Override
    public void deleteRecord(T deleteToRemove) {
        LOGGER.info("Removed " + deleteToRemove);
        if (deleteToRemove != null) {
            Transaction transaction = session.beginTransaction();
            session.delete(deleteToRemove);
            transaction.commit();
            System.out.println(deleteToRemove + " was removed");
        } else {
            System.out.println(deleteToRemove + "was not removed!");
        }
    }

    @Override
    public void deleteAll() {
        LOGGER.info("Removed all records from" + tableName);
        if (tableName != null) {
            session.beginTransaction();
            Query q1 = session.createQuery(String.format(SQLQueries.DELETE_ALL, tableName));
            int deleted = q1.executeUpdate();
            session.getTransaction().commit();

            System.out.println(tableName + " were removed");
        } else {
            System.out.println(tableName + "were not removed!");
        }
    }

}
