package com.yourname.pricecomparator.controller;

import com.yourname.pricecomparator.controller.dto.BascketDTO;
import com.yourname.pricecomparator.controller.dto.BasketResponseDTO;
import com.yourname.pricecomparator.controller.dto.DiscountDTO;
import com.yourname.pricecomparator.controller.dto.ProductPriceDTO;
import com.yourname.pricecomparator.port.ShoppingServicePort;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("api/bascket")
@RequiredArgsConstructor
public class ShoppingController {
    private final ShoppingServicePort shoppingServicePort;
    @PostMapping("/optimize")
    public ResponseEntity<BasketResponseDTO> optimizeBasket(@Valid @RequestBody BascketDTO bascketDTO )
    {
        BasketResponseDTO result = shoppingServicePort.optimizeBasket(bascketDTO);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/best")
    public ResponseEntity<List<DiscountDTO>> getTopDiscounts(@RequestParam(defaultValue = "5") int limit) {
        List<DiscountDTO> topDiscounts = shoppingServicePort.getTopDiscountDTOs(limit);
        return ResponseEntity.ok(topDiscounts);
    }
    @PostMapping("/new")
    public ResponseEntity<List<DiscountDTO>> getNewestDiscounts()
    {
        List<DiscountDTO> result = shoppingServicePort.getNewDiscounts();
        return ResponseEntity.ok(result);
    }
    @PostMapping("/price/store")
    public ResponseEntity<List<ProductPriceDTO>> getPriceHistoryByStore(@RequestParam String store)
    {
        List<ProductPriceDTO> result = shoppingServicePort.getPriceHistoryByStore(store);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/price/category")
    public ResponseEntity<List<ProductPriceDTO>> getPriceHistoryByCategory(@RequestParam String category)
    {
        List<ProductPriceDTO> result = shoppingServicePort.getPriceHistoryByCategory(category);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/price/brand")
    public ResponseEntity<List<ProductPriceDTO>> getPriceHistoryByBrand(@RequestParam String brand)
    {
        List<ProductPriceDTO> result = shoppingServicePort.getPriceHistoryByBrand(brand);
        return ResponseEntity.ok(result);
    }

}
