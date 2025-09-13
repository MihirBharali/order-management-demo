# Order Management

Spring Boot 3 Order Management Application

## Features

- Create and fetch orders with full e-commerce fields
- Payment method validation via external (or mock) payment service
- Structured logging with Log4j2 and response time tracking
- Transaction tracing using MDC (transactionId)
- H2 in-memory database for persistence
- API-level and DB-level error handling
- Enum-based API name propagation for type safety
- Masking logic for payment method in responses
- Comprehensive `.gitignore` for build, IDE, and OS files

## Architecture

![Architecture Diagram](docs/architecture.png)

```
User -> OrderController -> OrderService -> PaymentServiceClient
                                 |-> OrderDao -> OrderRepository
All layers use LogUtil for structured logging and response time
```

To view/edit the diagram, see `docs/architecture.puml` (PlantUML format).

## Build and Run

```
mvn clean install
mvn spring-boot:run
```

## API Endpoints

- POST `/orders`
  - Create a new order
  - Validates payment method via payment service
  - Logs request, response, and response time
  - Returns: 201 Created + Order JSON

- GET `/orders/{id}`
  - Fetch order by ID
  - Logs request, response, and response time
  - Returns: 200 OK + Order JSON or 404 if not found

- GET `/products?type={type}`
  - Fetch products by type
  - Integrates with inventory and pricing services
  - Logs request, response, and response time

- Mock Payment Validation
  - POST `/v1/payment/validate` (local mock)
  - Randomly returns true/false for payment method validation

## H2 Console

- Visit `/h2`
- JDBC URL: `jdbc:h2:mem:ordersdb`
- User: `sa`, Password: (empty)

## Logging

- All API and DB operations are logged using `LogUtil`
- Logs include API name (enum), message, server, and response time
- TransactionId is included for tracing
- Log format: ISO8601 timestamp + JSON object
- Example:
  ```
  2025-09-13T19:05:02.130Z {"transactionId":"tx-0802","api":"GET_ORDER_BY_ID","server":"local-server","message":"Exception in maskPaymentMethod: StringIndexOutOfBoundsException: begin 10, end 3, length 3.","responseTimeMs":1, "customerAccountId" : "test-user-1"}
  ```

## Error Handling

- Validation errors, payment failures, and DB errors are handled and logged
- Error responses include details and are logged with response time
- Masking logic for payment method will throw and log errors if length < 10
- All error scenarios are captured in logs and returned in API responses

## Development

- `.gitignore` excludes build output, IDE, OS, and log files
- Enum `ApiNames` used throughout for type safety
- See `logs.txt` for sample and error logs
