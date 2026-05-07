# Banking Microservices Platform

A complete enterprise-level banking system built with Spring Boot 3, Microservices, Angular 16, Docker, and MySQL.

## Tech Stack

### Backend
- **Core Java** (Java 21)
- **Spring Boot 3.x**
- **Spring Cloud** (Service Discovery, Config Server)
- **Spring Data JPA** (Hibernate)
- **Spring Security** (JWT Authentication)
- **MySQL 8.0**
- **Docker & Docker Compose**

### Frontend
- **Angular 16**
- **Bootstrap 5**
- **TypeScript**
- **RxJS**

### Quality & DevOps
- **JUnit 5 & Mockito** (Unit Testing)
- **SonarQube** (Code Quality)
- **Git** (Version Control)
- **Logging** (SLF4J + Logback)

## Project Structure

```
banking-microservices/
├── api-gateway/                 # API Gateway Service
├── user-service/                # User & Authentication Service
├── account-service/             # Account Management Service
├── transaction-service/         # Transaction Processing Service
├── common/                       # Shared DTOs & Utils
├── docker-compose.yml           # Docker Orchestration
├── pom.xml                       # Parent POM
└── README.md
```

## Microservices Architecture

### 1. User Service (Port: 8001)
- User registration & login
- JWT token generation
- Role-based access control (RBAC)
- User profile management

### 2. Account Service (Port: 8002)
- Create & manage accounts
- Account balance inquiries
- Account types (Savings, Checking, Business)

### 3. Transaction Service (Port: 8003)
- Money transfers between accounts
- Transaction history
- Transaction status tracking

### 4. API Gateway (Port: 8000)
- Route requests to appropriate services
- Load balancing
- Request/Response logging

## Prerequisites

- Java 21
- Maven 3.8+
- MySQL 8.0
- Docker & Docker Compose
- Node.js 18+ (for Angular)

## Getting Started

### Step 1: Clone Repository
```bash
git clone https://github.com/Avinash9111989/banking.git
cd banking
```

### Step 2: Start Services with Docker
```bash
docker-compose up -d
```

This will start:
- MySQL (Port: 3306)
- User Service (Port: 8001)
- Account Service (Port: 8002)
- Transaction Service (Port: 8003)
- API Gateway (Port: 8000)

### Step 3: Build & Run Locally (Alternative)
```bash
# Build all services
mvn clean install

# Run each service
cd user-service && mvn spring-boot:run
cd account-service && mvn spring-boot:run
cd transaction-service && mvn spring-boot:run
cd api-gateway && mvn spring-boot:run
```

### Step 4: Setup Angular Frontend
```bash
cd frontend
npm install
ng serve
```
Access at: `http://localhost:4200`

## API Endpoints

### User Service
- `POST /api/users/register` - Register new user
- `POST /api/users/login` - User login
- `GET /api/users/{id}` - Get user details

### Account Service
- `POST /api/accounts` - Create account
- `GET /api/accounts/{id}` - Get account details
- `GET /api/accounts/user/{userId}` - Get user accounts

### Transaction Service
- `POST /api/transactions/transfer` - Transfer money
- `GET /api/transactions/{id}` - Get transaction details
- `GET /api/transactions/account/{accountId}` - Get account transactions

## Database Schema

See `sql/schema.sql` for complete database schema with tables:
- users
- accounts
- transactions
- roles
- user_roles

## Testing

### Run Unit Tests
```bash
mvn test
```

### Run Tests with Coverage
```bash
mvn jacoco:report
```

### SonarQube Analysis
```bash
mvn sonar:sonar \
  -Dsonar.projectKey=banking-microservices \
  -Dsonar.sources=. \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=your-token
```

## Docker Commands

```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down

# Rebuild images
docker-compose build --no-cache
```

## Security Features

- ✅ JWT Token-based authentication
- ✅ Role-Based Access Control (RBAC)
- ✅ Password encryption (BCrypt)
- ✅ SQL Injection prevention (Prepared Statements)
- ✅ CORS configuration
- ✅ Request validation

## Best Practices Implemented

- ✅ SOLID Principles
- ✅ Clean Code Architecture
- ✅ Microservices Design Patterns
- ✅ Comprehensive Logging
- ✅ Unit Testing (JUnit/Mockito)
- ✅ Exception Handling
- ✅ API Documentation (Swagger/OpenAPI)

## Deployment

### Deploy to Docker Hub
```bash
docker build -t username/banking-user-service user-service/
docker push username/banking-user-service
```

### Deploy to Kubernetes (Optional)
See `k8s/` folder for Kubernetes manifests.

## Contributing

1. Create a feature branch
2. Commit your changes
3. Push to the branch
4. Create a Pull Request

## License

MIT License - See LICENSE file

## Support

For issues and questions, create a GitHub Issue.

---

**Last Updated:** May 2026
**Author:** Banking Team
