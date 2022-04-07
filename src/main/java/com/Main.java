package com;

import com.primaryData.PrimaryData;
import com.service.UserMenu;

public class Main {

    public static void main(String[] args) {

        new PrimaryData().loadPrimaryData();

        UserMenu userMenu = new UserMenu();
        userMenu.showWelcome();

    }
}
