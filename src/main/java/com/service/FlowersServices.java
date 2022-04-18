package com.service;

import com.model.Customer;
import com.model.Flower;
import com.repository.CustomerRepository;
import com.repository.FlowerRepository;
import com.repository.FlowersOrderRepository;

public class FlowersServices {

    FlowerRepository flowerRepository = new FlowerRepository();
    CustomerRepository customerRepository = new CustomerRepository();
    FlowersOrderRepository orderRepository = new FlowersOrderRepository();
    FlowerOrderingServices orderingServices = new FlowerOrderingServices();


    public void showAllFlowers() {
        flowerRepository.findAll().forEach(System.out::println);
    }

    public void addNewFlower(Flower newFlower) {
        Flower flowerInStock = findFlowerInStock(newFlower);
        if (flowerInStock != null) {
            System.out.println("Flower is already in stock");
        } else {
            flowerRepository.createOrUpdate(newFlower);
        }
    }

    public void removeFlower(Integer flowerId) {
        flowerRepository.deleteRecord(flowerRepository.findById(flowerId));
    }

    public void updateFlowerAmount(Integer flowerId, Integer newFlowerAmount) {
        Flower flowerToUpdate = flowerRepository.findById(flowerId);
        flowerToUpdate.setAmount(newFlowerAmount);
        flowerRepository.createOrUpdate(flowerToUpdate);
    }

    public void showAllCustomers() {
        customerRepository.findAll().forEach(System.out::println);
    }

    public void addNewCustomer(Customer newCustomer) {
        Customer customerInClientList = customerRepository.findByFullName(newCustomer.getFullName());
        if (customerInClientList == null) {
            customerRepository.createOrUpdate(newCustomer);
        } else {
            System.out.println("Customer already in Customers list:");
            System.out.println(customerInClientList);
        }
    }

    public void removeCustomerByName(String customerFullName) {
        Customer foundCustomerByName = findCustomerByName(customerFullName);
        if ( foundCustomerByName != null) {
            customerRepository.deleteRecord(foundCustomerByName);
        }
    }

    public Customer findCustomerByName(String customerFullName) {
        Customer foundCustomerByName = customerRepository.findByFullName(customerFullName);
        if (foundCustomerByName == null) {
            System.out.println("Customer was not found");
        }
        return foundCustomerByName;
    }

    public void showCustomerOrders() {
    }

    public void makeNewOrder() {
    }

    public void cancelOrder() {
    }

    public Flower findFlowerInStock(Flower newFlower) {
        return flowerRepository.findAll().stream()
                .filter(flower -> flower.getName().equalsIgnoreCase(newFlower.getName())
                        && flower.getColor().equalsIgnoreCase(newFlower.getColor())
                        && flower.getPrice().equals(newFlower.getPrice())).findFirst().orElse(null);
    }
}
