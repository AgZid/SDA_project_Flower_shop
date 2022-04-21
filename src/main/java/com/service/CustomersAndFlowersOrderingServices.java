package com.service;

import com.enumerators.OrderStatus;
import com.model.Customer;
import com.model.FlowersOrder;
import com.model.OrderedEntry;
import com.repository.CustomerRepository;
import com.repository.FlowerRepository;
import com.repository.FlowersOrderRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class CustomersAndFlowersOrderingServices {

    FlowerRepository flowerRepository = new FlowerRepository();
    FlowersOrderRepository orderRepository = new FlowersOrderRepository();
    CustomerRepository customerRepository = new CustomerRepository();
    FlowersServices flowersServices = new FlowersServices();

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

    public boolean isValidOrderId(Integer orderId) {
        return orderRepository.findById(orderId) != null;
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
        flowersOrder.getOrderedEntries().forEach(orderedEntry ->flowersServices.restoreFlowerAmount(orderedEntry));

        flowersOrder.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.createOrUpdate(flowersOrder);
    }

    public OrderedEntry createNewOrderEntry(Integer quantityOfFlowers, Integer flowerId) {
        OrderedEntry newOrderEntity = OrderedEntry.builder()
                .quantity(quantityOfFlowers)
                .flower(flowerRepository.findById(flowerId))
                .build();
        flowersServices.reduceFlowerAmount(newOrderEntity);
        return newOrderEntity;
    }
}
