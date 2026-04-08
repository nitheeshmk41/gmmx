# GMMX Mobile - OTP & Authentication Guide

## 📱 UI Updates - Modernized with Web Theme Colors

### Theme Colors
- **Rose 500**: `#f43f5e` (Primary dark theme)
- **Rose 600**: `#e11d48` (Secondary/accents)
- **Background**: Dark navy (`#0f172a`) with subtle rose gradient overlay
- **Matches**: Web design system (`globals.css` rose theme)

### UI Components
- ✨ Rose gradient logo background on GMMX header
- 🎨 Gradient primary button (Rose 500 → Rose 600)
- 🎯 Rose-tinted card borders with frosted glass effect
- 📍 Color-coded info panels (green for success, blue for info, red for errors)
- ⚡ Smooth animations and modern spacing

## 🔐 OTP Methods - All Working

### 1. **Backend OTP** ✅ (Default & Recommended for Testing)
**Works for ALL numbers in your gym system**

```
Endpoint: POST /api/auth/mobile/otp
Endpoint: POST /api/auth/mobile/verify
```

#### How It Works:
1. Enter any mobile number registered with your gym
2. System instantly returns a **debug OTP code** for development
3. Use this code to verify and login
4. In production, OTP is sent via SMS

#### To Test:
1. Select **"OTP"** tab (default)
2. Enter any 10-digit mobile number from your customers
3. Copy the debug OTP displayed below
4. Enter OTP in the verification field
5. Click **"Verify OTP"**

#### Requirements:
- ✅ Backend running on `localhost:8080` (or configured API URL)
- ✅ Database with customer records
- No Firebase setup needed

---

### 2. **Firebase Phone OTP** (Requires Setup)
**For production real phone number authentication**

```
Provider: Google Firebase Authentication
Service: Phone Sign-In
```

