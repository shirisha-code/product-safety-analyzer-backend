# 🛡️ Product Safety Analyzer Backend

An AI-powered Product Safety Analyzer built using Spring Boot, PostgreSQL, and Groq AI.

The system analyzes product ingredients, generates safety scores, identifies risky ingredients, provides ingredient explanations, recommends safer alternatives, compares products, and answers product-related safety questions using AI.

---

# 🚀 Features

### Product Analysis
- Analyze complete product safety
- Calculate overall safety score
- Categorize ingredients into:
  - Safe Ingredients
  - Caution Ingredients
  - High Risk Ingredients

### AI Ingredient Analysis
- Automatically analyzes unknown ingredients using Groq AI
- Generates:
  - Risk Score
  - Safety Information
  - Warning Messages
  - Effects on Body
  - Alternative Ingredients
  - Scientific Summary

### Ingredient Knowledge Cache
- Saves AI-generated ingredient knowledge into PostgreSQL
- Prevents repeated AI calls
- Improves performance

### Product Discovery
- Discover ingredients for products not present in database
- Automatically save products and ingredients
- Generate ingredient knowledge if missing

### Product Comparison
- Compare two products side by side
- Compare:
  - Safety Scores
  - Safe Ingredients
  - Caution Ingredients
  - High Risk Ingredients

### Product Chat Assistant
- Ask natural language questions about products
- AI-powered responses

### Product Recommendations
- Recommend safer alternative products
- Provide shopping links

---

# 🛠️ Tech Stack

## Backend
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven

## Database
- PostgreSQL

## AI
- Groq API
- Llama 3.3 70B Versatile

---

# 📂 Project Structure

src/main/java/com/productsafety

- controller
- service
- repository
- entity
- dto

src/main/resources

- application.properties

---

# 🗄️ Database Tables

### Products
Stores product information.

### Ingredients
Stores unique ingredients.

### Product_Ingredients
Maps products to ingredients.

### Ingredient_Knowledge
Stores AI-generated ingredient analysis.

---

# ⚙️ Setup Instructions

## 1. Clone Repository

```bash
git clone https://github.com/shirisha-code/product-safety-analyzer-backend.git
cd product-safety-analyzer-backend
```

## 2. Create PostgreSQL Database

```sql
CREATE DATABASE product_safety;
```

## 3. Create application.properties

Use the provided example file:

```properties
spring.application.name=product-safety-analyzer

spring.datasource.url=jdbc:postgresql://localhost:5432/product_safety
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8080

groq.api.key=YOUR_GROQ_API_KEY
groq.model=llama-3.3-70b-versatile
```

---

## 4. Run Application

```bash
mvn spring-boot:run
```

Application starts on:

```text
http://localhost:8080
```

---

# 📡 API Endpoints

## Product Analysis

```http
GET /products/full-analysis/{productName}
```

Example:

```http
GET /products/full-analysis/Dove Soap
```

---

## Ingredient Analysis

```http
GET /ingredient-analysis/{ingredientName}
```

Example:

```http
GET /ingredient-analysis/Glycerin
```

---

## Product Search

```http
GET /products/search/name/{keyword}
```

---

## Product Chat

```http
POST /chat
```

Request:

```json
{
  "productName": "Dove Soap",
  "summary": "Generally safe product",
  "question": "Can sensitive skin people use this?"
}
```

---

# 🧠 AI Workflow

1. User searches a product
2. System checks database
3. If product exists:
   - Analyze ingredients
   - Calculate safety score
4. If product does not exist:
   - Discover ingredients using AI
   - Save product
   - Save ingredients
   - Generate ingredient knowledge
   - Analyze product
5. Return complete product safety report

---

# 📈 Current Version

### Version 1.0

Completed Features:

- Product Analysis Engine
- AI Ingredient Analysis
- Ingredient Knowledge Cache
- Product Discovery
- Product Comparison
- Product Chat Assistant
- Product Recommendations
- PostgreSQL Integration
- GitHub Repository Setup

---

# 👨‍💻 Author

**Kudikyala Shirisha**

---

# 📄 License

This project is created for educational and portfolio purposes.
