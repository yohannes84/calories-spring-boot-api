# Calories REST API

**A Spring Boot REST API for analyzing food consumption, pricing, and nutritional data.**

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.2.0-green)
![Gradle](https://img.shields.io/badge/Gradle-Groovy-blue)
![Testing](https://img.shields.io/badge/Testing-JUnit%20%7C%20Mockito%20%7C%20AssertJ-blue)

## Overview

Calories REST API is a Java Spring Boot application that retrieves food consumption records from a publicly hosted JSON dataset and exposes REST endpoints for analyzing food prices and nutritional information.

The application demonstrates REST API development, external HTTP integration, JSON deserialization, dependency injection, layered architecture, and automated testing.

Rather than storing data in a database, the application retrieves and processes the JSON data directly from an external URL.

## Project Requirements

The project addresses four requirements.

### 1. Retrieve and Parse JSON Data

Read and deserialize a JSON dataset from an external URL.

The dataset contains approximately **6,000 food consumption records**, including information such as:

- Food name and price
- User ID
- Calories, protein, carbohydrates, and fat
- Meal type
- Consumption date and time

**Data source:** [calories.json](https://raw.githubusercontent.com/yohannes84/calories-data/refs/heads/main/calories.json)

The application uses Spring `RestClient` for HTTP communication and Jackson for JSON deserialization.

### 2. Find Foods Priced Above $49.90

Retrieve foods whose prices are strictly greater than $49.90.

**Business requirements:**
- Filter records by price.
- Sort matching records by price in ascending order.
- Return the first three results.
- Format prices to two decimal places.

**Endpoint:**

`GET /api/calories/expensive-foods`

**Example response:**

```json
[
  {
    "name": "Massaman Curry",
    "price": "49.92"
  },
  {
    "name": "Pasta with Tomato and Basil",
    "price": "49.92"
  },
  {
    "name": "Teriyaki Chicken Donburi",
    "price": "49.93"
  }
]
```

### 3. Analyze Nutrient Consumption by Meal Type

Analyze the food consumption records for **user 10**, from **December 4 through December 10, 2022**, inclusive.

**Business requirements:**
- Filter records by user ID and consumption date.
- Group records by meal type.
- Calculate total protein, carbohydrates, and fat for each group.
- Identify the meal type with the highest total for each nutrient.

**Endpoint:**

`GET /api/calories/highest-nutrients`

**Example result:**

| Nutrient | Meal type | Total |
|---|---|---:|
| Protein | Breakfast | 27.62 |
| Carbohydrates | Breakfast | 43.71 |
| Fat | Breakfast | 49.80 |

The API returns a structured JSON response with the highest protein, carbohydrate, and fat results.

### 4. Find the Three Cheapest Distinct Foods

Identify the three cheapest distinct foods in the dataset and their consumers.

**Business requirements:**
- Identify distinct food names.
- Determine the lowest recorded price for each food.
- Identify the corresponding consumer.
- Return the three cheapest foods with their lowest prices and consumer IDs.

**Status:** Planned / In development.

## Application Architecture

The application follows a layered architecture with a clear separation of responsibilities.

```text
                  HTTP Client
                      |
                      v
             +------------------+
             | CaloriesController|
             | REST API Layer    |
             +------------------+
                      |
                      v
             +------------------+
             | CaloriesService   |
             | Business Logic    |
             +------------------+
                      |
                      v
             +------------------+
             | CaloriesClient    |
             | HTTP Integration  |
             +------------------+
                      |
                      v
             +------------------+
             | GitHub Raw JSON   |
             | calories.json     |
             +------------------+
```

### Design Responsibilities

**Controller Layer:** Handles incoming REST requests and returns API responses.

**Service Layer:** Implements business rules, filtering, grouping, aggregation, and result selection.

**Client Layer:** Retrieves external JSON data using Spring RestClient and deserializes it with Jackson.

**Model Layer:** Represents food consumption records as Java objects.

**DTO Layer:** Defines structured API responses while keeping internal data models separate from external contracts.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Application development |
| Spring Boot 4.2.0 | Application framework |
| Spring Web MVC | REST API endpoints |
| Spring RestClient | HTTP integration |
| Jackson 3 | JSON processing |
| Gradle (Groovy DSL) | Build and dependency management |
| Lombok | Boilerplate code reduction |
| Spring Validation | Request validation support |
| Spring Boot Actuator | Application monitoring |
| JUnit 5 | Automated testing |
| Mockito | Mocking dependencies |
| AssertJ | Fluent test assertions |

## Testing Strategy

The application uses unit and integration tests to verify correctness.

### Unit Tests

JUnit 5, Mockito, and AssertJ are used to test service-layer business logic independently of the external data source.

Test coverage includes:

- Price filtering, sorting, and result limiting
- User and date-range filtering
- Grouping records by meal type
- Nutrient aggregation
- Identification of maximum nutrient totals

### Integration Tests

Integration tests verify that the application can retrieve JSON data from the hosted URL and deserialize it into Java model objects.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 21
- Git
- Internet connection

Gradle installation is not required because the repository includes the Gradle Wrapper.

### Clone the Repository

```bash
git clone https://github.com/yohannes84/calories-spring-boot-api.git
```

### Navigate to the Project

```bash
cd calories-spring-boot-api
```

### Run the Application

On Windows:

```bash
./gradlew.bat bootRun
```

On Linux/macOS:

```bash
./gradlew bootRun
```

The application runs on port `8080` by default.

### Try the API

**Expensive foods:**

`http://localhost:8080/api/calories/expensive-foods`

**Highest nutrient totals:**

`http://localhost:8080/api/calories/highest-nutrients`

### Run Tests

On Windows:

```bash
./gradlew.bat test
```

On Linux/macOS:

```bash
./gradlew test
```

## Configuration

The external JSON source is configured through `application.properties`:

```properties
calories.api.url=https://raw.githubusercontent.com/yohannes84/calories-data/refs/heads/main/calories.json
```

Keeping external URLs in configuration rather than hardcoding them improves maintainability and makes the application easier to configure for different environments.

## Development Status

- [x] Spring Boot project setup
- [x] External JSON retrieval
- [x] JSON deserialization
- [x] Expensive foods endpoint
- [x] Nutrient aggregation endpoint
- [x] Service unit tests
- [x] JSON retrieval integration test
- [ ] Cheapest distinct foods endpoint
- [ ] Complete final testing and review

## Repository Links

**Application repository:** [calories-spring-boot-api](https://github.com/yohannes84/calories-spring-boot-api)

**Dataset repository:** [calories-data](https://github.com/yohannes84/calories-data)

---

**Project purpose:** Demonstrate practical Java backend development using Spring Boot, external API integration, layered architecture, data processing, and automated testing.