package com.yourname.pricecomparator.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DataResetService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void resetAutoIncrement() {
        entityManager.createNativeQuery("ALTER TABLE PRODUCT_PRICE ALTER COLUMN ID RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE DISCOUNT ALTER COLUMN ID RESTART WITH 1").executeUpdate();
    }
}
