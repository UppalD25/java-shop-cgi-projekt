package org.test;

import org.mysql.mySqlEntityToDB.DataBaseSetup;

public class test {
    public static void main(String[] args) {
        org.mysql.mySqlEntityToDB.DataBaseSetup mysql = new DataBaseSetup();
        org.access.accessEntityToDB.DataBaseSetup  access = new org.access.accessEntityToDB.DataBaseSetup();

        mysql.createAllTables();
        access.createAllTables();
    }
}
