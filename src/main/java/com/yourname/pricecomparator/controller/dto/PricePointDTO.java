package com.yourname.pricecomparator.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PricePointDTO {
    private double price;
    private LocalDate date;
    private String store;
}
