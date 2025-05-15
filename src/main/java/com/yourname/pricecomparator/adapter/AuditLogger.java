package com.yourname.pricecomparator.adapter;

import com.yourname.pricecomparator.port.AuditLoggerPort;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
@Component
public class AuditLogger implements AuditLoggerPort {
    private final Path logPath = Paths.get("logs/import-report.log");
    public void logToFile(String message) {
        try {
            Files.createDirectories(logPath.getParent());
            Files.writeString(logPath, message + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Failed to write to import log: " + e.getMessage());
        }
    }
}
