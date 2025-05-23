package com.yourname.pricecomparator.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDealDTO {

    private double quantity;
    private double price;
    private double total;
    private String store;
}
