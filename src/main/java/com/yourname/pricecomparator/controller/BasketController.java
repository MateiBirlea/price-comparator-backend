package com.yourname.pricecomparator.controller;

import com.yourname.pricecomparator.controller.dto.BascketDTO;
import com.yourname.pricecomparator.controller.dto.BasketResponseDTO;
import com.yourname.pricecomparator.port.BascketServicePort;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/bascket")
@RequiredArgsConstructor
public class BasketController {
    private final BascketServicePort bascketServicePort;
    @PostMapping("/optimize")
    public ResponseEntity<BasketResponseDTO> optimizeBasket(@Valid @RequestBody BascketDTO bascketDTO )
    {
        BasketResponseDTO result = bascketServicePort.optimizeBasket(bascketDTO);
        return ResponseEntity.ok(result);
    }
}
