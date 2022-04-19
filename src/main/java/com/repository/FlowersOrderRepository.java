package com.repository;

import com.model.FlowersOrder;
import java.util.List;

public class FlowersOrderRepository extends Repository<FlowersOrder>{

    public FlowersOrderRepository() {
        super(FlowersOrder.class);
    }

    public List<FlowersOrder> findByForeignKey(String foreignKeyFieldName, Integer id) {
        String query = String.format(SQLQueries.SELECT_BY_FOREIGN_KEY, "FlowersOrder", foreignKeyFieldName);
        System.out.println("Query: " + query);
        return session.createQuery(query, FlowersOrder.class)
                .setParameter("id", id)
                .getResultList();
    }
}
