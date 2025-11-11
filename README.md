=======
# Email Sending Service

A robust, scalable REST API service for sending emails asynchronously using Spring Boot, RabbitMQ, and PostgreSQL. This service supports JWT authentication, email templates via Thymeleaf, and secure SMTP configuration, making it suitable for enterprise-level email operations.

## Features

- **Asynchronous Email Sending**: Utilizes RabbitMQ for queuing and processing emails in the background, ensuring high performance and reliability.
- **JWT Authentication**: Secure user authentication and authorization for API endpoints.
- **Email Templates**: Support for HTML email templates using Thymeleaf.
- **Database Persistence**: Stores email logs and user data in PostgreSQL.
- **Docker Support**: Easy deployment using Docker and Docker Compose.
- **RESTful API**: Clean, well-documented endpoints for email operations.
- **Security**: Integrated Spring Security with JWT tokens.
- **Logging and Monitoring**: Comprehensive logging for email status and system health.

## Prerequisites

Before running this application, ensure you have the following installed:

- **Java 17** or higher
- **Maven 3.6+** (for local builds)
- **Docker and Docker Compose** (for containerized deployment)
- **Git** (for cloning the repository)

## Installation

### Clone the Repository

```bash
git clone https://github.com/your-username/enterprise-email-service.git
cd enterprise-email-service
```

### Local Setup (Without Docker)

1. **Install Dependencies**:
   ```bash
   mvn clean install
   ```

2. **Configure Environment Variables**:
   Create a `.env` file or set environment variables for the following (refer to `application.yml` for defaults):
   - `DB_HOST`: PostgreSQL host (default: localhost)
   - `DB_PORT`: PostgreSQL port (default: 5432)
   - `DB_NAME`: Database name (default: emaildb)
   - `DB_USER`: Database username (default: admin)
   - `DB_PASS`: Database password (default: password)
   - `RABBIT_HOST`: RabbitMQ host (default: localhost)
   - `RABBIT_PORT`: RabbitMQ port (default: 5672)
   - `RABBIT_USER`: RabbitMQ username (default: guest)
   - `RABBIT_PASS`: RabbitMQ password (default: guest)
   - `SMTP_HOST`: SMTP server host (default: smtp.gmail.com)
   - `SMTP_PORT`: SMTP server port (default: 587)
   - `SMTP_USER`: SMTP username (your email)
   - `SMTP_PASS`: SMTP password (use app password for Gmail)
   - `PORT`: Application port (default: 8080)

3. **Set Up PostgreSQL and RabbitMQ**:
   - Install and start PostgreSQL locally.
   - Install and start RabbitMQ locally.
   - Create the database specified in `DB_NAME`.

### Docker Setup

1. **Update SMTP Credentials**:
   Edit `docker-compose.yml` and replace `SMTP_USER` and `SMTP_PASS` with your actual SMTP credentials. For Gmail, use an app password.

2. **Build and Run**:
   ```bash
   # Build the JAR (optional, Docker can build it)
   mvn clean package -DskipTests

   # Start all services
   docker-compose up --build
   ```

   This will start:
   - The Spring Boot application on `http://localhost:8080`
   - PostgreSQL on port 5432
   - RabbitMQ on ports 5672 (AMQP) and 15672 (Management UI)

## Running the Application

### Local Run

After setting up dependencies and environment variables:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

### Docker Run

As described in the installation section, use `docker-compose up --build`.

### Accessing Services

- **Application**: `http://localhost:8080`
- **RabbitMQ Management**: `http://localhost:15672` (username: guest, password: guest)
- **PostgreSQL**: Accessible via localhost:5432 with configured credentials

## API Documentation

### Authentication

#### Register a New User
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "your_username",
  "password": "your_password",
  "email": "your_email@example.com"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "your_username",
  "password": "your_password"
}
```

Response includes JWT token for authenticated requests.

### Email Operations

All email endpoints require JWT authentication. Include the token in the Authorization header: `Bearer <token>`

#### Send Email
```http
POST /api/email/send
Authorization: Bearer <token>
Content-Type: application/json

{
  "to": "recipient@example.com",
  "subject": "Hello from Enterprise Email Service",
  "body": "<h3>This is an HTML email body</h3>",
  "template": "welcome-email"  // Optional: use a predefined template
}
```

#### Get Email Logs
```http
GET /api/email/logs
Authorization: Bearer <token>
```

#### Get Email Status by ID
```http
GET /api/email/status/{id}
Authorization: Bearer <token>
```

## Configuration

The application uses `application.yml` for configuration. Key settings include:

- **Database**: PostgreSQL connection details
- **Mail**: SMTP server configuration
- **RabbitMQ**: Message queue settings
- **Security**: JWT secret and expiration
- **Logging**: Log levels and output

Environment variables override defaults in `application.yml`.

## Project Structure

```
src/
├── main/
│   ├── java/com/example/emailservice/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── entity/          # JPA entities
│   │   ├── producer/        # RabbitMQ producer
│   │   ├── consumer/        # RabbitMQ consumer
│   │   ├── repository/      # JPA repositories
│   │   ├── service/         # Business logic services
│   │   └── util/            # Utility classes
│   └── resources/
│       ├── application.yml  # Application configuration
│       └── templates/       # Thymeleaf email templates
└── test/                    # Unit and integration tests
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For support, email support@example.com or open an issue in the GitHub repository.
=======
