package com.yourname.pricecomparator.repository;

import com.yourname.pricecomparator.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount,Long> {
    List<Discount> findAllByOrderByPercentageOfDiscountDesc(Pageable pageable);
    @Query(value = """
    SELECT * FROM discount 
    WHERE ABS(DATEDIFF('DAY', imported_date, from_date)) < 1
""", nativeQuery = true)
    List<Discount> findNewDiscountsAddedInLast24h();

}
