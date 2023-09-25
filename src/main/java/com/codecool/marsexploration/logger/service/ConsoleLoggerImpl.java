package com.codecool.marsexploration.logger.service;

public class ConsoleLoggerImpl implements ConsoleLogger{
    @Override
    public void logInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    @Override
    public void logError(String message) {
        System.err.println("[ERROR] " + message);
    }
}
