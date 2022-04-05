package com.repository;

import com.model.Customer;
import org.apache.log4j.Logger;

public class CustomerRepository extends Repository<Customer>{

    private static final Logger LOGGER = Logger.getLogger(Repository.class);

    public CustomerRepository() {
        super(Customer.class);
    }

    public Customer findByFullName(String fullName) {
        LOGGER.info("Find by name from Flower where fullName = " + fullName);
        return session.createQuery(String.format(SQLQueries.SELECT_BY_NAME, "Customer", "fullName"), Customer.class)
                .setParameter("name", fullName)
                .getSingleResult();
    }
}
