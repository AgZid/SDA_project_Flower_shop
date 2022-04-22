package com.repository;

import com.enumerators.OrderStatus;
import com.model.Flower;
import com.model.OrderedEntry;
import com.model.FlowersOrder;
import com.service.customExceptions.IncorrectArgument;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class FlowersOrderRepositoryTest {

    FlowersOrderRepository flowersOrderRepository = new FlowersOrderRepository();
    FlowerRepository flowerRepository = new FlowerRepository();

    @BeforeEach
    public void SetUpFlowers() throws IncorrectArgument {

        Flower roze = Flower.builder()
                .name("TestRoze")
                .price(5.5)
                .color("RozeColor")
                .amount(50)
                .build();

        Flower tulpe = Flower.builder()
                .name("TestTulpe")
                .price(2.0)
                .color("TestColor")
                .amount(60)
                .build();

        flowerRepository.createOrUpdate(roze);
        flowerRepository.createOrUpdate(tulpe);

        FlowersOrder testFlowersOrder = FlowersOrder.builder()
                .orderStatus(OrderStatus.ORDERED)
                .orderDate(LocalDateTime.now())
                .deliveryDay(LocalDate.of(2022, 5, 8))
                .build();

        OrderedEntry testOrderedEntry = OrderedEntry.builder()
                .flower(roze)
                .quantity(20)
                .flowersOrder(testFlowersOrder)
                .build();

        testFlowersOrder.setOrderedEntries(List.of(testOrderedEntry));

        FlowersOrder testFlowersOrder2 = FlowersOrder.builder()
                .orderStatus(OrderStatus.ORDERED)
                .orderDate(LocalDateTime.now())
                .deliveryDay(LocalDate.of(2022, 5, 8))
                .build();

        OrderedEntry testOrderedEntry2 = OrderedEntry.builder()
                .flower(tulpe)
                .quantity(20)
                .flowersOrder(testFlowersOrder2)
                .build();

        testFlowersOrder2.setOrderedEntries(List.of(testOrderedEntry2));

        flowersOrderRepository.createOrUpdate(testFlowersOrder);
        flowersOrderRepository.createOrUpdate(testFlowersOrder2);
    }

    @AfterEach
    public void deleteOrders() {
        flowersOrderRepository.findAll().forEach(flowersOrder -> flowersOrderRepository.deleteRecord(flowersOrder));
    }

    @Test
    void createAndUpdate_create() throws IncorrectArgument {
        FlowersOrder testFlowerOrder = FlowersOrder.builder()
                .orderDate(LocalDateTime.now())
                .deliveryDay(LocalDate.of(2022, 4, 20))
                .orderStatus(OrderStatus.ORDERED)
                .build();

        OrderedEntry testOrderEntity = OrderedEntry.builder()
                .flower(flowerRepository.findByName("TestTulpe"))
                .quantity(22)
                .build();
        testFlowerOrder.setOrderedEntries(List.of(testOrderEntity));

        flowersOrderRepository.createOrUpdate(testFlowerOrder);

        assertThat(flowersOrderRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    void findAll() {
        assertThat(flowersOrderRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    void findById() {
        FlowersOrder testFlowersOrder = flowersOrderRepository.findAll().stream().findFirst().orElse(null);
        Integer testFlowersOrderId = testFlowersOrder.getId();

        assertThat(flowersOrderRepository.findById(testFlowersOrderId)).isEqualTo(testFlowersOrder);
    }

    @Test
    void findByForeignKey() {
        FlowersOrder testFlowerOrder = flowersOrderRepository.findAll().stream().findFirst().orElse(null);
        OrderedEntry testOrderEntity = testFlowerOrder.getOrderedEntries().stream().findFirst().orElse(null);
        Integer testForeignKeyId = testOrderEntity.getId();
        List<FlowersOrder> orderedFlowersQuantities =
                flowersOrderRepository.findByForeignKey("orderedEntries", testForeignKeyId);
        assertThat(orderedFlowersQuantities).contains(testFlowerOrder);

    }

    @Test
    void createAndUpdate_update() {
        FlowersOrder testFlowersOrder = flowersOrderRepository.findAll().stream().findFirst().orElse(null);
        testFlowersOrder.setOrderStatus(OrderStatus.CANCELED);

        assertThat(flowersOrderRepository
                .findById(testFlowersOrder.getId()).getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
    }

    @Test
    void deleteRecord() {
        FlowersOrder flowersOrder = flowersOrderRepository.findAll().stream().findFirst().orElse(null);
        flowersOrderRepository.deleteRecord(flowersOrder);

        assertThat(flowersOrderRepository.findAll().size()).isEqualTo(1);
    }
}