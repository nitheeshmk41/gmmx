# GMMX MVP Test Checklist

## Backend

1. POST /api/auth/owner/register returns tenantId + slug + trialEndsAt.
2. POST /api/member enforces member limit from trial plan.
3. POST /api/trainer enforces trainer limit from trial plan.
4. GET /api/dashboard/summary returns counts and trial days left.
5. POST /api/attendance/scan rejects duplicate same-day attendance.

## Web

1. /signup submits owner onboarding.
2. /{slug}/dashboard loads summary cards.
3. Add Member and Add Trainer forms create records.
4. /{slug} microsite renders public gym content.

## Mobile

1. Enter mobile and request OTP.
2. Verify OTP with debug code and route to role home.
3. Open QR attendance screen placeholder.

## Infra

1. docker compose up starts postgres/backend/web/nginx.
2. backend can connect to postgres.
3. web can call backend APIs via NEXT_PUBLIC_API_BASE_URL.
