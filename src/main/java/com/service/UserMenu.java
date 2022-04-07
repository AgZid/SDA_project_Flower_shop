package com.service;

import com.enumerators.OrderStatus;
import com.model.Customer;
import com.model.FlowersOrder;
import com.model.OrderedEntry;
import com.repository.CustomerRepository;
import com.repository.FlowerRepository;
import com.repository.FlowersOrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserMenu {

    Scanner scanner = new Scanner(System.in);

    MenuCheckingService checkingService = new MenuCheckingService();
    FlowerRepository flowerRepository = new FlowerRepository();
    CustomerRepository customerRepository = new CustomerRepository();
    FlowersOrderRepository flowersOrderRepository = new FlowersOrderRepository();
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

    public void createNewCustomer(String fullName) {
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        checkingService.checkEmail(email);

        System.out.println("Enter phone number:");
        String phoneNumber = scanner.nextLine();

        System.out.println("Enter billing address:");
        String billingAddress = scanner.nextLine();

        customer = new Customer
                (null, fullName, email, phoneNumber, billingAddress, null);

        customerRepository.createAndUpdate(customer);
    }

    public void showCustomerMenu() {
        System.out.println();
        System.out.println("Select an option:");
        System.out.println("1 - Show all flowers");
        System.out.println("2 - Show my flower orders");
        System.out.println("3 - Make new order");
        System.out.println("4 - Cancel order");
        System.out.println("5 - Edit contact information");
        System.out.println("0 - Exit");

        String customerSelection = scanner.nextLine();
        while (!customerSelection.equals("0")) {
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
                case "5":
//                editCustomer();
                    break;
                default:
                    System.out.println("Bey");
                    break;
            }
        }
    }

    public void makeNewOrder() {
        System.out.println("Enter delivery date (yyyy-mm-dd)");
        String deliveryDate = scanner.nextLine();

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

            orderedEntries.add(
                    new OrderedEntry(null, flowerQuantity, flowerRepository.findById(flowerId), null));
            System.out.println("Enter 0 - finish order, 1 - select more flowers");

            userSelection = scanner.nextLine();
        }

        List<FlowersOrder> orders = customer.getOrders();
        orders.add(
                new FlowersOrder(null, LocalDateTime.now(), null, deliveryAddress,
                        OrderStatus.ORDERED, orderedEntries, customer));
        customer.setOrders(orders);

        customerRepository.createAndUpdate(customer);
    }

    public void cancelOrder() {
        List<FlowersOrder> orders = customer.getOrders();

        if (orders.size() > 0) {
            Integer orderId;

            if (orders.size() > 1) {
                System.out.println();

                orders.stream().forEach(order -> System.out.println(flowersOrderRepository.findById(order.getId())));

                System.out.println("Enter flower order Id:");
                orderId = Integer.parseInt(scanner.nextLine());
            } else {
                orderId = orders.stream().findFirst().orElse(null).getId();
            }

            orderingServices.cancelOrder(orderId);
         } else System.out.println("List of orders is empty");
    }


    public void showAllFlowers() {
        flowerRepository.findAll().forEach(System.out::println);
    }

    public void showCustomerOrders() {
        customer.getOrders().forEach(System.out::println);
    }
}
