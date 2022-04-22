package com.service;

import com.enumerators.OrderStatus;
import com.model.Customer;
import com.model.FlowersOrder;
import com.model.OrderedEntry;
import com.repository.CustomerRepository;
import com.repository.FlowerRepository;
import com.repository.FlowersOrderRepository;
import com.service.customExceptions.IncorrectArgument;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    public void addNewCustomer(Customer newCustomer) throws IncorrectArgument {
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
            {
                throw new IncorrectArgument("No customer " + customerFullName + ". Incorrect name was entered.");
            }
        }
        return foundCustomerByName;
    }

    public List<FlowersOrder> retrieveCustomerOrders(String customerFullName) throws IncorrectArgument {
        Customer customer = findCustomerByName(customerFullName);
        if (customer.getOrders() == null) {
            throw new IncorrectArgument("Customer have no orders.");
        }
        return orderRepository.findByForeignKey("customer", customer.getId());
    }

    public void showCustomerOrders(String customerFullName) throws IncorrectArgument {
        retrieveCustomerOrders(customerFullName).forEach(System.out::println);
    }

    public boolean isValidOrderId(Integer orderId) {
        System.out.println("Invalid id");
        return orderRepository.findById(orderId) != null;
    }

    public void addNewOrder(String customerFullName, FlowersOrder newOrder) throws IncorrectArgument {
        Customer customer = findCustomerByName(customerFullName);
        newOrder.setCustomer(customer);
        List<OrderedEntry> orderedEntries = newOrder.getOrderedEntries();
        orderedEntries.forEach(orderedEntry -> orderedEntry.setFlowersOrder(newOrder));

        if (customer.getOrders() == null) {
            List<FlowersOrder> flowersOrders = new ArrayList<>();
            flowersOrders.add(newOrder);
            customer.setOrders(flowersOrders);
        } else {
            List<FlowersOrder> flowersOrders = customer.getOrders();
            flowersOrders.add(newOrder);
        }

        customerRepository.createOrUpdate(customer);
    }

    public void cancelOrder(Integer orderId) throws IncorrectArgument {
        FlowersOrder flowersOrder = orderRepository.findById(orderId);

        if (flowersOrder.getOrderStatus().equals(OrderStatus.CANCELED)) {
            {
                throw new IncorrectArgument("Order is already cancelled");
            }
        }

        flowersOrder.getOrderedEntries().forEach(orderedEntry -> {
            try {
                flowersServices.restoreFlowerAmount(orderedEntry);
            } catch (IncorrectArgument e) {
                e.printStackTrace();
            }
        });

        flowersOrder.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.createOrUpdate(flowersOrder);
    }

    public OrderedEntry createNewOrderEntry(Integer quantityOfFlowers, Integer flowerId) throws IncorrectArgument {
        OrderedEntry newOrderEntity = OrderedEntry.builder()
                .quantity(quantityOfFlowers)
                .flower(flowerRepository.findById(flowerId))
                .build();
        flowersServices.reduceFlowerAmount(newOrderEntity);
        return newOrderEntity;
    }
}
