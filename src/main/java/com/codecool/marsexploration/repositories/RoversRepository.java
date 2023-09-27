package com.codecool.marsexploration.repositories;

import com.codecool.marsexploration.logger.service.ConsoleLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RoversRepository {
    private final String dbFile;
    private final ConsoleLogger logger;

    private final Connector connector;

    public RoversRepository(String dbFile, ConsoleLogger logger, Connector connector) {
        this.dbFile = dbFile;
        this.logger = logger;
        this.connector = connector;
    }



    public void add(int id, int waterExtracted, int mineralsExtracted) {
        String sql = "INSERT INTO rovers(rover_id, water_extracted, minerals_extracted) VALUES(?,?,?)";

        try (Connection conn = connector.getConnection()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.setInt(2, waterExtracted);
                pstmt.setInt(3, mineralsExtracted);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.logError(e.getMessage());
        }
    }


    public void delete(int id) {
        String sql = "DELETE FROM rovers WHERE rover_id = ?";
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
        String sql = "DELETE FROM rovers";
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
        String sql = "SELECT * FROM rovers";
        List<String> roversData = new ArrayList<>();
        try (Connection conn = connector.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    roversData.add("rover_id: "+
                            rs.getInt("rover_id")+", water_extracted: "+
                            rs.getInt("water_extracted")+", minerals_extracted: "+
                            rs.getInt("minerals_extracted"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return roversData;
    }
}
