package com.yourname.pricecomparator.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPriceDTO {
    private String prductName;
    private List<PricePointDTO> historyPrice;
}
