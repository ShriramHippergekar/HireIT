# HireIT — Starter Full-Stack Project

This is a starter full-stack project for **HireIT** (Jobs platform).
It includes a Java Spring Boot backend, a React frontend (Vite), Docker Compose for local dev,
and Terraform snippets for AWS. This is a minimal, runnable skeleton to help you build further.

## Quick start (local)

1. Ensure Docker & Docker Compose are installed.
2. From this project root run:
   ```
   docker-compose up --build
   ```
3. Backend: http://localhost:8080
4. Frontend: http://localhost:3000

Default Postgres credentials (used in docker-compose):
- DB: hireit
- User: hireit
- Password: hireitpass

JWT secret used in backend: `change-me` (for dev only)

## Contents
- hireit-backend/: Spring Boot app (Maven)
- hireit-frontend/: React (Vite)
- docker-compose.yml
- terraform/: basic Terraform snippets
- .github/: CI workflow

