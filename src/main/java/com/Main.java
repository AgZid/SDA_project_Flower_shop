package com;

import com.primaryData.PrimaryData;
import com.service.UserMenu;
import com.service.customExceptions.UnknownOrIncorrectInput;

public class Main {

    public static void main(String[] args) throws UnknownOrIncorrectInput {

        new PrimaryData().loadPrimaryData();

        UserMenu userMenu = new UserMenu();
        userMenu.showWelcome();

    }
}
