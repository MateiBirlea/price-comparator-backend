package com.yourname.pricecomparator.repository;

import com.yourname.pricecomparator.model.PriceAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {
    List<PriceAlert> findByTriggeredFalse();
}

