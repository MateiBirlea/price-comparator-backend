package com.yourname.pricecomparator.config;

import com.yourname.pricecomparator.port.CsvImporterPort;
import com.yourname.pricecomparator.port.DataResetServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class StartUpRunner implements CommandLineRunner {

    private final CsvImporterPort importerService;
    private final DataResetServicePort dataResetService;
    @Override
    public void run(String... args) throws IOException {
        dataResetService.resetAutoIncrement();
        importerService.importAll();
    }
}

