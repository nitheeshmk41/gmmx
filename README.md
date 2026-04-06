# GMMX V2 MVP (Monorepo)

Multi-tenant gym SaaS MVP for India.

## Stack

- Web: Next.js (App Router)
- Mobile: Flutter (Android first)
- Backend: Spring Boot 3 + Java 21
- Database: PostgreSQL 16
- Infra: Docker Compose + Nginx

## Monorepo Structure

```
backend/               # Spring Boot tenant-aware API
database/init/         # DB init SQL
docker/                # Compose + nginx
docs/                  # Documentation
mobile/                # Flutter app (OTP + role routing)
web/                   # Next.js app (onboarding + dashboard + microsite)
```

## MVP Features Included

- Owner web onboarding with 14-day trial provisioning
- Slug-based tenant creation
- Tenant-aware entities with tenant_id
- Owner/member/trainer role model
- Mobile OTP request + verify flow (debug OTP for dev)
- Dashboard summary API and UI
- Member/Trainer quick add
- Public microsite by slug
- Daily QR generate and attendance scan API with duplicate same-day prevention
- Dockerized local stack

## Environment Setup

1. Copy root env:

```
copy .env.example .env
```

2. Backend env (optional if using defaults):

```
copy backend\.env.example backend\.env
```

3. Web env:

```
copy web\.env.example web\.env.local
```

## Run With Docker (Recommended)

From repository root:

```
docker compose -f docker/docker-compose.yml --env-file .env up --build
```

Services:

- Web: http://localhost:3000
- Backend: http://localhost:8080
- Postgres: localhost:5432
- Nginx: http://localhost:80

## Run Locally Without Docker

### Backend

```
cd backend
mvn spring-boot:run
```

### Web

```
cd web
npm install
npm run dev
```

### Mobile

```
cd mobile
flutter pub get
flutter run --dart-define=API_BASE_URL=http://10.0.2.2:8080 --dart-define=TENANT_SLUG=your-slug
```

## Core APIs

- POST /api/auth/owner/register
- POST /api/auth/mobile/otp
- POST /api/auth/mobile/verify
- POST /api/tenant/create
- GET /api/public/{slug}
- POST /api/member
- POST /api/trainer
- GET /api/dashboard/summary?tenantSlug={slug}
- POST /api/attendance/qr/generate
- POST /api/attendance/scan

## Quick Manual Test Flow

1. Register owner from web signup page.
2. Open slug dashboard page and add one trainer + one member.
3. Call QR generate endpoint for that slug.
4. In mobile app, request OTP using trainer/member number.
5. Verify OTP with returned debug code.

## Notes

- OTP SMS provider integration is intentionally mocked for MVP speed.
- Security is baseline and should be hardened in next iteration (JWT, refresh tokens, RBAC guards).
- Flutter project is scaffold-level MVP; run `flutter create .` inside `mobile/` once to generate Android/iOS platform folders if not already present.

## Next Implementation Checklist

1. Add JWT auth and guarded dashboard routes.
2. Add real SMS provider integration (MSG91/Twilio).
3. Add production wildcard domain + SSL on VPS.
4. Add QR scanning package in Flutter and attendance call wiring.
5. Add CI/CD pipelines in .github/workflows.
