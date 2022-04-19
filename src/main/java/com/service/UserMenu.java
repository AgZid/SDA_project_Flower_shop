package com.service;

import com.enumerators.OrderStatus;
import com.model.Customer;
import com.model.Flower;
import com.model.FlowersOrder;
import com.model.OrderedEntry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserMenu {

    Scanner scanner = new Scanner(System.in);
    FlowersServices flowersServices = new FlowersServices();

    HashMap<String, String> menuOptions;
    MenuService menuService = new MenuService();

    public void showWelcome() {
        System.out.println();
        System.out.println("Welcome to flower application");
        System.out.println();

        manageMenu();
    }

    public void manageMenu() {
        menuOptions = menuService.assignMenuOptionsToMap();

        String userSelection = "1";

        while (!userSelection.equals("0")) {
            menuService.printMenu();

            System.out.println("Select an option from menu:");
            userSelection = scanner.nextLine();

            while (!menuService.isUserEnteredMenuOptionValid(userSelection)) {
                System.out.println("Select an option from menu:");
                userSelection = scanner.nextLine();
            }

            switch (userSelection) {
                case "1":
                    flowersServices.showAllFlowers();
                    break;
                case "2":
                    flowersServices.addNewFlower(enterNewFlowerFields());
                    break;
                case "3":
                    flowersServices.showAllFlowers();
                    flowersServices.removeFlower(receiveFlowerId());
                    break;
                case "4":
                    flowersServices.showAllFlowers();
                    updateFlowerAmount();
                    break;
                case "5":
                    flowersServices.showAllCustomers();
                    break;
                case "6":
                    flowersServices.addNewCustomer(enterCustomerFields());
                    break;
                case "7":
                    flowersServices.removeCustomerByName(enterCustomerName());
                    break;
                case "8":
                    flowersServices.showCustomerOrders(enterCustomerName());
                    break;
                case "9":
                    flowersServices.addNewOrder(enterCustomerName(), enterNewOrderFields());
                    break;
                case "10":
                    flowersServices.showCustomerOrders(enterCustomerName());
                    flowersServices.cancelOrder(enterOrderId());
                    break;
                default:
                    System.out.println("We look forward to seeing you again!");
                    break;
            }
        }
    }

    private Integer receiveFlowerId() {
        System.out.println("Enter flower id");
        String flowerId = scanner.nextLine();
        return menuService.convertStringToInteger(flowerId, "flower ID");
    }

    public Flower enterNewFlowerFields() {
        System.out.println("Enter flower name:");
        String flowerName = scanner.nextLine();

        System.out.println("Enter flower color:");
        String flowerColor = scanner.nextLine();

        System.out.println("Enter flower price:");
        String price = scanner.nextLine();

        Double flowerPrice = menuService.convertFlowerPriceValueToDouble(price);
        System.out.println("Enter flowers amount:");

        String amount = scanner.nextLine();
        Integer flowerAmount = menuService.convertStringToInteger(amount, "flower amount");

        return Flower.builder()
                .name(flowerName)
                .color(flowerColor)
                .price(flowerPrice)
                .amount(flowerAmount)
                .build();
    }

    public void updateFlowerAmount() {
        System.out.println("Enter flowers amount:");

        String amount = scanner.nextLine();
        Integer newFlowerAmount = menuService.convertStringToInteger(amount, "flower amount");

        flowersServices.updateFlowerAmount(receiveFlowerId(), newFlowerAmount);
    }

    public Customer enterCustomerFields() {
        System.out.println("Enter full name:");
        String fullName = scanner.nextLine();

        System.out.println("Enter email:");
        String email = scanner.nextLine();
        while (!menuService.checkEmail(email)) {
            System.out.println("Incorrect email address, enter correct email:");
            email = scanner.nextLine();
        }

        System.out.println("Enter phone number:");
        String phoneNumber = scanner.nextLine();

        return Customer.builder()
                .fullName(fullName)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
    }

    private String enterCustomerName() {
        System.out.println("Enter full name:");
        return scanner.nextLine();
    }

    public FlowersOrder enterNewOrderFields() {
        System.out.println("Enter delivery date (yyyy-mm-dd)");
        String deliveryDateInput = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deliveryDate = LocalDate.parse(deliveryDateInput, formatter);

        System.out.println("Enter delivery address:");
        String deliveryAddress = scanner.nextLine();

        String userSelection = "1";

        List<OrderedEntry> orderedEntries = new ArrayList<>();
        while (userSelection.equals("1")) {

            System.out.println("Select flower ID from the list below:");
            flowersServices.showAllFlowers();
            System.out.println();

            Integer flowerId = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter quantity:");
            Integer flowersQuantity = Integer.parseInt(scanner.nextLine());

            orderedEntries.add(flowersServices.createNewOrderEntry(flowersQuantity, flowerId));

            System.out.println("Enter 0 - finish order, 1 - select more flowers");
            userSelection = scanner.nextLine();
        }

        return FlowersOrder.builder()
                .deliveryDay(deliveryDate)
                .orderStatus(OrderStatus.ORDERED)
                .orderDate(LocalDateTime.now())
                .deliveryAddress(deliveryAddress)
                .orderedEntries(orderedEntries)
                .build();
    }

    public Integer enterOrderId() {
        System.out.println("Enter Order Id:");
        return  menuService.convertStringToInteger(scanner.nextLine(), "Order Id");
    }
}
