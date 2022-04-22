package com.service;

import com.model.Flower;
import com.repository.FlowerRepository;
import com.service.customExceptions.IncorrectArgument;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FlowersServicesTest {

    public static FlowersServices flowersServices = new FlowersServices();
    private static final FlowerRepository FLOWER_REPOSITORY = new FlowerRepository();

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

        Flower gvazdikas = Flower.builder()
                .name("TestGvazdikas")
                .price(2.0)
                .color("TestColor")
                .amount(20)
                .build();

        FLOWER_REPOSITORY.createOrUpdate(roze);
        FLOWER_REPOSITORY.createOrUpdate(tulpe);
        FLOWER_REPOSITORY.createOrUpdate(gvazdikas);
    }

    @AfterEach
    public void deleteDataBase() {
        FLOWER_REPOSITORY.deleteAll();
    }

    @Test
    void testIsValidFlowerId_valid() {
        Integer flowerId = FLOWER_REPOSITORY.findByName("TestTulpe").getId();

        assertThat(flowersServices.isValidFlowerId(flowerId)).isTrue();
    }

    @Test
    void testIsValidFlowerId_invalid() {
        assertThat(flowersServices.isValidFlowerId(5)).isFalse();
    }

    @Test
    void testIsFlowersQuantityAppropriate() {
        Integer flowerId = FLOWER_REPOSITORY.findByName("TestTulpe").getId();

        assertThat(flowersServices.isFlowersQuantityAppropriate(flowerId, 10)).isTrue();
    }

    @Test
    void testIsFlowersQuantityAppropriate_invalid() {
        Integer flowerId = FLOWER_REPOSITORY.findByName("TestTulpe").getId();

        assertThat(flowersServices.isFlowersQuantityAppropriate(flowerId, -5)).isFalse();
        assertThat(flowersServices.isFlowersQuantityAppropriate(flowerId, 70)).isFalse();
    }

    @Test
    void testAddNewFlower() {
        Flower jurginas = Flower.builder()
                .name("TestJurginas")
                .price(2.0)
                .color("TestColor")
                .amount(50)
                .build();

        flowersServices.addNewFlower(jurginas);
        assertThat(FLOWER_REPOSITORY.findAll().size()).isEqualTo(4);
    }

    @Test
    void removeFlower() {
        Integer flowerId = FLOWER_REPOSITORY.findByName("TestTulpe").getId();

        flowersServices.removeFlower(flowerId);

        assertThat(FLOWER_REPOSITORY.findAll().size()).isEqualTo(2);
    }

    @Test
    void updateFlowerAmount() {
    }

    @Test
    void restoreFlowerAmount() {
    }

    @Test
    void findFlowerInStock() {
    }

    @Test
    void reduceFlowerAmount() {
    }
}