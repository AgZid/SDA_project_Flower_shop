package com.repository;

import com.model.Customer;

import javax.persistence.NoResultException;

public class CustomerRepository extends Repository<Customer> {

    public CustomerRepository() {
        super(Customer.class);
    }

    public Customer findByFullName(String fullName) {
        try {
            return session.createQuery(String.format(SQLQueries.SELECT_BY_NAME, "Customer", "fullName"), Customer.class)
                    .setParameter("name", fullName)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }
}
