package com.yourname.pricecomparator.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreGroupDTO {
    private String storeName;
    private List<ProductItemDTO> products;
    private double storeTotal;
}
