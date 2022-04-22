package com.repository;

import com.model.Customer;
import com.service.customExceptions.IncorrectArgument;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerRepositoryTest {

    private static final CustomerRepository CUSTOMER_REPOSITORY = new CustomerRepository();

    @BeforeEach
    public void SetUpFlowers() throws IncorrectArgument {

        Customer johnTest = Customer.builder()
                .fullName("John Test")
                .phoneNumber("TestPhoneNumber1")
                .email("TestEmail1")
                .build();

        Customer tomTest = Customer.builder()
                .fullName("Tom Test")
                .phoneNumber("TestPhoneNumber2")
                .email("TestEmail2")
                .build();

        CUSTOMER_REPOSITORY.createOrUpdate(johnTest);
        CUSTOMER_REPOSITORY.createOrUpdate(tomTest);
    }

    @AfterEach
    public void deleteDataBase() {
        CUSTOMER_REPOSITORY.deleteAll();
    }

    @Test
    void testCreateAndUpdate_update() throws IncorrectArgument {
        Customer testCustomer = CUSTOMER_REPOSITORY.findByFullName("Tom Test");
        testCustomer.setEmail("New email address");

        CUSTOMER_REPOSITORY.createOrUpdate(testCustomer);

        assertThat(CUSTOMER_REPOSITORY.findById(testCustomer.getId()).getEmail())
                .isEqualTo("New email address");
    }

    @Test
    void testCreateAndUpdate_create() throws IncorrectArgument {
        Customer testTest = Customer.builder()
                .fullName("Test Test")
                .phoneNumber("TestPhone")
                .email("TestEmail")
                .build();

        CUSTOMER_REPOSITORY.createOrUpdate(testTest);

        assertThat(CUSTOMER_REPOSITORY.findAll().size()).isEqualTo(3);
    }

    @Test
    void testFindAll() {
        assertThat(CUSTOMER_REPOSITORY.findAll().size()).isEqualTo(2);
    }

    @Test
    void testFindById() {
        Integer id = CUSTOMER_REPOSITORY.findByFullName("Tom Test").getId();

        assertThat(CUSTOMER_REPOSITORY.findById(id).getFullName()).isEqualTo("Tom Test");
    }

    @Test
    void testFindByFullName() {
        Customer customerTest = CUSTOMER_REPOSITORY.findAll().stream().findFirst().orElse(null);
        String customerTestName = customerTest.getFullName();

        assertThat(CUSTOMER_REPOSITORY.findById(customerTest.getId()).getFullName()).isEqualTo(customerTestName);
    }

    @Test
    void testDeleteRecord() {
        CUSTOMER_REPOSITORY.deleteRecord(CUSTOMER_REPOSITORY.findByFullName("John Test"));

        assertThat(CUSTOMER_REPOSITORY.findAll().size()).isEqualTo(1);
        assertThat(CUSTOMER_REPOSITORY.findByFullName("Tom Test")).isNotNull();
    }

    @Test
    void testDeleteAll() {
        CUSTOMER_REPOSITORY.deleteAll();

        assertThat(CUSTOMER_REPOSITORY.findAll().size()).isEqualTo(0);
    }

}