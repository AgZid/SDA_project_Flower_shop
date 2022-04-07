package com.service;

import com.model.Customer;
import com.repository.CustomerRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuCheckingService {

    public void checkEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);
        System.out.println(email + " : " + matcher.matches());
    }

    public boolean isUserFlowerShopCustomer(String userName) {
        CustomerRepository customerRepository = new CustomerRepository();
        Customer byFullName = customerRepository.findByFullName(userName);
        return byFullName != null;
    }


}
