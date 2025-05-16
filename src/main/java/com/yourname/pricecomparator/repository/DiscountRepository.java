package com.yourname.pricecomparator.repository;

import com.yourname.pricecomparator.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount,Long> {
    List<Discount> findAllByOrderByPercentageOfDiscountDesc(Pageable pageable);
}
