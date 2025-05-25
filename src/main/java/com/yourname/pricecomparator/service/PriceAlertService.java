package com.yourname.pricecomparator.service;

import com.yourname.pricecomparator.controller.dto.PriceAlertDTO;
import com.yourname.pricecomparator.model.PriceAlert;
import com.yourname.pricecomparator.model.ProductPrice;
import com.yourname.pricecomparator.port.PriceAlertPort;
import com.yourname.pricecomparator.repository.PriceAlertRepository;
import com.yourname.pricecomparator.repository.ProductPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceAlertService implements PriceAlertPort {
    private final PriceAlertRepository alertRepository;
    private final ProductPriceRepository priceRepository;
    ///verificarea alertelor
    public List<PriceAlert> checkAlerts(){
        List<PriceAlert> alerts = alertRepository.findByTriggeredFalse();

        for (PriceAlert alert : alerts) {
            List<ProductPrice> prices = priceRepository.findByDateAndProductName(alert.getDate(), alert.getProductName());

            for (ProductPrice price : prices) {
                if (price.getPrice() <= alert.getTargetPrice()) {
                    alert.setTriggered(true);
                    alert.setStore(price.getStore());
                    alert.setDate(price.getDate());
                    alertRepository.save(alert);
                    break; // ne oprim la prima potrivire
                }
            }
        }

        return alertRepository.findAll();
    }
    /// metoda de test
    public List<ProductPrice> test()
    {
        List<PriceAlert> alerts = alertRepository.findByTriggeredFalse();
        List<ProductPrice> p =priceRepository.findByDateAndProductName(alerts.get(0).getDate(),alerts.get(0).getProductName());
        return p;
    }

    @Scheduled(cron = "0 0 * * * *") // rulează o dată pe oră
    public void checkAlertsAutomatically() {
        checkAlerts();
    }
    public void createAlert(PriceAlertDTO dto) {
        PriceAlert alert = PriceAlert.builder()
                .productName(dto.getProductName())
                .targetPrice(dto.getTargetPrice())
                .date(dto.getDate())
                .triggered(false)
                .build();

        alertRepository.save(alert);
    }

}

