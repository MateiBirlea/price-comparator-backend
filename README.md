# 🛒 Price Comparator Backend

This is a Java Spring Boot backend developed as part of a software engineering internship challenge. The application helps users compare prices of everyday grocery products across multiple supermarket chains and make smart shopping decisions.

---

## ✅ Features

- 🧺 **Basket Optimization**  
  Automatically find the cheapest way to buy a user's shopping basket across all stores.

- 🔝 **Top Discounts**  
  List products with the highest current discount percentages.

- 🆕 **New Discounts**  
  Show discounts added in the last 24 hours.

- 📈 **Price History Visualization**  
  Provide time-series data (price vs. date) to support frontend graphing, filterable by brand, category, or store.

- ⚖️ **Product Substitutes**  
  Identify the best "price per unit" options (e.g., per liter or kg) to suggest value purchases.

- 🔔 **Custom Price Alerts**  
  Users can set target prices and receive alerts when products fall below those prices (checked via `@Scheduled` or manual endpoint).

---

## 🧱 Architecture

This project follows **Hexagonal Architecture (Ports and Adapters)**:

- **Core Domain**
    - `model/` – Business entities (e.g., `ProductPrice`, `Discount`, `PriceAlert`)
    - `service/` – Application logic
    - `port/` – Interfaces between domain and outside layers

- **Adapters**
    - `controller/` – Exposes HTTP endpoints
    - `repository/` – Database persistence via Spring Data JPA
    - `adapter/` – File imports (CSV), logging, and other external integrations

This approach ensures separation of concerns, modularity, and testability.

---

## 🔧 Tech Stack

- Java 17
- Spring Boot 3
- Spring Data JPA (with in-memory H2 database)
- Gradle
- CSV File Parsing
- Scheduled Tasks (`@Scheduled`)
-  Swagger/OpenAPI

---

## 📁 API Endpoints

| Method | Endpoint                          | Description                                |
|--------|-----------------------------------|--------------------------------------------|
| POST   | `/api/bascket/optimize`           | Optimize shopping basket for lowest cost   |
| GET    | `/api/bascket/best`               | Top N discounts by percentage              |
| GET    | `/api/bascket/new`                | Discounts added in the last 24 hours       |
| GET    | `/api/bascket/price/store`        | Price history filtered by store            |
| GET    | `/api/bascket/price/category`     | Price history filtered by category         |
| GET    | `/api/bascket/price/brand`        | Price history filtered by brand            |
| GET    | `/api/bascket/best/price`         | Best unit price by product and store       |
| POST   | `/api/alerts`                     | Create new price alert                     |
| GET    | `/api/alerts/check`               | Check alerts manually                      |

---

## ▶️ Running the Tests

```bash
./gradlew test 