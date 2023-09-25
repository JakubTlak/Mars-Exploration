package com.codecool.marsexploration.logger.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Scanner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class FileLoggerImplTest {

    private static final String LOG_FILE_PATH = "src/test/test_file_logger.txt";
    private FileLoggerImpl fileLogger;

    @BeforeEach
    void setUp() {
        fileLogger = new FileLoggerImpl(LOG_FILE_PATH);
    }

    @Test
    void logInfo_shouldAppendMessageToFile() throws IOException {
        String message = "This is an info message.";
        fileLogger.logInfo(message);
        String logContent = readLogFileContent();
        assertTrue(logContent.contains(message), "Log file should contain the info message.");
    }

    @Test
    void logError_shouldPrintErrorMessageToStdErr() {
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
        String errorMessage = "This is an error message.";
        fileLogger.logError(errorMessage);
        System.setErr(System.err);
        String printedError = errContent.toString().trim();
        assertEquals("[ERROR] " + errorMessage, printedError,
                "Error message should be printed to standard error.");
    }

      private String readLogFileContent() throws IOException {
        StringBuilder content = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(LOG_FILE_PATH))) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        }
        return content.toString();
    }
}