package com.repository;

import com.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class Repository {

    private static final Logger LOGGER = Logger.getLogger(Repository.class);
    private static Session session = HibernateUtil.getSessionFactory().openSession();

    public <T> List<T> findAll(String SQLQuery, Class<T> queryClass) {
        LOGGER.info("Find all from " + queryClass);
        return session.createQuery(SQLQuery, queryClass).getResultList();
    }

    public <T> T findById(String SQLQuery, Class<T> queryClass, Integer id) {
        LOGGER.info("Find by id from " + queryClass + " where id = " + id);
        return session.createQuery(SQLQuery, queryClass)
                .setParameter("id", id)
                .getSingleResult();
    }

    public <T> T findByName(String SQLQuery, Class<T> queryClass, String name) {
        LOGGER.info("Find by name from " + queryClass + " where name = " + name);
        return session.createQuery(SQLQuery, queryClass)
                .setParameter("name", name)
                .getSingleResult();
    }

    public <T> void createOrUpdateRecord(T recordToAdd) {
        LOGGER.info("Create or update " + recordToAdd );
        if (recordToAdd != null) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(recordToAdd);
            transaction.commit();
            System.out.println(recordToAdd + " was saved");
        } else {
            System.out.println(recordToAdd + "was not saved!");
        }
    }

    public <T> void removeRecord(T recordToRemove) {
        LOGGER.info("Removed " + recordToRemove);
        if (recordToRemove != null) {
            Transaction transaction = session.beginTransaction();
            session.delete(recordToRemove);
            transaction.commit();
            System.out.println(recordToRemove + " was removed");
        } else {
            System.out.println(recordToRemove + "was not removed!");
        }
    }

}
