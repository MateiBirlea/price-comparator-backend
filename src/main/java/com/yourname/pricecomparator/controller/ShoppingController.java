package com.yourname.pricecomparator.controller;

import com.yourname.pricecomparator.controller.dto.*;
import com.yourname.pricecomparator.port.ShoppingServicePort;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
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
    @GetMapping("/best")
    public ResponseEntity<List<DiscountDTO>> getTopDiscounts(@RequestParam int limit) {
        List<DiscountDTO> topDiscounts = shoppingServicePort.getTopDiscountDTOs(limit);
        return ResponseEntity.ok(topDiscounts);
    }
    @GetMapping("/new")
    public ResponseEntity<List<DiscountDTO>> getNewestDiscounts()
    {
        List<DiscountDTO> result = shoppingServicePort.getNewDiscounts();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/best/price")
    public ResponseEntity<List<BestDealDTO>> getBestDealByPriceAndQuantity(@RequestParam LocalDate date)
    {
        List<BestDealDTO> result = shoppingServicePort.getBestPricesByDate(date);
        return ResponseEntity.ok(result);
    }

}
