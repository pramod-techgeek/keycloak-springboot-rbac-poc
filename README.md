# Keycloak RBAC POC

A Proof of Concept demonstrating Role-Based Access Control (RBAC) using Keycloak, Spring Boot, and React.

## Architecture

- **Identity Provider**: Keycloak (Docker)
  - Realm: `rbac-realm`
  - Clients: `rbac-backend`, `rbac-frontend`
- **Backend**: Spring Boot 3.x (Resource Server)
  - Validates JWTs
  - Enforces Role checks (`ADMIN`, `USER`)
- **Frontend**: React (SPA)
  - Authenticates via Keycloak (Authorization Code Flow with PKCE)
  - Displays content based on roles

## Prerequisites

- Docker & Docker Compose
- Java 25 (for Backend build)
- Node.js (for Frontend, optional if running via other means, but needed for `npm start`)

## How to Run

### 1. Start Keycloak and Backend (Docker)

First, build the backend application:
```bash
cd backend
./gradlew clean build -x test
cd ..
```

Then start the services:
```bash
docker-compose up --build
```

- **Keycloak**: http://localhost:8080
- **Backend API**: http://localhost:8081

### 2. Start Frontend

```bash
cd frontend
npm install
npm start
```

- **Frontend**: http://localhost:3000

## Testing Credentials

| User  | Password | Component Access |
|-------|----------|------------------|
| alice | password | User Page        |
| bob   | password | User & Admin Page|

## Verification Steps

1. Open http://localhost:3000.
2. Click **Login** and sign in as `alice`.
3. Verify you can access **User Page** but don't see **Admin Page** link (or receive "Access Denied" if accessed directly).
4. Logout and sign in as `bob`.
5. Verify you can access **Admin Page**.
6. Check Backend logs to see authorized requests.

## Key Configuration Details

- **Backend**: Configured as Resource Server. Mapped `realm_access.roles` to Spring Security Authorities via `JwtRoleConverter`.
- **Docker Networking**: `docker-compose.yml` configures Backend to validate tokens issued by `http://localhost:8080` (Browser) while validating keys via internal Docker network `http://keycloak:8080`.
