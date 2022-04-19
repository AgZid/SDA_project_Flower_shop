package com.service;

import com.enumerators.OrderStatus;
import com.model.Customer;
import com.model.Flower;
import com.model.FlowersOrder;
import com.model.OrderedEntry;
import com.repository.CustomerRepository;
import com.repository.FlowerRepository;
import com.repository.FlowersOrderRepository;

import java.util.List;

public class FlowersServices {

    FlowerRepository flowerRepository = new FlowerRepository();
    CustomerRepository customerRepository = new CustomerRepository();
    FlowersOrderRepository orderRepository = new FlowersOrderRepository();
    CustomersAndFlowersOrderingServices orderingServices = new CustomersAndFlowersOrderingServices();

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
        if (foundCustomerByName != null) {
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

    public List<FlowersOrder> retrieveCustomerOrders(String customerFullName) {
        Customer customer = findCustomerByName(customerFullName);
        return orderRepository.findByForeignKey("customer", customer.getId());
    }

    public void showCustomerOrders(String customerFullName) {
        retrieveCustomerOrders(customerFullName).forEach(System.out::println);
    }

    public void addNewOrder(String customerFullName, FlowersOrder newOrder) {
        Customer customer = findCustomerByName(customerFullName);
        newOrder.setCustomer(customer);
        List<OrderedEntry> orderedEntries = newOrder.getOrderedEntries();
        orderedEntries.forEach(orderedEntry -> orderedEntry.setFlowersOrder(newOrder));
        customer.addOrder(newOrder);
        customerRepository.createOrUpdate(customer);
    }

    public void cancelOrder(Integer orderId) {
        FlowersOrder flowersOrder = orderRepository.findById(orderId);
        flowersOrder.getOrderedEntries().forEach(orderedEntry -> restoreFlowerAmount(orderedEntry));

        flowersOrder.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.createOrUpdate(flowersOrder);
    }

    public void restoreFlowerAmount(OrderedEntry orderedEntry) {
        Flower flowerToRestoreAmount = flowerRepository.findById(orderedEntry.getFlower().getId());
        flowerToRestoreAmount.setAmount(flowerToRestoreAmount.getAmount() + orderedEntry.getQuantity());
        flowerRepository.createOrUpdate(flowerToRestoreAmount);
    }

    public Flower findFlowerInStock(Flower newFlower) {
        return flowerRepository.findAll().stream()
                .filter(flower -> flower.getName().equalsIgnoreCase(newFlower.getName())
                        && flower.getColor().equalsIgnoreCase(newFlower.getColor())
                        && flower.getPrice().equals(newFlower.getPrice())).findFirst().orElse(null);
    }

    public OrderedEntry createNewOrderEntry(Integer quantityOfFlowers, Integer flowerId) {
        OrderedEntry newOrderEntity = OrderedEntry.builder()
                .quantity(quantityOfFlowers)
                .flower(flowerRepository.findById(flowerId))
                .build();
        orderingServices.reduceFlowerAmount(newOrderEntity);
        return newOrderEntity;
    }
}
