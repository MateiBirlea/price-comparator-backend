package com.yourname.pricecomparator.controller;

import com.yourname.pricecomparator.controller.dto.ProductPriceDTO;
import com.yourname.pricecomparator.port.ShoppingServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/bascket")
@RequiredArgsConstructor
public class PriceHistoryController {
    private final ShoppingServicePort shoppingServicePort;
    @GetMapping("/price/store")
    public ResponseEntity<List<ProductPriceDTO>> getPriceHistoryByStore(@RequestParam String store)
    {
        List<ProductPriceDTO> result = shoppingServicePort.getPriceHistoryByStore(store);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/price/category")
    public ResponseEntity<List<ProductPriceDTO>> getPriceHistoryByCategory(@RequestParam String category)
    {
        List<ProductPriceDTO> result = shoppingServicePort.getPriceHistoryByCategory(category);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/price/brand")
    public ResponseEntity<List<ProductPriceDTO>> getPriceHistoryByBrand(@RequestParam String brand)
    {
        List<ProductPriceDTO> result = shoppingServicePort.getPriceHistoryByBrand(brand);
        return ResponseEntity.ok(result);
    }
}
