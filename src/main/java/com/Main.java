package com;

import com.primaryData.PrimaryData;
import com.service.UserMenu;
import com.service.customExceptions.IncorrectArgument;

public class Main {

    public static void main(String[] args) throws IncorrectArgument {

        new PrimaryData().loadPrimaryData();

        UserMenu userMenu = new UserMenu();
        userMenu.showWelcome();

    }
}
