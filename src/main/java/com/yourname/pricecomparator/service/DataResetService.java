package com.yourname.pricecomparator.service;

import com.yourname.pricecomparator.port.DataResetServicePort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DataResetService implements DataResetServicePort {

    @PersistenceContext
    private EntityManager entityManager;
//reset la id-urile din baza de date
    @Transactional
    public void resetAutoIncrement() {
        entityManager.createNativeQuery("ALTER TABLE PRODUCT_PRICE ALTER COLUMN ID RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE DISCOUNT ALTER COLUMN ID RESTART WITH 1").executeUpdate();
    }
}
