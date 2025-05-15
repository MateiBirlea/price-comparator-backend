package com.yourname.pricecomparator.port;

import com.yourname.pricecomparator.controller.dto.BascketDTO;
import com.yourname.pricecomparator.controller.dto.BasketResponseDTO;

public interface BascketServicePort {
    public BasketResponseDTO optimizeBasket(BascketDTO bascketDTO);
}
