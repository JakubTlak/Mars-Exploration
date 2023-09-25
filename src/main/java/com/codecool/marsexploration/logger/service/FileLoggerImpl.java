package com.codecool.marsexploration.logger.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLoggerImpl implements ConsoleLogger {
    private String filePath;

    public FileLoggerImpl(String filePath) {
        this.filePath = filePath;
    }


    @Override
    public void logInfo(String message) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = localDateTime.format(formatter);

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(formattedDate + " ------- " + message);
        } catch (IOException e) {
            System.err.println("Error writing to the log file: " + e.getMessage());
        }
    }

    @Override
    public void logError(String message) {
        System.err.println("[ERROR] " + message);
    }
}
