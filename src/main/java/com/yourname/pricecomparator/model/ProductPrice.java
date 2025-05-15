package com.yourname.pricecomparator.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;
    private String productName;
    private String productCategory;
    private String brand;
    private double packageQuantity;
    private String packageUnit;
    private double price;
    private String currency;
    private String store;
    private LocalDate date;
}
