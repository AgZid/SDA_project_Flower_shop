package com.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.model.Flower;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FlowerRepositoryTest {

    private static final FlowerRepository FLOWER_REPOSITORY = new FlowerRepository();

    @BeforeEach
    public void SetUpFlowers() {
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

        FLOWER_REPOSITORY.createAndUpdate(roze);
        FLOWER_REPOSITORY.createAndUpdate(tulpe);
        FLOWER_REPOSITORY.createAndUpdate(gvazdikas);
    }

    @AfterEach
    public void deleteDataBase() {
        FLOWER_REPOSITORY.deleteAll();
    }

    @Test
    public void testCreateOrUpdateFlower_create() {
        Flower bijunas = Flower.builder()
                .name("TestBijunas")
                .price(4.0)
                .color("TestPurple")
                .amount(60)
                .build();
        FLOWER_REPOSITORY.createAndUpdate(bijunas);

        assertThat(FLOWER_REPOSITORY.findAll().size()).isEqualTo(4);
        assertThat(FLOWER_REPOSITORY.findByName("TestBijunas")).isNotNull();
    }

    @Test
    public void testCreateOrUpdateFlower_update() {
        Double newPrice = 30.0;
        Flower gele = FLOWER_REPOSITORY.findByName("TestTulpe");

        gele.setPrice(newPrice);
        FLOWER_REPOSITORY.createAndUpdate(gele);

        Flower updatedGele = FLOWER_REPOSITORY
                .findByName("TestTulpe");

        assertThat(updatedGele.getPrice()).isEqualTo(30.0);
    }

    @Test
    void findAll() {
        assertThat(FLOWER_REPOSITORY.findAll().size()).isEqualTo(3);
    }

    @Test
    void findFlowerById() {
        Integer flowerId = FLOWER_REPOSITORY.findByName("TestTulpe").getId();
        String expectedName = "TestTulpe";

        Flower flower = FLOWER_REPOSITORY.findById(flowerId);

        assertThat(flower.getName()).isEqualTo(expectedName);
    }

    @Test
    void findFlowerByName() {
        Flower testFlower = FLOWER_REPOSITORY.findAll().stream().findFirst().orElse(null);

        Integer testId = FLOWER_REPOSITORY.findByName(testFlower.getName()).getId();

        assertThat(testFlower.getId()).isEqualTo(testId);
    }

    @Test
    void deleteRecord() {
        FLOWER_REPOSITORY.deleteRecord(FLOWER_REPOSITORY.findByName("TestGvazdikas"));

        assertThat(FLOWER_REPOSITORY.findAll().size()).isEqualTo(2);
        assertThat(FLOWER_REPOSITORY.findByName("TestTulpe")).isNotNull();
//        assertThat(FLOWER_REPOSITORY.findByName("TestGvazdikas")).isNull();
    }


}