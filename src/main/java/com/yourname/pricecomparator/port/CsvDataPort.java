package com.yourname.pricecomparator.port;

import com.yourname.pricecomparator.model.Discount;
import com.yourname.pricecomparator.model.ProductPrice;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface CsvDataPort {
    public List<ProductPrice> loadPrices(Path pricesPath) throws IOException;
    public List<Discount> loadDiscounts(Path pricesPath) throws IOException;

}