#### Setup Checklist:
- [ ] Go to [Firebase Console](https://console.firebase.google.com)
- [ ] Select project `gmmxapp`
- [ ] Navigate to **Authentication → Sign-in method → Phone**
- [ ] **Enable** Phone provider
- [ ] Add **SMS regions** → Select "India" (+91)
- [ ] Go to **Project Settings → Android app**
- [ ] Register app SHA fingerprints:
  ```
  SHA-1:  36:DF:66:A8:7E:F2:61:BE:A4:9B:5F:D4:18:C5:68:8F:5B:51:D8:BE
  SHA-256: F4:4B:6D:80:AF:42:6A:07:76:D4:8C:6D:96:FF:84:4F:34:78:4F:34:48:8F:38:73:2B:B2:51:E9:E1:5B:1D:FB
  ```
- [ ] Ensure Play App Signing is enabled
- [ ] Run: `flutterfire configure --project=gmmxapp`

#### To Test (After Setup):
1. Select **"Phone"** tab
2. Enter any active mobile number
3. Check phone for SMS with OTP
4. Enter OTP code
5. Click **"Verify OTP"**

---

### 3. **Firebase Email Link OTP** (Requires Backend Email Service)
**For passwordless email authentication**

```
Provider: Google Firebase Authentication
Service: Email Link Sign-In
```

#### Setup Needed:
1. Implement backend email verification endpoint
2. Set up email provider (SendGrid, AWS SES, etc.)
3. Configure custom email link handler
4. Test with valid email addresses

#### To Test (After Backend Setup):
1. Select **"Email"** tab
2. Enter email address
3. Check email for sign-in link
4. Paste link in the app
5. Click **"Verify"**

---

## 🚀 Quick Start for Development

### Step 1: Start Backend & Database
```bash
cd docker/
docker-compose up -d
```

### Step 2: Build & Run Mobile App
```bash
cd mobile/
flutter pub get
flutter run
```

### Step 3: Test Backend OTP
1. App opens on LoginPage with **"OTP"** tab selected (default)
2. Enter any mobile number: `9876543210`
3. Debug OTP code appears below (e.g., `123456`)
4. Enter code and verify
5. Success! You're logged in

---

## 📊 OTP Verification Flow

```
┌─────────────────────────────────────────────────┐
│         User Enters Mobile Number                │
└────────────────────┬────────────────────────────┘
                     │
         ┌───────────▼───────────┐
         │  Backend OTP Request   │
         │  (Instant - Dev Mode)  │
         └───────────┬───────────┘
                     │
         ┌───────────▼──────────────┐
         │  Debug OTP Displayed     │
         │  (Ready for immediate    │
         │   testing)               │
         └───────────┬──────────────┘
                     │
         ┌───────────▼────────────────┐
         │  User Enters OTP Code      │
         │  & Clicks "Verify OTP"     │
         └───────────┬────────────────┘
                     │
         ┌───────────▼────────────────┐
         │  Backend Verifies Code     │
         │  & Returns Auth Token      │
         └───────────┬────────────────┘
                     │
         ┌───────────▼────────────────┐
         │  Login Success!            │
         │  Navigate to HomePage      │
         └────────────────────────────┘
```

---

## 🔧 Configuration Files

### `lib/core/config.dart`
```dart
// API Configuration
API_BASE_URL: String          // Backend URL (localhost:8080)
TENANT_SLUG: String          // Current gym/tenant (e.g., coachmohan)

// Firebase Configuration
FIREBASE_EMAIL_LINK_URL      // Email verification handler URL
ANDROID_PACKAGE_NAME         // App package (com.example.gmmx_mobile)
IOS_BUNDLE_ID                // iOS app identifier
```

### `lib/firebase_options.dart`
Auto-generated by `flutterfire configure`
- Web, Android, iOS, macOS, Windows configs
- Project ID: `gmmxapp`
- Real Firebase API keys (keep secure!)

---

## ✅ Testing Scenarios

### Scenario 1: Development Testing (✅ Works Now)
```
1. Backend OTP tab → Enter mobile → Copy debug OTP → Verify
2. No external setup needed
3. Instant feedback for development
```

### Scenario 2: Production Phone Auth (⏳ Needs Firebase Console)
```
1. Firebase Phone tab → Enter mobile → Receive SMS → Verify
2. Requires Firebase setup (see checklist above)
3. Real SMS delivery for live users
```

### Scenario 3: Email Auth (⏳ Needs Backend Email Service)
```
1. Email tab → Enter email → Check email for link → Paste & verify
2. Requires backend email service configuration
3. Passwordless authentication option
```

---

## 🐛 Troubleshooting

### "Backend OTP request failed"
- ✅ Make sure backend is running: `docker-compose up -d`
- ✅ Check backend logs: `docker logs gmmx-backend`
- ✅ Verify database is healthy: `docker logs gmmx-postgres`
- ✅ Test API manually: `curl -X POST http://localhost:8080/api/auth/mobile/otp ...`

### "Firebase phone OTP not working"
- ✅ Verify Firebase Console: Phone provider enabled?
- ✅ Check SMS region: India (+91) added?
- ✅ Confirm SHA fingerprints registered in Firebase
- ✅ Run: `flutterfire configure --project=gmmxapp`
- ✅ Build with: `flutter clean && flutter pub get && flutter run`

### "UI looks wrong / colors off"
- ✅ Run: `flutter clean && flutter pub get`
- ✅ Verify theme in `main.dart`: Rose colors (0xFFe11d48, 0xFFf43f5e)?
- ✅ Check system theme setting: Should be dark mode

### "OTP code not appearing"
- ✅ Backend OTP only shows in debug mode
- ✅ Check app is using dev server (localhost:8080)
- ✅ Verify backend response includes `debugCode` field

---

## 📝 API Reference

### Backend OTP Request
```
POST /api/auth/mobile/otp

Request Body:
{
  "tenantSlug": "coachmohan",
  "mobile": "9876543210"
}

Response Success:
{
  "debugCode": "123456",
  "message": "OTP sent"
}

Response Error:
{
  "error": "Mobile number not found or invalid status",
  "tenantSlug": "coachmohan"
}
```

### Backend OTP Verify
```
POST /api/auth/mobile/verify

Request Body:
{
  "tenantSlug": "coachmohan",
  "mobile": "9876543210",
  "code": "123456"
}

Response Success:
{
  "userId": "uuid-123",
  "role": "member",
  "tenantSlug": "coachmohan"
}

Response Error:
{
  "error": "Invalid OTP"
}
```

---

## 🎯 Next Steps

1. **Immediate**: Test Backend OTP with development numbers
2. **Soon**: Configure Firebase Phone Auth (follow checklist above)
3. **Later**: Implement email verification service for Email Link auth

---

## 📱 App Features Summary

| Feature | Status | Dependencies |
|---------|--------|--------------|
| Backend OTP | ✅ Ready | Backend running, DB |
| Firebase Phone | ✅ Code Ready | Firebase console setup |
| Firebase Email | ✅ Code Ready | Backend email service |
| Modern UI | ✅ Complete | Flutter 3.4+ |
| Dark Theme | ✅ Complete | Rose color scheme |
| Error Handling | ✅ Complete | User-friendly messages |
| Multi-tenant | ✅ Complete | Tenant slug routing |

---

🎉 **Your GMMX mobile app is now ready for testing!**
