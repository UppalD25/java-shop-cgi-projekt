package org.test;

import org.databases.mysql.setup.Addon;
import org.databases.mysql.setup.DataBaseSetup;

public class test {
    public static void main(String[] args) {
        DataBaseSetup mysql = new DataBaseSetup();
        org.databases.access.setup.DataBaseSetup access = new org.databases.access.setup.DataBaseSetup();
        Addon addon = new Addon();
       /*
       mysql.createAllTables();
       access.createAllTables();
       addon.runAddon();
       */





    }
}
