package com.yourname.pricecomparator.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;
    private String productName;
    private String brand;
    private double packageQuantity;
    private String packageUnit;
    private String productCategory;
    private String store;
    private LocalDate fromDate;
    private LocalDate toDate;
    private LocalDate importedDate;
    private int percentageOfDiscount;
}
