package com.yourname.pricecomparator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yourname.pricecomparator.controller.dto.BascketDTO;
import com.yourname.pricecomparator.controller.dto.PriceAlertDTO;
import com.yourname.pricecomparator.model.PriceAlert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceComparatorBackendApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private String getBaseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void contextLoads() {
        assertThat(restTemplate).isNotNull();
    }

    @Test
    void testOptimizeBasket() {
        BascketDTO dto = new BascketDTO(LocalDate.now(), List.of("lapte zuzu", "pâine albă"));
        ResponseEntity<String> response = restTemplate.postForEntity(getBaseUrl() + "/api/bascket/optimize", dto, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testGetTopDiscounts() {
        ResponseEntity<String> response = restTemplate.postForEntity(getBaseUrl() + "/api/bascket/best?limit=3", null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testGetNewestDiscounts() {
        ResponseEntity<String> response = restTemplate.postForEntity(getBaseUrl() + "/api/bascket/new", null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testGetPriceHistoryByStore() {
        ResponseEntity<String> response = restTemplate.postForEntity(getBaseUrl() + "/api/bascket/price/store?store=lidl", null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testCreatePriceAlert() {
        PriceAlertDTO dto = new PriceAlertDTO();
        dto.setProductName("lapte zuzu");
        dto.setStore("lidl");
        dto.setTargetPrice(9.50);
        dto.setDate(LocalDate.now());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PriceAlertDTO> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(getBaseUrl() + "/api/alerts", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testManualCheckPriceAlert() {
        ResponseEntity<PriceAlert[]> response = restTemplate.getForEntity(getBaseUrl() + "/api/alerts/check", PriceAlert[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}
