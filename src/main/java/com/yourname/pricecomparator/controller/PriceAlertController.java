package com.yourname.pricecomparator.controller;


import com.yourname.pricecomparator.controller.dto.PriceAlertDTO;
import com.yourname.pricecomparator.model.PriceAlert;
import com.yourname.pricecomparator.model.ProductPrice;
import com.yourname.pricecomparator.port.PriceAlertPort;
import com.yourname.pricecomparator.service.PriceAlertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class PriceAlertController {
    private final PriceAlertPort alertService;

    @GetMapping("/check")
    public ResponseEntity<List<PriceAlert>> manualCheck() {
        List<PriceAlert> result = alertService.checkAlerts();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/test")
    public ResponseEntity<List<ProductPrice>> manualCheck1() {
        List<ProductPrice> result = alertService.test();
        return ResponseEntity.ok(result);
    }
    @PostMapping
    public ResponseEntity<String> createAlert(@Valid @RequestBody PriceAlertDTO alertDTO) {
        alertService.createAlert(alertDTO);
        return ResponseEntity.ok("Alert created successfully");
    }
}

