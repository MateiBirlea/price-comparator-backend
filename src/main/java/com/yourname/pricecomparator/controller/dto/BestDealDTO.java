package com.yourname.pricecomparator.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BestDealDTO {
    private String name;
    private double bestDeal;
    private String storeName;
    private List<ProductDealDTO> products;
}
