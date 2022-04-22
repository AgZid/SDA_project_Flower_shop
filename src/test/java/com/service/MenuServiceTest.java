package com.service;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MenuServiceTest {

    private static MenuService menuServices = new MenuService();

    @Test
    void isUserEnteredMenuOptionValid() {
        HashMap<String, String> menuOptions = new HashMap<>();
        menuOptions.put("1", "Show all flowers in stock");
        menuOptions.put("2", "Add new flower");

        String userInput = "1";

        assertThat(menuServices.isUserEnteredMenuOptionValid(userInput)).isTrue();
    }

    @Test
    void checkEmail_valid() {
        String correctEmail = "jonas@company.com";

        assertThat(menuServices.checkEmail(correctEmail)).isTrue();
    }

    @Test
    void checkEmail_invalid() {
        String incorrectEmail1 = "jonas@com";
        String incorrectEmail2 = "jonas@.com";
        String incorrectEmail3 = "jonas.company.com";

        assertThat(menuServices.checkEmail(incorrectEmail1)).isFalse();
        assertThat(menuServices.checkEmail(incorrectEmail2)).isFalse();
        assertThat(menuServices.checkEmail(incorrectEmail3)).isFalse();
    }

    @Test
    void checkPhoneNumber_valid() {
        String correctPhoneNumber1 = "867522222";
        String correctPhoneNumber2 = "+37067522222";

        assertThat(menuServices.checkPhoneNumber(correctPhoneNumber1)).isTrue();
        assertThat(menuServices.checkPhoneNumber(correctPhoneNumber2)).isTrue();
    }

    @Test
    void checkPhoneNumber_invalid() {
        String incorrectPhoneNumber1 = "8 67522";
        String incorrectPhoneNumber2 = "+370675222222";

        assertThat(menuServices.checkPhoneNumber(incorrectPhoneNumber1)).isFalse();
        assertThat(menuServices.checkPhoneNumber(incorrectPhoneNumber2)).isFalse();
    }

    @Test
    void convertStringToDouble() {
        String givenPrice = "1.2";
        Double expectedPrice = 1.2;

        assertThat(menuServices.convertStringToDouble(givenPrice,"Any element")).isEqualTo(expectedPrice);
    }

    @Test
    void convertStringToInteger() {
        String givenValue = "5";
        Integer expectedvalue = 5;

        assertThat(menuServices.convertStringToInteger(givenValue, "Any element")).isEqualTo(expectedvalue);
    }
}