# GMMX Backend API

Spring Boot backend for the GMMX MVP.

## Base URL

When the app is running locally, the API is typically served from:

```text
http://localhost:8080
```

All routes below are relative to that base URL.

## HTTP methods in simple terms

- **GET**: read data from the server.
- **POST**: create data or trigger an action.
- **PUT**: usually update/replace existing data.
- **DELETE**: remove data.

> Current backend controllers expose **GET** and **POST** endpoints only. There are no **PUT** or **DELETE** routes in the current codebase.

## Using these endpoints in Postman

1. Open Postman and click **New** → **HTTP Request**.
2. Choose the request method, such as **GET** or **POST**.
3. Enter the full URL, for example `http://localhost:8080/api/auth/mobile/otp`.
4. For **POST** requests, go to **Body** → **raw** → **JSON** and paste the JSON payload.
5. Add header:
   - `Content-Type: application/json`
6. Click **Send**.

## API endpoints

### Auth

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/auth/owner/register` | Register a new gym owner and tenant. |
| `POST` | `/api/auth/mobile/otp` | Send an OTP to a mobile number. |
| `POST` | `/api/auth/mobile/verify` | Verify OTP and return login/session details. |

#### `POST /api/auth/owner/register`

Request body:

```json
{
  "ownerName": "John Doe",
  "mobile": "9876543210",
  "email": "john@example.com",
  "gymName": "Power House Gym",
  "location": "Bangalore",
  "slug": "power-house-gym",
  "password": "secret123"
}
```

Response fields:

- `tenantId`
- `slug`
- `ownerId`
- `trialEndsAt`

#### `POST /api/auth/mobile/otp`

Request body:

```json
{
  "tenantSlug": "power-house-gym",
  "mobile": "9876543210"
}
```

Response fields:

- `message`
- `expiresInSeconds`
- `debugCode`

#### `POST /api/auth/mobile/verify`

Request body:

```json
{
  "tenantSlug": "power-house-gym",
  "mobile": "9876543210",
  "code": "123456"
}
```

Response fields:

- `userId`
- `role`
- `tenantId`
- `tenantSlug`

### Attendance

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/attendance/qr/generate` | Generate a daily QR token for a tenant. |
| `POST` | `/api/attendance/scan` | Scan a QR token and mark attendance. |

#### `POST /api/attendance/qr/generate`

Request body:

```json
{
  "tenantSlug": "power-house-gym"
}
```

Response fields:

- `tenantSlug`
- `qrToken`
- `qrDate`

#### `POST /api/attendance/scan`

Request body:

```json
{
  "tenantSlug": "power-house-gym",
  "qrToken": "qr-token-value",
  "memberId": "11111111-1111-1111-1111-111111111111"
}
```

Response fields:

- `message`
- `attendanceDate`

### Dashboard

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/dashboard/summary?tenantSlug=power-house-gym` | Get tenant dashboard summary. |

#### `GET /api/dashboard/summary`

Query parameters:

- `tenantSlug` (required)

Response fields:

- `tenantSlug`
- `activeMembers`
- `activeTrainers`
- `todayAttendance`
- `trialDaysLeft`
- `memberLimit`
- `trainerLimit`

### Tenant

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/tenant/create` | Create a tenant/gym. |
| `GET` | `/api/public/{slug}` | Fetch public gym profile by slug. |

#### `POST /api/tenant/create`

Request body:

```json
{
  "gymName": "Power House Gym",
  "location": "Bangalore",
  "slug": "power-house-gym"
}
```

Response fields:

- `tenantId`
- `slug`

#### `GET /api/public/{slug}`

Example request:

```text
GET /api/public/power-house-gym
```

Response fields:

- `slug`
- `gymName`
- `location`
- `about`
- `contactPhone`
- `themePrimary`

### Users

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/member` | Create a member user. |
| `POST` | `/api/trainer` | Create a trainer user. |

#### `POST /api/member`

Request body:

```json
{
  "tenantSlug": "power-house-gym",
  "fullName": "Jane Member",
  "mobile": "9876543210",
  "email": "jane@example.com"
}
```

Response fields:

- `id`
- `fullName`
- `mobile`
- `email`
- `role`

#### `POST /api/trainer`

Request body:

```json
{
  "tenantSlug": "power-house-gym",
  "fullName": "Mark Trainer",
  "mobile": "9876543211",
  "email": "mark@example.com"
}
```

Response fields:

- `id`
- `fullName`
- `mobile`
- `email`
- `role`

## Validation and error responses

The backend uses a global exception handler for validation and HTTP status errors.

### Validation error example

```json
{
  "error": "Validation failed",
  "status": 400,
  "details": ["fieldName: must not be blank"]
}
```

### ResponseStatusException example

```json
{
  "error": "Not found",
  "status": 404
}
```

## Quick Postman checklist

- Set the request method (`GET` or `POST`).
- Use the correct URL.
- Add `Content-Type: application/json` for POST requests.
- Put the JSON payload in the body.
- Review the response status and body.

## Running tests

```bash
mvn test
```

