package com.yourname.pricecomparator.service;

import com.yourname.pricecomparator.adapter.mapper.DiscountMapper;
import com.yourname.pricecomparator.controller.dto.*;
import com.yourname.pricecomparator.model.Discount;
import com.yourname.pricecomparator.model.ProductPrice;
import com.yourname.pricecomparator.port.ShoppingServicePort;
import com.yourname.pricecomparator.repository.DiscountRepository;
import com.yourname.pricecomparator.repository.ProductPriceRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ShoppingService implements ShoppingServicePort {
    private final ProductPriceRepository productPriceRepository;
    private final DiscountRepository discountRepository;
    @Override
    public BasketResponseDTO optimizeBasket(BascketDTO bascketDTO) {
        List<ProductPrice> productPrices = new ArrayList<>();

        // 1. Căutăm cel mai ieftin produs per nume + dată
        for (String name : bascketDTO.getProductNames()) {
            productPriceRepository
                    .findTopByProductNameAndDateOrderByPriceAsc(name, bascketDTO.getDate())
                    .ifPresent(productPrices::add);
        }

        // 2. Grupăm produsele după numele magazinului (store)
        Map<String, List<ProductItemDTO>> groupedByStore = new HashMap<>();
        for (ProductPrice product : productPrices) {
            groupedByStore
                    .computeIfAbsent(product.getStore(), k -> new ArrayList<>())
                    .add(new ProductItemDTO(product.getProductName(), product.getPrice()));
        }

        // 3. Creăm lista de StoreGroupDTO
        List<StoreGroupDTO> storeGroups = new ArrayList<>();
        double totalCost = 0.0;

        for (Map.Entry<String, List<ProductItemDTO>> entry : groupedByStore.entrySet()) {
            double storeTotal = entry.getValue().stream().mapToDouble(ProductItemDTO::getPrice).sum();
            totalCost += storeTotal;

            storeGroups.add(new StoreGroupDTO(entry.getKey(), entry.getValue(), storeTotal));
        }

        // 4. Returnăm DTO final
        return new BasketResponseDTO(storeGroups, totalCost);
    }

    public List<DiscountDTO> getTopDiscountDTOs(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return discountRepository.findAllByOrderByPercentageOfDiscountDesc(pageable)
                .stream()
                .map(DiscountMapper::createFrom)
                .toList();
    }
    public List<DiscountDTO> getNewDiscounts(){
        return discountRepository.findNewDiscountsAddedInLast24h()
                .stream()
                .map(DiscountMapper::createFrom)
                .toList();
    }
    public List<ProductPriceDTO> getPriceHistoryByStore(String store)
    {
        List<ProductPrice> allPrices = productPriceRepository.findByStore(store);
        Map<String,List<PricePointDTO>> grouped = allPrices.stream()
                .collect(Collectors.groupingBy(
                        ProductPrice::getProductName,
                        Collectors.mapping(
                                p->new PricePointDTO(p.getPrice(),p.getDate()),
                                        Collectors.toList()
                        )
                ));
        List<ProductPriceDTO> result = grouped.entrySet().stream()
                .map(entry ->new ProductPriceDTO(entry.getKey(),entry.getValue()))
                .toList();
        return result;
    }

}
