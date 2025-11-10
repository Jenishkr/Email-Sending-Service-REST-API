# Enterprise Email Sending Service

## Quick start (Docker)
1. Replace `SMTP_USER` and `SMTP_PASS` in `docker-compose.yml` with valid SMTP credentials (Gmail: use app password).
2. Build jar locally or let Docker build (recommended to build jar first):

   ```bash
   mvn clean package -DskipTests
   docker-compose up --build
   ````

3. Service will be at `http://localhost:8080`.

## API examples
Send a single email (POST):

```
POST http://localhost:8080/api/email/send
Content-Type: application/json

{
  "to": "recipient@example.com",
  "subject": "Hello from Enterprise Email Service",
  "body": "<h3>This is an HTML email body</h3>"
}
```

Get logs:
```
GET http://localhost:8080/api/email/logs
```

Check status by id:
```
GET http://localhost:8080/api/email/status/{id}
<<<<<<< HEAD
```
=======
```
>>>>>>> 3208f67fcaa9ad671a33cf201d06104b5909215b
