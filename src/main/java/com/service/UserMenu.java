package com.service;

import com.enumerators.OrderStatus;
import com.model.Customer;
import com.model.FlowersOrder;
import com.model.OrderedEntry;
import com.repository.CustomerRepository;
import com.repository.FlowerRepository;
import com.repository.FlowersOrderRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UserMenu {

    Scanner scanner = new Scanner(System.in);

    MenuCheckingService checkingService = new MenuCheckingService();
    FlowerRepository flowerRepository = new FlowerRepository();
    CustomerRepository customerRepository = new CustomerRepository();
    FlowersOrderRepository orderRepository = new FlowersOrderRepository();
    FlowerOrderingServices orderingServices = new FlowerOrderingServices();

    Customer customer;

    public void showWelcome() {
        System.out.println("Welcome to flower shop");
        System.out.println();
        registration();
    }

    public void registration() {
        System.out.println("Please register");
        System.out.println("Enter full name:");
        String customerFullName = scanner.nextLine();

        if (!checkingService.isUserFlowerShopCustomer(customerFullName)) {
            createNewCustomer(customerFullName);
        } else {
            customer = customerRepository.findByFullName(customerFullName);
        }

        showCustomerMenu();
    }

    public void showCustomerMenu() {

        String customerSelection = "1";

        while (!customerSelection.equals("0")) {

            System.out.println();
            System.out.println("Select an option:");
            System.out.println("1 - Show all flowers");
            System.out.println("2 - Show my flower orders");
            System.out.println("3 - Make new order");
            System.out.println("4 - Cancel order");
            System.out.println("0 - Exit");

            customerSelection = scanner.nextLine();

            switch (customerSelection) {
                case "1":
                    showAllFlowers();
                    break;
                case "2":
                    showCustomerOrders();
                    break;
                case "3":
                    makeNewOrder();
                    break;
                case "4":
                    cancelOrder();
                    break;
                default:
                    System.out.println("We look forward to seeing you again!");
                    break;
            }

        }
    }

    public void createNewCustomer(String fullName) {
        System.out.println("Enter email:");
        boolean isEmailCorrect = false;
        String email = null;

        while (!isEmailCorrect) {
            email = scanner.nextLine();
            isEmailCorrect = checkingService.checkEmail(email);
        }

        System.out.println("Enter phone number:");
        String phoneNumber = scanner.nextLine();

        customer = new Customer
                (null, fullName, email, phoneNumber, null);

        customerRepository.createOrUpdate(customer);
    }

    public void makeNewOrder() {
        System.out.println("Enter delivery date (yyyy-mm-dd)");
        String deliveryDateInput = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deliveryDate =  LocalDate.parse(deliveryDateInput, formatter);


        System.out.println("Enter delivery address:");
        String deliveryAddress = scanner.nextLine();

        String userSelection = "1";

        List<OrderedEntry> orderedEntries = new ArrayList<>();
        while (userSelection.equals("1")) {

            System.out.println("Select flower ID from the list below:");
            showAllFlowers();
            System.out.println();

            Integer flowerId = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter quantity:");
            Integer flowerQuantity = Integer.parseInt(scanner.nextLine());

            OrderedEntry orderedEntry =
                    new OrderedEntry(null, flowerQuantity, flowerRepository.findById(flowerId), null);

            orderingServices.reduceFlowerAmount(orderedEntry);

            orderedEntries.add(orderedEntry);
            System.out.println("Enter 0 - finish order, 1 - select more flowers");

            userSelection = scanner.nextLine();
        }

        FlowersOrder newOrder = new FlowersOrder(null, LocalDateTime.now(), deliveryDate, deliveryAddress, OrderStatus.ORDERED, orderedEntries, customer);
        addFlowerOrder(newOrder);
    }

    private void addFlowerOrder( FlowersOrder flowersOrder) {

        flowersOrder.getOrderedEntries().forEach(orderedEntry -> orderedEntry.setFlowersOrder(flowersOrder));
        orderRepository.createOrUpdate(flowersOrder);
    }

    public void cancelOrder() {
        List<FlowersOrder> orders = customer.getOrders();

        if (orders.size() > 0) {
            Integer orderId;

            orderId = getOrderToBeUpdatedId(orders);

            orderingServices.cancelOrder(orderId);
        } else System.out.println("List of orders is empty");
    }

    private Integer getOrderToBeUpdatedId(List<FlowersOrder> orders) {
        Integer orderId;
        if (orders.size() > 1) {
            System.out.println();

            orders.stream().filter(order -> order.getOrderStatus() == OrderStatus.ORDERED)
                    .forEach(System.out::println);

            System.out.println("Enter flower order Id:");
            orderId = Integer.parseInt(scanner.nextLine());
        } else {
            orderId = Objects.requireNonNull(orders.stream().findFirst().orElse(null)).getId();
        }
        return orderId;
    }

    public void showAllFlowers() {
        flowerRepository.findAll().forEach(System.out::println);
    }

    public void showCustomerOrders() {
        try {
            System.out.println(orderRepository.findBYForeignKey("customer", customer.getId()));
        } catch (NullPointerException e) {
            System.out.println("No orders in a list.");
        }
    }
}
