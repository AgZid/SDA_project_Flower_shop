package com;

import com.model.Customer;
import com.model.Flower;
import com.model.FlowersForOrdering;
import com.model.FlowersOrder;
import com.primaryData.PrimaryData;
import com.repository.Repository;

import java.time.LocalDate;
import java.util.List;

public class Main {

    private Repository repository;

    public static void main(String[] args) {

        new PrimaryData().loadPrimaryData();

        Main main = new Main();

        main.repository = new Repository();


        System.out.println();
        System.out.println(main.repository.findById(Flower.class, 1, "Flower"));

        System.out.println("Make order");
        FlowersOrder flowersOrder = FlowersOrder.builder()
                .customer(main.repository.findById(Customer.class, 1, "Customer"))
                .orderDate(LocalDate.of(2022, 5, 3))
                .deliveryDay(LocalDate.of(2022, 5, 8))
                .build();

        FlowersForOrdering flowersForOrdering = FlowersForOrdering.builder()
                .flower(main.repository.findById(Flower.class, 1, "Flower"))
                .quantity(5)
                .build();

        flowersOrder.setFlowersForOrderings(List.of(flowersForOrdering));

        System.out.println("Find by FK:");
//             main.repository.findBYForeignKey(FlowersOrder.class, "FlowersOrder", "customer", 1).forEach(System.out::println);

            main.repository.deleteRecordsFromTable("Customer");

    }
}
