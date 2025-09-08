# Order Management

Minimal Spring Boot 3 app with H2 in-memory DB.

## Build and Run

```
mvn spring-boot:run
```

## API

- POST `/orders`
  - Body:
    ```
    { "customerName": "Alice", "totalAmount": 123.45 }
    ```
  - Response: 201 Created + Order JSON

- GET `/orders/{id}`
  - Response: 200 OK + Order JSON or 404 if not found

## H2 Console

- Visit `/h2`
- JDBC URL: `jdbc:h2:mem:ordersdb`
- User: `sa`, Password: (empty)
