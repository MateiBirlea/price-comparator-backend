package com.yourname.pricecomparator.port;

import com.yourname.pricecomparator.controller.dto.*;
import com.yourname.pricecomparator.model.Discount;

import java.time.LocalDate;
import java.util.List;

public interface ShoppingServicePort {
    public BasketResponseDTO optimizeBasket(BascketDTO bascketDTO);
    public List<DiscountDTO> getTopDiscountDTOs(int limit);
    public List<DiscountDTO> getNewDiscounts();
    public List<ProductPriceDTO> getPriceHistoryByStore(String store);
    public List<ProductPriceDTO> getPriceHistoryByCategory(String category);
    public List<ProductPriceDTO> getPriceHistoryByBrand(String brand);
    public List <BestDealDTO> getBestPricesByDate(LocalDate date);
}
