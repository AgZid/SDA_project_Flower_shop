package com.repository;

import com.model.FlowersOrder;

import javax.persistence.NoResultException;
import java.util.List;

public class FlowersOrderRepository extends Repository<FlowersOrder>{

    public FlowersOrderRepository() {
        super(FlowersOrder.class);
    }

    public List<FlowersOrder> findByForeignKey(String foreignKeyFieldName, Integer id) {
        try {

            String query = String.format(SQLQueries.SELECT_BY_FOREIGN_KEY, "FlowersOrder", foreignKeyFieldName);
            System.out.println("Query: " + query);
            return session.createQuery(query, FlowersOrder.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (NoResultException e) {
            System.out.println("ERROR: ID not found");
            return null;
        }
    }
}
