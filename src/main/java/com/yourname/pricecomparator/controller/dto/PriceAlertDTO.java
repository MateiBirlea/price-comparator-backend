package com.yourname.pricecomparator.controller.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
public class PriceAlertDTO {
    @NotBlank
    private String productName;

    @NotBlank
    private String store;

    @NotNull
    private Double targetPrice;
    @NotNull
    private LocalDate date;
}
