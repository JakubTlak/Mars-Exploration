package com.codecool.marsexploration.repositories;

import com.codecool.marsexploration.logger.service.ConsoleLogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private final String dbFile;
    private final ConsoleLogger logger;

    public Connector(String dbFile, ConsoleLogger logger) {
        this.dbFile = dbFile;
        this.logger = logger;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {

            String url = "jdbc:sqlite:" + dbFile;

            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}
