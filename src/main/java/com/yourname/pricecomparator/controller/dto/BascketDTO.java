package com.yourname.pricecomparator.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BascketDTO {
    @NotNull
    private LocalDate date;
    @NotNull
    @NotEmpty
    private List<String> productNames;
}
