package com.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.enumerators.OrderStatus;
import com.model.Customer;
import com.model.Flower;

import com.model.FlowersForOrdering;
import com.model.FlowersOrder;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RepositoryTest {

    private static final Repository REPOSITORY = new Repository();

    @BeforeEach
    public void SetUpFlowers()  {
        Flower roze = Flower.builder()
                .name("TestRoze")
                .price(5.5)
                .intakeDate("TestDate1")
                .expires(("TestaDate2"))
                .color("RozeColor")
                .length(50.0)
                .amount(40)
                .build();

        Flower tulpe = Flower.builder()
                .name("TestTulpe")
                .price(2.0)
                .intakeDate("TestDate1")
                .expires(("TestaDate2"))
                .color("TestColor")
                .length(20.0)
                .amount(50)
                .build();

        Flower gvazdikas = Flower.builder()
                .name("TestGvazdikas")
                .price(2.0)
                .intakeDate("TestDate3")
                .expires(("TestaDate5"))
                .color("TestColor")
                .amount(60)
                .build();

        REPOSITORY.createOrUpdateRecord(roze);
        REPOSITORY.createOrUpdateRecord(tulpe);
        REPOSITORY.createOrUpdateRecord(gvazdikas);

        Customer john = Customer.builder()
                .name("John Test")
                .phoneNumber("TestPhoneNumber")
                .billingAddress("TestAddress")
                .email("TestEmail")
                .build();

        FlowersOrder johnFlowersOrder = FlowersOrder.builder()
                .orderStatus(OrderStatus.ORDERED)
                .orderDate(LocalDate.of(2022, 3,5))
                .deliveryDay(LocalDate.of(2022,5, 8))
                .customer(john)
                .build();

        FlowersForOrdering johnFlowersForOrdering = FlowersForOrdering.builder()
                .flower(roze)
                .quantity(20)
                .flowersOrder(johnFlowersOrder)
                .build();

        johnFlowersOrder.setFlowersForOrderings(List.of(johnFlowersForOrdering));
        john.setOrders(List.of(johnFlowersOrder));

        REPOSITORY.createOrUpdateRecord(john);
    }

    @AfterEach
    public void truncateDataBase() {
        REPOSITORY.deleteRecordsFromTable("FlowersForOrdering");
        REPOSITORY.deleteRecordsFromTable("Flower");
        REPOSITORY.deleteRecordsFromTable("FlowersOrder");
        REPOSITORY.deleteRecordsFromTable("Customer");
    }

    @Test
    public void testCreateOrUpdateFlower_create() {
        Flower bijunas = Flower.builder()
                .name("TestBijunas")
                .price(4.0)
                .intakeDate("TestDate3")
                .expires(("TestaDate4"))
                .color("TestPurple")
                .amount(60)
                .build();
        REPOSITORY.createOrUpdateRecord(bijunas);

        assertThat(REPOSITORY.findAll(Flower.class, "Flower").size()).isEqualTo(4);
        assertThat(REPOSITORY.findByName(Flower.class, "TestBijunas", "Flower")).isNotNull();
    }


    @Test
    public void testCreateOrUpdateFlower_update() {
        int newAmount = 100;
        Flower gele = REPOSITORY.findByName(Flower.class, "TestTulpe", "Flower");

        gele.setAmount(newAmount);
        REPOSITORY.createOrUpdateRecord(gele);

        Flower updatedGele = REPOSITORY
                .findByName(Flower.class, "TestTulpe", "Flower");

        assertThat(updatedGele.getAmount()).isEqualTo(100);
    }


    @Test
    void findAllFlowers() {
        findAll(Flower.class, "Flower", 3);
    }

    @Test
    void findAllCustomers() {
        findAll(Customer.class, "Customer", 1);
    }

    @Test
    void findAllFlowersOrders() {
        findAll(FlowersOrder.class, "FlowersOrder", 1);
    }

    @Test
    void findAllFlowersForOrdering() {
        findAll(FlowersForOrdering.class, "FlowersForOrdering", 1);
    }

    @Test
    void findFlowerById() {
        Integer flowerId = REPOSITORY.findByName(Flower.class, "TestTulpe", "Flower").getId();
        String expectedName = "TestTulpe";

        Flower flower = REPOSITORY.findById(Flower.class, flowerId, "Flower");

        assertThat(flower.getName()).isEqualTo(expectedName);
    }

    @Test
    void findCustomerById() {
        Integer customerId = REPOSITORY.findByName(Customer.class, "John Test", "Customer").getId();
        String expectedName = "John Test";

        Customer realCustomer = REPOSITORY.findById(Customer.class, customerId, "Customer");

        assertThat(realCustomer.getName()).isEqualTo(expectedName);
    }

//    @Test
//    void findFlowersOrderById() {
//        Integer customerId = REPOSITORY.findByName(Customer.class, "John Test", "Customer").getId();
//        List<Integer> orderIds = REPOSITORY
//                .findBYForeignKey(FlowersOrder.class, "FlowersOrder", "customer", customerId)
//                .stream()
//                .map(flowersOrder -> flowersOrder.getId())
//                .collect(Collectors.toList());
//
//        FlowersOrder actualFlowerOrder = REPOSITORY.findById(FlowersOrder.class, orderIds.indexOf(0), "FlowersOrder");
//
//        assertThat(actualFlowerOrder.).isEqualTo(expectedName);
//    }


    @Test
    void testFindByName() {
        String flowerName = "TestRoze";
        Double expectedPrice = 5.5;
        String expectedColor = "RozeColor";

        Flower flower = REPOSITORY.findByName(Flower.class, flowerName, "Flower");

        assertThat(flower.getPrice()).isEqualTo(expectedPrice);
        assertThat(flower.getColor()).isEqualTo(expectedColor);
    }

    @Test
    void testRemoveRecordFromFlower() {
        Flower flower = REPOSITORY.findByName(Flower.class, "TestGvazdikas", "Flower");

        REPOSITORY.removeRecord(flower);

        assertThat(REPOSITORY.findAll(Flower.class, "Flower").size()).isEqualTo(2);
        assertThat(REPOSITORY.findByName(Flower.class, "TestTulpe", "Flower"))
                .isNotNull();
    }

    <T> void findAll(Class<T> queryClass, String tableName, Integer expectedSize) {
        List<T> flowers = REPOSITORY.findAll(queryClass, tableName);

        assertThat(flowers.size()).isEqualTo(expectedSize);
    }


}