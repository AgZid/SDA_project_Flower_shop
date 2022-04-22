package com.repository;

import com.service.customExceptions.IncorrectArgument;
import com.util.HibernateUtil;
import lombok.Data;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;

@Data
public class Repository<T> implements CRUD<T> {

    public static Session session = HibernateUtil.getSessionFactory().openSession();
    private String tableName;
    private Class<T> queryClass;

    public Repository(Class<T> queryClass) {
        this(queryClass, queryClass.getSimpleName());
    }

    public Repository(Class<T> queryClass, String tableName) {
        this.queryClass = queryClass;
        this.tableName = tableName;
    }


    @Override
    public List<T> findAll() {
        return session.createQuery(String.format(SQLQueries.SELECT_ALL, tableName), queryClass)
                .getResultList();
    }

    @Override
    public T findById(Integer id) {
        try {
            return session.createQuery(String.format(SQLQueries.SELECT_BY_ID, tableName), queryClass)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("ERROR: ID not found");
            return null;
        }
    }

    @Override
    public void createOrUpdate(T recordToCreateOrUpdate) throws IncorrectArgument {
        if (recordToCreateOrUpdate == null) {throw new IncorrectArgument("Value was not passed. Record was not saved.");}
        else {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(recordToCreateOrUpdate);
            transaction.commit();
            System.out.println(recordToCreateOrUpdate + " was saved");
        }
//        if (recordToCreateOrUpdate != null) {
//            Transaction transaction = session.beginTransaction();
//            session.saveOrUpdate(recordToCreateOrUpdate);
//            transaction.commit();
//            System.out.println(recordToCreateOrUpdate + " was saved");
//        } else {
//            System.out.println(recordToCreateOrUpdate + "was not saved!");
//        }
    }

    @Override
    public void deleteRecord(T deleteRecord) {
        if (deleteRecord != null) {
            Transaction transaction = session.beginTransaction();
            session.delete(deleteRecord);
            transaction.commit();
            System.out.println(deleteRecord + " was removed");
        } else {
            System.out.println(deleteRecord + " was not removed!");
        }
    }

    @Override
    public void deleteAll() {
        if (tableName != null) {
            session.beginTransaction();
            Query q1 = session.createQuery(String.format(SQLQueries.DELETE_ALL, tableName));
            int deleted = q1.executeUpdate();
            session.getTransaction().commit();

            System.out.println(tableName + " were removed");
        } else {
            System.out.println(tableName + " were not removed!");
        }
    }
}
