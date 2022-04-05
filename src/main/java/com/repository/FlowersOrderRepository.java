package com.repository;

import com.model.FlowersOrder;
import org.apache.log4j.Logger;

import java.util.List;

public class FlowersOrderRepository extends Repository<FlowersOrder>{

    private static final Logger LOGGER = Logger.getLogger(Repository.class);

    public FlowersOrderRepository() {
        super(FlowersOrder.class);
    }

    public List<FlowersOrder> findBYForeignKey(String foreignKeyFieldName, Integer id) {
        LOGGER.info("Find by foreign key from " + FlowersOrder.class);
        String query = String.format(SQLQueries.SELECT_BY_FOREIGN_KEY, "FlowersOrder", foreignKeyFieldName);
        System.out.println("Query: " + query);
        return session.createQuery(query, FlowersOrder.class)
                .setParameter("id", id)
                .getResultList();
    }
}
