# TODO List for Java Email Sending Service with MERN Frontend

## Backend Enhancements (Spring Boot)
- [ ] Add Spring Security and JWT dependencies to pom.xml
- [ ] Create User entity for authentication
- [ ] Create UserRepository
- [ ] Create JWT utility class
- [ ] Create AuthController for login/register
- [ ] Secure email endpoints with JWT
- [ ] Update EmailRequest DTO to include attachments (List<String> base64)
- [ ] Update EmailController to handle attachments in request
- [ ] Update EmailConsumer to process templates using Thymeleaf
- [ ] Update EmailConsumer to handle attachments (decode base64 and attach)
- [ ] Add more templates in resources/templates
- [ ] Test all features: send with attachments, templates, logs, retry

## Frontend (MERN Stack)
- [ ] Create Node.js Express backend in Email_Service directory
- [ ] Add MongoDB connection (for potential user data, but proxy to Java API)
- [ ] Create React app for dashboard
- [ ] Implement login page (call Java auth)
- [ ] Implement dashboard: list email logs, send email form with attachments, templates
- [ ] Connect React to Java API via fetch/axios
- [ ] Add Docker for MERN if needed

## Deployment
- [ ] Update docker-compose.yml to include MERN services
- [ ] Ensure full stack runs with docker-compose

## Testing
- [ ] Test sending emails with all features
- [ ] Test dashboard functionality
