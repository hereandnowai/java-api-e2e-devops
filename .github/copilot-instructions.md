# Spring Boot Java API Best Practices

This document provides instructions for GitHub Copilot to ensure that all generated Java and Spring Boot code follows industry standard best practices for API development.

## 1. Project Structure & Architecture
- Follow a **Layered Architecture**:
    - `controller`: REST Endpoints.
    - `service`: Business logic and orchestration.
    - `repository`: Data access (Spring Data JPA, etc.).
    - `model/entity`: JPA Entities or database models.
    - `dto`: Data Transfer Objects for API requests and responses.
    - `exception`: Custom exceptions and global exception handler.
    - `config`: Configuration classes (Security, OpenAPI, Bean definitions).
- Use **DTOs** (Data Transfer Objects) for all external API communication. Never expose JPA Entities directly through REST endpoints.
- Use **Java Records** for DTOs when using Java 16+.

## 2. API Design
- Adhere to **RESTful principles**.
- Use plural nouns for resource naming (e.g., `/api/v1/users` instead of `/api/v1/user`).
- Use proper HTTP Method mapping:
    - `GET`: Retrieve resources.
    - `POST`: Create a new resource.
    - `PUT`: Update/Replace an existing resource.
    - `PATCH`: Partially update an existing resource.
    - `DELETE`: Remove a resource.
- Support **API Versioning** (e.g., `/api/v1/...`).
- Return appropriate **HTTP Status Codes**:
    - `200 OK`: Successful GET/PUT/PATCH.
    - `201 Created`: Successful POST.
    - `204 No Content`: Successful DELETE.
    - `400 Bad Request`: Validation errors.
    - `401 Unauthorized`: Missing or invalid authentication.
    - `403 Forbidden`: Authenticated but lacking permissions.
    - `404 Not Found`: Resource doesn't exist.
    - `500 Internal Server Error`: For unhandled exceptions.

## 3. Coding Standards
- **Constructor Injection**: Always use constructor-based dependency injection instead of `@Autowired` on fields. This makes the code more testable and immutable.
- **Modern Java**: Use modern features like `Optional`, `Streams`, `Lambda expressions`, and `var` (where it improves readability).
- **Lombok Usage**:
    - Use `@RequiredArgsConstructor` for constructor injection.
    - Use `@Getter`, `@Setter`, `@Builder`.
    - **Avoid** using `@Data` on JPA Entities to prevent issues with `hashCode` and `equals` in collections.
- **Validation**: Use `@Valid` and Bean Validation annotations (`@NotNull`, `@Size`, `@Email`, etc.) in DTOs.

## 4. Error Handling
- Implement a **Global Exception Handler** using `@RestControllerAdvice`.
- Create a consistent `ErrorResponse` DTO that includes status, message, timestamp, and field-level validation errors.
- Prefer throwing specific custom exceptions in the Service layer (e.g., `ResourceNotFoundException`).

## 5. Persistence (JPA/Hibernate)
- Use **Spring Data JPA** repositories.
- Use `fetch = FetchType.LAZY` for `To-One` relationships to avoid N+1 problems.
- Use `@Query` or Specification API for complex queries.
- Ensure proper use of `@Transactional` at the Service layer for write operations.

## 6. Testing
- Aim for high test coverage with **JUnit 5** and **Mockito**.
- Use `@WebMvcTest` for Controller unit tests.
- Use `@DataJpaTest` for Repository tests with an in-memory database like H2.
- Use `@SpringBootTest` for integration tests.
- Use **Testcontainers** for integration tests requiring real databases/services if applicable.

## 7. Documentation
- Use **SpringDoc OpenAPI** (Swagger) for automatic API documentation.
- Annotate Controllers and DTOs with `@Tag`, `@Operation`, and `@Schema` to provide clear documentation.

## 8. Logging & Observability
- Use **SLF4J** (`@Slf4j` from Lombok) for logging.
- Log meaningful information at appropriate levels (`DEBUG`, `INFO`, `WARN`, `ERROR`).
- Do not log sensitive information (PII, credentials).
- Enable **Spring Boot Actuator** for health checks and metrics.
