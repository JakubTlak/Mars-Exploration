package com.codecool.marsexploration.repositories;

import com.codecool.marsexploration.logger.service.ConsoleLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConstructionEventsRepository {
    private final String dbFile;
    private final ConsoleLogger logger;

    private final Connector connector;

    public ConstructionEventsRepository(String dbFile, ConsoleLogger logger, Connector connector) {
        this.dbFile = dbFile;
        this.logger = logger;
        this.connector = connector;
    }



    public void add(int eventId, int commandCenterId, int waterUsed, int mineralsUsed) {
        String sql = "INSERT INTO construction_events(event_id, command_center_id, water_used, minerals_used) VALUES(?,?,?,?)";

        try (Connection conn = connector.getConnection()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, eventId);
                pstmt.setInt(2, commandCenterId);
                pstmt.setInt(3, waterUsed);
                pstmt.setInt(4, mineralsUsed);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.logError(e.getMessage());
        }
    }


    public void delete(int id) {
        String sql = "DELETE FROM construction_events WHERE event_id = ?";
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

    public void deleteByCommandCenterId(int commandCenterId) {
        String sql = "DELETE FROM construction_events WHERE command_center_id = ?";
        try (Connection conn = connector.getConnection()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, commandCenterId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.logError(e.getMessage());
        }
    }

    public void deleteAll() {
        String sql = "DELETE FROM construction_events";
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
        String sql = "SELECT * FROM construction_events";
        List<String> constructionEventsData = new ArrayList<>();
        try (Connection conn = connector.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    constructionEventsData.add("event_id: "+ rs.getInt("event_id")
                            +", command_center_id: "+ rs.getInt("command_center_id")
                            +", water_used: "+ rs.getInt("water_used")
                            +", minerals_used: "+ rs.getInt("minerals_used"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return constructionEventsData;
    }
}
