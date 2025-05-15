package com.yourname.pricecomparator.adapter;

import com.yourname.pricecomparator.model.Discount;
import com.yourname.pricecomparator.model.ProductPrice;
import com.yourname.pricecomparator.port.AuditLoggerPort;
import com.yourname.pricecomparator.port.CsvDataPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvDataLoader implements CsvDataPort {

    private final AuditLoggerPort auditLoggerPort;

    public List<ProductPrice> loadPrices(Path pricesPath) throws IOException{
        List<ProductPrice> productPrices=new ArrayList<>();
        Files.list(pricesPath)
                .filter(Files::isRegularFile)
                .forEach(file ->{
                    int validLines = 0;
                    int invalidLines = 0;
                    long start = System.currentTimeMillis();
                  try{
                     String fileName = file.getFileName().toString();
                     String store = fileName.split("_")[0];
                     String dateStr = fileName.split("_")[1].replace(".csv","");
                     LocalDate date = LocalDate.parse(dateStr);
                     List<String> lines = Files.readAllLines(file);
                     List<String> content = lines.subList(1,lines.size());
                     for(String line : content)
                     {
                         try{
                             String [] parts = line.split(";");
                             if (parts.length != 8) {
                                 invalidLines++;
                                 auditLoggerPort.logToFile("Skipped (invalid format): " + line + " " + parts.length);
                                 continue;
                             }
                             ProductPrice product = ProductPrice.builder()
                                     .productName(parts[1])
                                     .productCategory(parts[2])
                                     .brand(parts[3])
                                     .packageQuantity(Double.parseDouble(parts[4]))
                                     .packageUnit(parts[5])
                                     .price(Double.parseDouble(parts[6]))
                                     .store(store)
                                     .currency(parts[7])
                                     .date(date)
                                     .build();
                             productPrices.add(product);
                             validLines++;
                         }catch (Exception ex)
                         {
                             invalidLines++;
                             auditLoggerPort.logToFile("Skipped (exception): " + line + " → " + ex.getMessage());
                         }
                     }
                      long end = System.currentTimeMillis();
                      auditLoggerPort.logToFile("Finished " + fileName + " in " + (end - start) + "ms | Valid: " + validLines + " | Invalid: " + invalidLines);
                      auditLoggerPort.logToFile("--------------------------------------------------");

                  }catch(Exception ex){
                      auditLoggerPort.logToFile(" Failed to process file " + file + ": " + ex.getMessage());
                  }
                });
        return productPrices;
    }
    public List<Discount> loadDiscounts(Path pricesPath) throws IOException{
        List<Discount> discounts=new ArrayList<>();
        Files.list(pricesPath)
                .filter(Files::isRegularFile)
                .forEach(file ->{
                    int validLines = 0;
                    int invalidLines = 0;
                    long start = System.currentTimeMillis();
                    try{
                        String fileName = file.getFileName().toString();
                        String store = fileName.split("_")[0];
                        String dateStr = fileName.split("_")[2].replace(".csv","");
                        LocalDate date = LocalDate.parse(dateStr);
                        List<String> lines = Files.readAllLines(file);
                        List<String> content = lines.subList(1,lines.size());
                        for(String line : content)
                        {
                            try{
                                String [] parts = line.split(";");
                                if (parts.length !=9) {
                                    invalidLines++;
                                    auditLoggerPort.logToFile("Skipped (invalid format): " + line + " " + parts.length);
                                    continue;
                                }
                                Discount discount = Discount.builder()
                                        .productName(parts[1])
                                        .brand(parts[2])
                                        .packageQuantity(Double.parseDouble(parts[3]))
                                        .packageUnit(parts[4])
                                        .productCategory(parts[5])
                                        .store(store)
                                        .fromDate(LocalDate.parse(parts[6]))
                                        .toDate(LocalDate.parse(parts[7]))
                                        .percentageOfDiscount(Integer.parseInt(parts[8]))
                                        .build();
                                discounts.add(discount);
                                validLines++;
                            }catch(Exception ex){
                                invalidLines++;
                                auditLoggerPort.logToFile("Skipped (exception): " + line + " → " + ex.getMessage());
                          }
                        }
                        long end = System.currentTimeMillis();
                        auditLoggerPort.logToFile("Finished " + fileName + " in " + (end - start) + "ms | Valid: " + validLines + " | Invalid: " + invalidLines);
                        auditLoggerPort.logToFile("--------------------------------------------------");
                    }catch (Exception ex)
                    {
                        auditLoggerPort.logToFile(" Failed to process file " + file + ": " + ex.getMessage());
                    }
                });
        return discounts;

    }

}
