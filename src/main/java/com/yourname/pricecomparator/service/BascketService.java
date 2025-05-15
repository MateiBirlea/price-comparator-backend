package com.yourname.pricecomparator.service;

import com.yourname.pricecomparator.controller.dto.BascketDTO;
import com.yourname.pricecomparator.controller.dto.BasketResponseDTO;
import com.yourname.pricecomparator.controller.dto.ProductItemDTO;
import com.yourname.pricecomparator.controller.dto.StoreGroupDTO;
import com.yourname.pricecomparator.model.ProductPrice;
import com.yourname.pricecomparator.port.BascketServicePort;
import com.yourname.pricecomparator.repository.ProductPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BascketService implements BascketServicePort {
    private final ProductPriceRepository productPriceRepository;
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

}
