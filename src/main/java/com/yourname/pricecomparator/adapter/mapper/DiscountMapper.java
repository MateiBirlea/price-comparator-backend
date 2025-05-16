package com.yourname.pricecomparator.adapter.mapper;

import com.yourname.pricecomparator.controller.dto.DiscountDTO;
import com.yourname.pricecomparator.model.Discount;

public class DiscountMapper {
    private DiscountMapper(){};
    public static DiscountDTO createFrom(Discount discount){
        return new DiscountDTO(
                discount.getProductName(),
                discount.getBrand(),
                discount.getPackageQuantity(),
                discount.getPackageUnit(),
                discount.getProductCategory(),
                discount.getStore(),
                discount.getFromDate(),
                discount.getToDate(),
                discount.getPercentageOfDiscount()
        );
    }
}
