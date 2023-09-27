package com.codecool.marsexploration.repositories;

import com.codecool.marsexploration.logger.service.ConsoleLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandCentersRepository {
    private final String dbFile;
    private final ConsoleLogger logger;

    private final Connector connector;

    public CommandCentersRepository(String dbFile, ConsoleLogger logger, Connector connector) {
        this.dbFile = dbFile;
        this.logger = logger;
        this.connector = connector;
    }



    public void add(int id, int waterDelivered, int mineralsDelivered) {
        String sql = "INSERT INTO command_centers(command_center_id, water_delivered, minerals_delivered) VALUES(?,?,?)";

        try (Connection conn = connector.getConnection()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.setInt(2, waterDelivered);
                pstmt.setInt(3, mineralsDelivered);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.logError(e.getMessage());
        }
    }


    public void delete(int id) {
        String sql = "DELETE FROM command_centers WHERE command_center_id = ?";
        try (Connection conn = connector.getConnection()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.logError(e.getMessage());
        }
    }

    public void deleteAll() {
        String sql = "DELETE FROM command_centers";
        try (Connection conn = connector.getConnection()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.logError(e.getMessage());
        }
    }


    public List<String> getAll() {
        String sql = "SELECT * FROM command_centers";
        List<String> commandCentersData = new ArrayList<>();
        try (Connection conn = connector.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    commandCentersData.add("command_center_id: "+
                            rs.getInt("command_center_id")+", water_delivered: "+
                            rs.getInt("water_delivered")+", minerals_delivered: "+
                            rs.getInt("minerals_delivered"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return commandCentersData;
    }
}
