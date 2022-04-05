package com;

import com.model.OrderedEntry;
import com.model.FlowersOrder;
import com.primaryData.PrimaryData;
import com.repository.CustomerRepository;
import com.repository.FlowerRepository;
import com.repository.FlowersOrderRepository;
import com.repository.Repository;

import java.time.LocalDate;
import java.util.List;

public class Main {

    private Repository repository;
    private FlowerRepository flowerRepository;
    private CustomerRepository customerRepository;
    private FlowersOrderRepository flowersOrderRepository;

    public static void main(String[] args) {

        new PrimaryData().loadPrimaryData();

        Main main = new Main();

        main.flowerRepository = new FlowerRepository();
        main.customerRepository = new CustomerRepository();
        main.flowersOrderRepository = new FlowersOrderRepository();


        System.out.println();
        System.out.println(main.flowerRepository.findAll());

        System.out.println("Make order");
        FlowersOrder flowersOrder = FlowersOrder.builder()
                .customer(main.customerRepository.findById(1))
                .orderDate(LocalDate.of(2022, 5, 3))
                .deliveryDay(LocalDate.of(2022, 5, 8))
                .build();

        OrderedEntry orderedEntry = OrderedEntry.builder()
                .flower(main.flowerRepository.findById(1))
                .quantity(5)
                .build();

        flowersOrder.setOrderedFlowersQuantities(List.of(orderedEntry));

        System.out.println("Find by FK:");
        main.flowersOrderRepository.findBYForeignKey("customer", 1).forEach(System.out::println);

//            main.repository.deleteAll("Customer");

    }
}
