# url-shortener

A URL shortener API built with Spring Boot that generates random slugs, supports automatic expiration, and logs detailed click analytics.

---

## Description

The **URL Shortener Challenge** is a REST API that allows users to create shortened URLs with automatic expiration.  
The system generates a random alphanumeric slug (5–10 characters), stores expiration metadata, and logs click details such as IP address, User-Agent, Referer, and Accept-Language.

---

## Technologies Used

- **Java 21**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **Jakarta Validation**
- **PostgreSQL**
- **Maven**

---

## Architecture

The project follows a layered architecture:

- **Controller** – Handles HTTP requests and responses
- **Service** – Contains business rules (URL creation, expiration validation, click logging)
- **Repository** – JPA repositories for persistence
- **DTO** – Request/response data transfer objects
- **Entity** – `URL` and `URLClick` domain models
- **Exception Handler** – Centralized error handling

---

## Core Features

- Generate short random slugs (5–10 alphanumeric characters)
- Automatic expiration (default: 24 hours)
- Redirect to original URL
- Click tracking with metadata:
    - IP address
    - User-Agent
    - Referer
    - Accept-Language
- Retrieve full URL analytics

---

## Business Rules

- Each shortened URL expires after **24 hours**
- Expired URLs return an error
- If a slug does not exist, a not found error is returned
- Every redirect request logs a click entry

---

## Endpoints

### POST `/shorten-url`

Creates a shortened URL.

**Request Body:**
```json
{
  "url": "https://example.com"
}
```

**Response (201):**

```json
{
    "url": "https://xxx.com/DXB6V"
}
```

---

### GET `/{slug}`

Redirects to the original URL.

**Response:**
- `302 Found` → Redirect
- `404 Not Found` → Slug does not exist
- `410 Gone` → URL expired

---

### GET `/{slug}/info`

Returns URL details and click analytics.

**Response (200):**

```json
{
  "id": 1,
  "url": "https://example.com",
  "slug": "aB3xYz",
  "expiresAt": "2026-02-24T12:00:00Z",
  "createdAt": "2026-02-23T12:00:00Z",
  "clicks": [
    {
      "id": 10,
      "ip": "127.0.0.1",
      "userAgent": "Mozilla/5.0",
      "referer": "https://google.com",
      "acceptLanguage": "en-US",
      "clickedAt": "2026-02-23T13:00:00Z"
    }
  ]
}
```

---

## Validation

### URLRequest

- `url`
    - Required
    - Must be a valid URL format

---

## Running the Application

```bash
mvn spring-boot:run
```

Default server:

```
http://localhost:8080
```

---

## Database

Example configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/urlshortener
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

---

## Example Using cURL

```bash
curl -X POST http://localhost:8080/urls \
  -H "Content-Type: application/json" \
  -d '{"url":"https://example.com"}'
```

---

## Challenge Context

This project was developed as a backend challenge to demonstrate:

- Clean layered architecture
- Proper exception handling
- Transaction management
- REST best practices
- Basic analytics tracking

### Original Challenge

https://github.com/backend-br/desafios/blob/master/url-shortener/PROBLEM.md
