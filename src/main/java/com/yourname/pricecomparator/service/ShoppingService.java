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

import java.time.LocalDate;
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
                                p->new PricePointDTO(p.getPrice(),p.getDate(),p.getStore()),
                                        Collectors.toList()
                        )
                ));
        List<ProductPriceDTO> result = grouped.entrySet().stream()
                .map(entry ->new ProductPriceDTO(entry.getKey(),entry.getValue()))
                .toList();
        return result;
    }
    public List<ProductPriceDTO> getPriceHistoryByCategory(String category)
    {
        List<ProductPrice> allPrices = productPriceRepository.findByProductCategory(category);
        Map<String,List<PricePointDTO>> grouped = allPrices.stream()
                .collect(
                        Collectors.groupingBy(
                                ProductPrice::getProductName,
                                Collectors.mapping(
                                        p-> new PricePointDTO(p.getPrice(),p.getDate(),p.getStore()),
                                        Collectors.toList()
                                )
                        )
                );
        List<ProductPriceDTO> result = grouped.entrySet().stream()
                .map(entry -> new ProductPriceDTO(entry.getKey(),entry.getValue()))
                .toList();
        return result;
    }
    public List<ProductPriceDTO> getPriceHistoryByBrand(String brand)
    {
        List<ProductPrice> allPrices = productPriceRepository.findByBrand(brand);
        Map<String,List<PricePointDTO>> grouped = allPrices.stream()
                .collect(
                        Collectors.groupingBy(
                                ProductPrice::getProductName,
                                Collectors.mapping(
                                        p-> new PricePointDTO(p.getPrice(),p.getDate(),p.getStore()),
                                        Collectors.toList()
                                )
                        )
                );
        List<ProductPriceDTO> result = grouped.entrySet().stream()
                .map(entry-> new ProductPriceDTO(entry.getKey(),entry.getValue()))
                .toList();
        return result;
    }
    public List<BestDealDTO> getBestPricesByDate(LocalDate date) {
        List<ProductPrice> productPrices = productPriceRepository.findByDate(date);

        Map<String, List<ProductDealDTO>> grouped = productPrices.stream()
                .collect(
                        Collectors.groupingBy(
                                ProductPrice::getProductName,
                                Collectors.mapping(
                                        p -> {
                                            double price = p.getPrice() * p.getPackageQuantity();
                                            return new ProductDealDTO(p.getPackageQuantity(), p.getPrice(), price, p.getStore());
                                        },
                                        Collectors.toList()
                                )
                        )
                );

        // Pentru fiecare grup de produse (pe numele produsului)
        List<BestDealDTO> result = grouped.entrySet().stream()
                .map(entry -> {
                    // Selectează produsul cu cel mai mic preț
                    ProductDealDTO bestDeal = entry.getValue().stream()
                            .min(Comparator.comparingDouble(ProductDealDTO::getTotal))  // Compară prețurile totale
                            .orElseThrow(() -> new RuntimeException("No products found for " + entry.getKey()));

                    // Crează BestDealDTO cu prețul minim și magazinul corespunzător
                    return new BestDealDTO(
                            entry.getKey(),  // Numele produsului
                            bestDeal.getTotal(),  // Prețul cel mai mic
                            bestDeal.getStore(),  // Magazinul cu prețul cel mai mic
                            entry.getValue()  // Lista completă de produse
                    );
                })
                .collect(Collectors.toList());

        return result;
    }



}
