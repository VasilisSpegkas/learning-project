LEARNING PROJECT  

A small hands-on project for learning how these pieces work individually and how they connect together. No real business purpose — just wiring.
Components: Spring Boot + Redis + PostgreSQL + Keycloak + React

STACK
- Spring Boot (Java) — backend API
- Redis — fast in-memory cache
- PostgreSQL — permanent relational database
- Keycloak — handles user login/authentication, issues tokens
- React (Vite) — frontend
- Docker Compose — runs Redis, Postgres, Keycloak, and the Spring app together


HOW TO RUN
- Make sure Docker Desktop is open and running.
- From the Spring project folder, run:
  ~ docker compose up --build

Services will be available at:
- Spring API → http://localhost:8080
- Postgres → localhost:5432 (user/pass: demo / demo)
- Redis → localhost:6379
- REACT Frontend runs at http://localhost:5173
  ~ cd Frontend
  ~ npm install
  ~ npm run dev

- Keycloak admin console → http://localhost:8081 (login: admin / admin)
    Getting a login token (for testing protected endpoints):
    ~ curl.exe -X POST http://localhost:8081/realms/demo-app/protocol/openid-connect/token ^
    -H "Content-Type: application/x-www-form-urlencoded" ^
    -d "client_id=spring-backend" ^
    -d "client_secret=YOUR_CLIENT_SECRET" ^
    -d "grant_type=password" ^
    -d "username=YOUR_TEST_USERNAME" ^
    -d "password=YOUR_TEST_PASSWORD"

    Then call a protected endpoint with the returned access_token:
    ~ curl.exe http://localhost:8080/hello -H "Authorization: Bearer YOUR_ACCESS_TOKEN"


NOTES
Redis data is disposable — treated purely as cache, never source of truth.
Postgres data persists across restarts via a named Docker volume.
Inside Docker's internal network, services reach each other by service name (e.g. redis, postgres, keycloak), not localhost.