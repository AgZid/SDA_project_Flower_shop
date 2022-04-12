package com.service;

import com.model.Customer;
import com.repository.CustomerRepository;
import com.service.customExceptions.IncorrectInput;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuService {

    HashMap<String, String> menuOptions = assignMenuOptionsToMap();

    public HashMap<String, String> assignMenuOptionsToMap() {
        HashMap<String, String> menuOptions = new HashMap<>();

        menuOptions.put("1", "Show all flowers in stock");
        menuOptions.put("2", "Add new flower");
        menuOptions.put("3", "Remove a flower");
        menuOptions.put("4", "Update flower amount");
        menuOptions.put("5", "Show all customers");
        menuOptions.put("6", "Add new customer");
        menuOptions.put("7", "Remove customer by name");
        menuOptions.put("8", "Show customer's all orders");
        menuOptions.put("9", "Make new flower order for customer");
        menuOptions.put("10", "Cancel customer order");
        menuOptions.put("0", "Exit");

        return menuOptions;
    }

    public void printMenu() {
        System.out.println();
        menuOptions.forEach((key, value) -> System.out.println(key + " - " + value));
    }

    public boolean isUserEnteredMenuOptionValid(String userInput) {
        return menuOptions.containsKey(userInput);
    }

    public boolean checkEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);
        System.out.println(email + " : " + matcher.matches());
        return matcher.matches();
    }

    public boolean isUserFlowerShopCustomer(String userName) {
        CustomerRepository customerRepository = new CustomerRepository();
        Customer byFullName = customerRepository.findByFullName(userName);
        return byFullName != null;
    }

    public Double convertFlowerPriceValueToDouble(String price) {
        Double flowerPrice = 0.0;
        try {
            flowerPrice =  Double.parseDouble(price);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect price value provided:" + e.getMessage());
        }
         return flowerPrice;
    }

    public Integer convertStringToInteger(String stringValue, String enteredElement) {
        Integer integerValue = 0;
        try {
            integerValue = Integer.parseInt(stringValue);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect value provided " + enteredElement + ": " + e.getMessage());
        }
        return integerValue;
    }

}
