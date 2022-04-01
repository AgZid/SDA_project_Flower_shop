package com.primaryData;

import com.google.gson.reflect.TypeToken;
import com.model.Customer;
import com.model.Flower;
import com.model.FlowersForOrdering;
import com.model.FlowersOrder;
import com.repository.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.primaryData.FromFileToObject.extractFromJsonFile;
import static com.repository.SQLQueries.SELECT_BY_ID;

public class PrimaryData {

    public void loadPrimaryData() {

        com.repository.Repository repository = new Repository();

        List<Flower> flowers = extractFromJsonFile("src/main/resources/flowers.json", new TypeToken<>(){});

        for (Flower flower : flowers) {
            repository.createOrUpdateRecord(flower);
        }

        Customer jonas = Customer.builder()
                .fullName("Jonas Jonaitis")
                .email("jonas@org.com")
                .phoneNumber("223658")
                .billingAddress("Address 1")
                .build();

        FlowersOrder jonasOrder1 = FlowersOrder.builder()
                .customer(jonas)
                .orderDate(LocalDate.of(2022, 3, 5))
                .deliveryDay(LocalDate.of(2022, 3, 15))
                .build();

        FlowersOrder jonasOrder2 = FlowersOrder.builder()
                .customer(jonas)
                .orderDate(LocalDate.of(2022, 4, 1))
                .deliveryDay(LocalDate.of(2022, 4, 10))
                .build();

        jonas.setOrders(List.of(jonasOrder1, jonasOrder2));

        FlowersForOrdering jonasFlowers1 = FlowersForOrdering.builder()
                .flowersOrder(jonasOrder1)
                .flower(repository.findById(SELECT_BY_ID, Flower.class, 2, "Flower"))
                .quantity(3)
                .build();

        jonasOrder1.setFlowersForOrderings(List.of(jonasFlowers1));

        FlowersForOrdering jonasFlowers2 = FlowersForOrdering.builder()
                .flowersOrder(jonasOrder1)
                .flower(repository.findById(SELECT_BY_ID, Flower.class, 5, "Flower"))
                .quantity(5)
                .build();

        jonasOrder1.setFlowersForOrderings(List.of(jonasFlowers1, jonasFlowers2));

        FlowersForOrdering jonasFlowers3 = FlowersForOrdering.builder()
                .flowersOrder(jonasOrder2)
                .flower(repository.findById(SELECT_BY_ID, Flower.class, 10, "Flower"))
                .quantity(5)
                .build();

        jonasOrder2.setFlowersForOrderings(List.of(jonasFlowers3));

        repository.createOrUpdateRecord(jonas);

    }
}
