package com.repository;

import com.model.Flower;

public class FlowerRepository extends Repository<Flower> {

    public FlowerRepository() {
        super(Flower.class);
    }

    public Flower findByName(String name) {
        return session.createQuery(String.format(SQLQueries.SELECT_BY_NAME, "Flower", "name"), Flower.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
