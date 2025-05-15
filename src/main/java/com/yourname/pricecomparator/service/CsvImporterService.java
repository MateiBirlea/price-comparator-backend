package com.yourname.pricecomparator.service;

import com.yourname.pricecomparator.model.Discount;
import com.yourname.pricecomparator.model.ProductPrice;
import com.yourname.pricecomparator.port.CsvDataPort;
import com.yourname.pricecomparator.port.CsvImporterPort;
import com.yourname.pricecomparator.repository.DiscountRepository;
import com.yourname.pricecomparator.repository.ProductPriceRepository;
import com.yourname.pricecomparator.adapter.CsvDataLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvImporterService implements CsvImporterPort {
    private final DiscountRepository discountRepository;
    private final ProductPriceRepository productPriceRepository;
    private final CsvDataPort csvDataPort;

    public void importAll() throws IOException
    {
        try{
            List<ProductPrice> productPrices = csvDataPort.loadPrices(Paths.get("src/main/resources/data/prices"));
            productPriceRepository.saveAll(productPrices);
            List<Discount> discounts = csvDataPort.loadDiscounts(Paths.get("src/main/resources/data/discounts"));
            discountRepository.saveAll(discounts);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
