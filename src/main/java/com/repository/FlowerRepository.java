package com.repository;

import com.model.Flower;
import org.apache.log4j.Logger;

public class FlowerRepository extends Repository<Flower> {

    private static final Logger LOGGER = Logger.getLogger(Repository.class);

    public FlowerRepository() {
        super(Flower.class);
    }

    public Flower findByName(String name) {
        LOGGER.info("Find by name from Flower where name = " + name);
        return session.createQuery(String.format(SQLQueries.SELECT_BY_NAME, "Flower", "name"), Flower.class)
                .setParameter("name", name)
                .getSingleResult();
    }

}
