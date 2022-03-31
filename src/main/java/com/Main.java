package com;

import com.model.Customer;
import com.model.Flower;
import com.primaryData.PrimaryData;
import com.repository.Repository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.util.List;

import static com.repository.SQLQueries.SELECT_ALL_FLOWERS;
import static com.repository.SQLQueries.SELECT_ALL_FLOWER_BY_ID;


public class Main {

    private Repository repository;

    public static void main(String[] args) {

        new PrimaryData().loadPrimaryData();

        Main main = new Main();

        main.repository = new Repository();

        System.out.println();
        System.out.println(main.repository.findById(SELECT_ALL_FLOWER_BY_ID, Flower.class, 1));

    }

}
