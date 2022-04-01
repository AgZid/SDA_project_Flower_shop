package com.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.model.Flower;

import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RepositoryTest {

    private static Repository repository = new Repository();

    @Test
    @Order(1)
    public void testCreateOrUpdateRecord_create() {
        Flower roze = Flower.builder()
                .name("TestRoze")
                .price(5.5)
                .intakeDate("TestDate1")
                .expires(("TestaDate2"))
                .color("TestColor")
                .length(50.0)
                .amount(40)
                .build();
        repository.createOrUpdateRecord(roze);

        Flower tulpe = Flower.builder()
                .name("TestTulpe")
                .price(2.0)
                .intakeDate("TestDate1")
                .expires(("TestaDate2"))
                .color("TestColor")
                .length(20.0)
                .amount(50)
                .build();
        repository.createOrUpdateRecord(tulpe);

        assertThat(roze.getFlowerId()).isEqualTo(1);
        assertThat(tulpe.getFlowerId()).isEqualTo(2);
    }

    @Test
    @Order(2)
    public void testCreateOrUpdateRecord_update() {
        int newAmount = 100;
        Flower tulpe = repository.findByName(SQLQueries.SELECT_BY_NAME, Flower.class, "TestTulpe", "Flower");

        tulpe.setAmount(newAmount);
        repository.createOrUpdateRecord(tulpe);

        Flower updatedTulpe = repository
                .findByName(SQLQueries.SELECT_BY_NAME, Flower.class, "TestTulpe", "Flower");

        assertThat(updatedTulpe.getFlowerId()).isEqualTo(2);
        assertThat(updatedTulpe.getAmount()).isEqualTo(100);
    }


    @Test
    @Order(3)
    void findAll() {
        List<Flower> flowers = repository.findAll(SQLQueries.SELECT_ALL, Flower.class, "Flower");

        assertThat(flowers.size()).isEqualTo(2);
        assertThat(flowers.stream()
                .findFirst()
                .map(Flower::getName)
                .orElse(null))
                .isEqualTo("TestRoze");
    }

    @Test
    @Order(4)
    void findById() {
        Integer flowerId = 1;
        String expectedName = "TestRoze";

        Flower flower = repository.findById(SQLQueries.SELECT_BY_ID, Flower.class, flowerId, "Flower");

        assertThat(flower.getName()).isEqualTo(expectedName);
    }

    @Test
    @Order(5)
    void testFindByName() {
        String flowerName = "TestRoze";
        Integer expectedId = 1;

        Flower flower = repository.findByName(SQLQueries.SELECT_BY_NAME, Flower.class, "TestRoze", "Flower");

        assertThat(flower.getFlowerId()).isEqualTo(expectedId);
    }

    @Test
    @Order(6)
    void testRemoveRecord() {
        Flower flower = repository.findByName(SQLQueries.SELECT_BY_NAME, Flower.class, "TestRoze", "Flower");

        repository.removeRecord(flower);

        assertThat(repository.findAll(SQLQueries.SELECT_ALL, Flower.class, "Flower").size()).isEqualTo(1);
        assertThat(repository.findByName(SQLQueries.SELECT_BY_NAME, Flower.class, "TestTulpe", "Flower"))
                .isNotNull();
    }

}