# BFHL REST API - API Round (24 June)

A production-ready, highly configurable, and clean-architecture-based Java Spring Boot REST API for processing, categorizing, and transforming mixed data lists.

---

## Technical Stack
- **Language:** Java 17
- **Framework:** Spring Boot 3.3.0
- **Build Tool:** Maven
- **Documentation:** SpringDoc OpenAPI 3 (Swagger UI)

---

## Features
- **Data Classification:** Categorizes lists of input strings into odd numbers, even numbers, alphabets, and special characters.
- **Parity-Safe Calculations:** Accurately sums all numeric elements, handling both positive and negative values without integer overflow.
- **Alphabet Manipulation (CONCAT_STRING):**
  1. Filters elements consisting purely of letters.
  2. Concatenates them in order of appearance.
  3. Reverses the concatenated string.
  4. Appends them in alternating case starting with an uppercase character.
- **Externalized Constants:** Configurable metadata (`fullName`, `dob`, `email`, `rollNumber`) via `application.properties`, avoiding hardcoded credentials in the source code.
- **Global Exception Handler:** Catches validation constraints, null body inputs, and invalid JSON formats.
- **JUnit 5 & MockMvc Test Suite:** Includes 9 test cases covering all edge conditions and error boundaries.

---

## Project Structure

```text
com.example.bfhl/
├── config/
│   └── OpenApiConfig.java          # Swagger OpenAPI documentation bean
├── controller/
│   └── BfhlController.java         # REST Controller exposing POST /bfhl
├── dto/
│   ├── BfhlRequest.java            # Validated request DTO
│   └── BfhlResponse.java           # Jackson-mapped JSON response DTO
├── exception/
│   └── GlobalExceptionHandler.java # Intercepts validation errors and invalid JSON
├── service/
│   └── BfhlService.java            # Service contract interface
│       └── impl/
│           └── BfhlServiceImpl.java # Core business logic engine
└── BfhlApplication.java            # Main Bootstrapper
```

---

## Quick Start & Local Setup

### Prerequisites
- Java JDK 17 or higher
- Maven 3.6+ or standard Maven Wrapper

### 1. Configure Personal Details
Open `src/main/resources/application.properties` and replace the placeholder fields:
```properties
bfhl.user.full-name=your_full_name
bfhl.user.dob=ddmmyyyy
bfhl.user.email=your_email
bfhl.user.roll-number=your_roll_number
```

### 2. Build the Application
Compile the code and execute the automated integration test suite:
```bash
mvn clean test
```

### 3. Run the Application
Run the local development server:
```bash
mvn spring-boot:run
```
The server will start on port `8080` (or the `PORT` specified in your environment variables).

---

## API Contract & Swagger UI

### Swagger API Documentation
Once the server is running, navigate to:
- **Interactive UI (Swagger):** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **JSON OpenAPI Specifications:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Endpoint Specification
- **Method:** `POST`
- **Path:** `/bfhl`
- **Request Headers:** `Content-Type: application/json`

#### Sample Request Body
```json
{
  "data": ["2", "a", "y", "4", "&", "-", "*", "5", "92", "b"]
}
```

#### Sample Response Body (HTTP 200)
```json
{
  "is_success": true,
  "user_id": "john_doe_15082002",
  "email": "john.doe@example.com",
  "roll_number": "2110991234",
  "odd_numbers": ["5"],
  "even_numbers": ["2", "4", "92"],
  "alphabets": ["A", "Y", "B"],
  "special_characters": ["&", "-", "*"],
  "sum": "103",
  "concat_string": "ByA"
}
```

#### Error Response Body (HTTP 400 - e.g., missing data array)
```json
{
  "is_success": false,
  "message": "Validation failed: data list cannot be null; "
}
```

---

## Deployment Instructions

### Deploying to Render
1. Create a new Web Service on Render and connect your GitHub repository.
2. Configure build settings:
   - **Environment:** `Docker` or `Java`
   - **Build Command:** `mvn clean package -DskipTests`
   - **Start Command:** `java -jar target/bfhl-0.0.1-SNAPSHOT.jar`
3. Add Environment Variables (optional):
   - Set custom parameters if you want to override the default metadata in production:
     - `BFHL_USER_FULL-NAME` = `your_full_name`
     - `BFHL_USER_DOB` = `ddmmyyyy`
     - `BFHL_USER_EMAIL` = `your_email`
     - `BFHL_USER_ROLL-NUMBER` = `your_roll`

### Deploying to Railway
1. Create a new project on Railway.
2. Select **Deploy from GitHub repo** and connect your repository.
3. Railway automatically detects the project as a Maven/Spring Boot application.
4. It will read the `Procfile` at the root and use the start command:
   ```bash
   web: java -jar target/bfhl-0.0.1-SNAPSHOT.jar
   ```
5. Railway will automatically inject the `PORT` variable.
