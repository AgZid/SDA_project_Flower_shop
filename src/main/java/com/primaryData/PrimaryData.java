package com.primaryData;

import com.enumerators.OrderStatus;
import com.google.gson.reflect.TypeToken;
import com.model.Customer;
import com.model.Flower;
import com.model.OrderedEntry;
import com.model.FlowersOrder;
import com.repository.CustomerRepository;
import com.repository.FlowerRepository;
import com.service.customExceptions.IncorrectArgument;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.primaryData.FromFileToObject.extractFromJsonFile;

public class PrimaryData {

    public void loadPrimaryData() throws IncorrectArgument {

        com.repository.FlowerRepository flowerRepository = new FlowerRepository();
        com.repository.CustomerRepository customerRepository = new CustomerRepository();

        List<Flower> flowers = extractFromJsonFile("src/main/resources/flowers.json", new TypeToken<>() {
        });

        for (Flower flower : flowers) {
            flowerRepository.createOrUpdate(flower);
        }

        Customer jonas = Customer.builder()
                .fullName("Jonas Jonaitis")
                .email("jonas@org.com")
                .phoneNumber("223658")
                .build();

        FlowersOrder jonasOrder1 = FlowersOrder.builder()
                .customer(jonas)
                .orderDate(LocalDateTime.now())
                .deliveryDay(LocalDate.of(2022, 3, 15))
                .orderStatus(OrderStatus.ORDERED)
                .build();

        FlowersOrder jonasOrder2 = FlowersOrder.builder()
                .customer(jonas)
                .orderDate(LocalDateTime.now())
                .deliveryDay(LocalDate.of(2022, 4, 10))
                .orderStatus(OrderStatus.CANCELED)
                .build();

        List<FlowersOrder> jonasOrders = new ArrayList<>();
        jonasOrders.add(jonasOrder1);
        jonasOrders.add(jonasOrder2);
        jonas.setOrders(jonasOrders);

        OrderedEntry jonasFlowers1 = OrderedEntry.builder()
                .flowersOrder(jonasOrder1)
                .flower(flowerRepository.findById(2))
                .quantity(3)
                .build();

        jonasOrder1.setOrderedEntries(List.of(jonasFlowers1));

        OrderedEntry jonasFlowers2 = OrderedEntry.builder()
                .flowersOrder(jonasOrder1)
                .flower(flowerRepository.findById(5))
                .quantity(5)
                .build();

        jonasOrder1.setOrderedEntries(Arrays.asList(jonasFlowers1, jonasFlowers2));

        OrderedEntry jonasFlowers3 = OrderedEntry.builder()
                .flowersOrder(jonasOrder2)
                .flower(flowerRepository.findById(10))
                .quantity(5)
                .build();

        jonasOrder2.setOrderedEntries(Arrays.asList(jonasFlowers3));

        customerRepository.createOrUpdate(jonas);
    }
}
