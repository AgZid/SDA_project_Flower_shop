package com.repository;

import com.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerRepositoryTest {

    private static final CustomerRepository CUSTOMER_REPOSITORY = new CustomerRepository();

    @BeforeEach
    public void SetUpFlowers() {

        Customer johnTest = Customer.builder()
                .fullName("John Test")
                .phoneNumber("TestPhoneNumber1")
                .billingAddress("TestAddress1")
                .email("TestEmail1")
                .build();

        Customer tomTest = Customer.builder()
                .fullName("Tom Test")
                .phoneNumber("TestPhoneNumber2")
                .billingAddress("TestAddress2")
                .email("TestEmail2")
                .build();

        CUSTOMER_REPOSITORY.createAndUpdate(johnTest);
        CUSTOMER_REPOSITORY.createAndUpdate(tomTest);
    }

    @AfterEach
    public void deleteDataBase() {
        CUSTOMER_REPOSITORY.deleteAll();
    }

    @Test
    void createAndUpdate_update() {
        Customer testCustomer = CUSTOMER_REPOSITORY.findByFullName("Tom Test");
        testCustomer.setBillingAddress("New Billing Address");

        CUSTOMER_REPOSITORY.createAndUpdate(testCustomer);

        assertThat(CUSTOMER_REPOSITORY.findById(testCustomer.getId()).getBillingAddress())
                .isEqualTo("New Billing Address");
    }

    @Test
    void createAndUpdate_create() {
        Customer testTest = Customer.builder()
                .fullName("Test Test")
                .phoneNumber("TestPhone")
                .billingAddress("TestAddress")
                .email("TestEmail")
                .build();

        CUSTOMER_REPOSITORY.createAndUpdate(testTest);

        assertThat(CUSTOMER_REPOSITORY.findAll().size()).isEqualTo(3);
    }

    @Test
    void findAll() {
        assertThat(CUSTOMER_REPOSITORY.findAll().size()).isEqualTo(2);
    }

    @Test
    void findById() {
        Integer id = CUSTOMER_REPOSITORY.findByFullName("Tom Test").getId();

        assertThat(CUSTOMER_REPOSITORY.findById(id).getFullName()).isEqualTo("Tom Test");
    }

    @Test
    void findByFullName() {
        Customer customerTest = CUSTOMER_REPOSITORY.findAll().stream().findFirst().orElse(null);
        String customerTestName = customerTest.getFullName();

        assertThat(CUSTOMER_REPOSITORY.findById(customerTest.getId()).getFullName()).isEqualTo(customerTestName);
    }

    @Test
    void deleteRecord() {
        CUSTOMER_REPOSITORY.deleteRecord(CUSTOMER_REPOSITORY.findByFullName("John Test"));

        assertThat(CUSTOMER_REPOSITORY.findAll().size()).isEqualTo(1);
        assertThat(CUSTOMER_REPOSITORY.findByFullName("Tom Test")).isNotNull();
    }

    @Test
    void deleteAll() {
        CUSTOMER_REPOSITORY.deleteAll();

        assertThat(CUSTOMER_REPOSITORY.findAll().size()).isEqualTo(0);
    }

}