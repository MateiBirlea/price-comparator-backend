package com.yourname.pricecomparator.port;

import com.yourname.pricecomparator.controller.dto.PriceAlertDTO;
import com.yourname.pricecomparator.model.PriceAlert;
import com.yourname.pricecomparator.model.ProductPrice;

import java.util.List;

public interface PriceAlertPort {
    public List<PriceAlert> checkAlerts();
    public List<ProductPrice> test();
    public void checkAlertsAutomatically();
    public void createAlert(PriceAlertDTO dto);
}
