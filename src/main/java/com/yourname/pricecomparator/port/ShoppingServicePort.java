package com.yourname.pricecomparator.port;

import com.yourname.pricecomparator.controller.dto.BascketDTO;
import com.yourname.pricecomparator.controller.dto.BasketResponseDTO;
import com.yourname.pricecomparator.controller.dto.DiscountDTO;
import com.yourname.pricecomparator.model.Discount;

import java.util.List;

public interface ShoppingServicePort {
    public BasketResponseDTO optimizeBasket(BascketDTO bascketDTO);
    public List<DiscountDTO> getTopDiscountDTOs(int limit);
    public List<DiscountDTO> getNewDiscounts();
}
