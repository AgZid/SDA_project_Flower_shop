package com.repository;

import com.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class Repository {

    private static final Logger LOGGER = Logger.getLogger(Repository.class);
    private static final Session session = HibernateUtil.getSessionFactory().openSession();

    public <T> List<T> findAll(Class<T> queryClass, String tableName) {
        LOGGER.info("Find all from " + queryClass);
        return session.createQuery(String.format(SQLQueries.SELECT_ALL, tableName), queryClass)
                .getResultList();
    }

    public <T> List<T> findBYForeignKey(Class<T> queryClass, String tableName, String foreignKeyFieldName, Integer id) {
        LOGGER.info("Find by foreign key from " + queryClass);
        String query = String.format(SQLQueries.SELECT_BY_FOREIGN_KEY, tableName, foreignKeyFieldName);
//        String query = "SELECT p FROM FlowersOrder p join p.customer c WHERE c.id =:id";
        System.out.println("Query: " + query);
        return session.createQuery(query, queryClass)
                .setParameter("id", id)
                .getResultList();
    }

    public <T> T findById(Class<T> queryClass, Integer id, String tableName) {
        LOGGER.info("Find by id from " + queryClass + " where id = " + id);
        return session.createQuery(String.format(SQLQueries.SELECT_BY_ID, tableName), queryClass)
                .setParameter("id", id)
                .getSingleResult();
    }

    public <T> T findByName(Class<T> queryClass, String name, String tableName) {
        LOGGER.info("Find by name from " + queryClass + " where name = " + name);
        return session.createQuery(String.format(SQLQueries.SELECT_BY_NAME, tableName), queryClass)
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

    public void deleteRecordsFromTable(String tableName) {
        LOGGER.info("Removed all records from" + tableName);
//
//        try {
//            session.beginTransaction();
//
//
//            Query q1 = session.createQuery ("DELETE FROM MapField");
//            int deleted = q1.executeUpdate ();
//
//            Query q2 = session.createQuery ("DELETE FROM MapRecord");
//            int deleted = q2.executeUpdate ();
//
//        } catch (Exception e) {
//            logger.error("Error :" + e);
//            session.getTransaction().rollback();
//        } finally {
//            session.close();
//        }

        if (tableName != null) {
            session.beginTransaction();
            Query q1 = session.createQuery (String.format(SQLQueries.DELETE_ALL, tableName));
            int deleted = q1.executeUpdate ();
            session.getTransaction().commit();

            System.out.println(tableName + " were removed");
        } else {
            System.out.println(tableName + "were not removed!");
        }
    }

}
