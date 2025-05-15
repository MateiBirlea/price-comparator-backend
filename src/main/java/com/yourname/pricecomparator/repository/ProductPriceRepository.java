package com.yourname.pricecomparator.repository;

import com.yourname.pricecomparator.model.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice,Long> {
    Optional<ProductPrice> findTopByProductNameAndDateOrderByPriceAsc(String productName, LocalDate date);
}
