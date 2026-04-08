# 📱 Real SMS OTP Setup Guide

Your GMMX mobile app now has theme colors perfectly matching your web design. Now let's set up real SMS delivery!

## 🚀 Option 1: Firebase Phone OTP (Recommended - Easiest)

Firebase handles everything - your app just needs configuration.

### Step-by-Step Setup

#### 1. **Enable Phone Authentication**
```
Go to: Firebase Console
  → Project: gmmxapp
  → Authentication
  → Sign-in method
  → Phone
  → Enable toggle
  → Save
```

#### 2. **Allow India SMS Region**
```
In Phone Sign-in settings:
  → Scroll to "SMS provider configuration (optional)"
  → Add Region: India (+91)
  → Save
```

#### 3. **Register App Fingerprints** (CRITICAL)
```
Firebase Console
  → Project Settings (gear icon)
  → Android app: com.example.gmmx_mobile
  → SHA certificate fingerprints
  → Add certificate:
     SHA-1:  36:DF:66:A8:7E:F2:61:BE:A4:9B:5F:D4:18:C5:68:8F:5B:51:D8:BE
     SHA-256: F4:4B:6D:80:AF:42:6A:07:76:D4:8C:6D:96:FF:84:4F:34:78:4F:34:48:8F:38:73:2B:B2:51:E9:E1:5B:1D:FB
  → Save
```

#### 4. **Reconfigure FlutterFire**
```bash
cd mobile/
flutterfire configure --project=gmmxapp --yes
```

#### 5. **Test it!**
```bash
# In Flutter terminal:
r        # Hot reload after config changes

# Then in app:
1. Select "Phone" tab
2. Enter your own mobile number: +919876543210
3. Check your phone for SMS with OTP
4. Enter code and verify
```

**Why this works:**
- Firebase sends real SMS directly to users
- No backend SMS provider needed
- Google handles reCAPTCHA and Play Integrity checks
- SMS delivered in seconds

---

## 🔌 Option 2: Backend OTP with Real SMS (Professional)

For production gyms, integrate SMS provider into backend.

### Step 1: Add SMS Provider to Backend

**Installation (Choose one):**

**Option A: Twilio (Most Popular)**
```bash
# Add to backend pom.xml
<dependency>
    <groupId>com.twilio.sdk</groupId>
    <artifactId>twilio</artifactId>
    <version>8.10.0</version>
</dependency>
```

**Option B: AWS SNS**
```bash
# Add to backend pom.xml
<dependency>
    <groupId>software.amazon.awssdk</groupId>
    <artifactId>sns</artifactId>
    <version>2.20.0</version>
</dependency>
```

**Option C: OTP.io (India-Specific)**
```bash
# Add to backend pom.xml
<dependency>
    <groupId>otp.io</groupId>
    <artifactId>java-sdk</artifactId>
    <version>1.0</version>
</dependency>
```

### Step 2: Update Backend OTP Endpoint

**Example with Twilio (Java/Spring Boot):**

```java
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@RestController
@RequestMapping("/api/auth/mobile")
public class OtpController {
    
    // Set from environment
    private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private static final String TWILIO_PHONE = System.getenv("TWILIO_PHONE");
    
    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }
    
    @PostMapping("/otp")
    public ResponseEntity<?> requestOtp(@RequestBody OtpRequest req) {
        try {
            // Generate 6-digit OTP
            String otp = String.format("%06d", new Random().nextInt(999999));
            
            // Save to database with expiration (5 minutes)
            otpService.saveOtp(req.getTenantSlug(), req.getMobile(), otp);
            
            // Send SMS via Twilio
            String phoneWithCountryCode = req.getMobile().startsWith("+") 
                ? req.getMobile() 
                : "+91" + req.getMobile();
            
            Message message = Message.creator(
                new PhoneNumber(phoneWithCountryCode),  // To number
                new PhoneNumber(TWILIO_PHONE),          // From number
                "Your GMMX OTP is: " + otp + ". Valid for 5 minutes."
            ).create();
            
            return ResponseEntity.ok(Map.of(
                "message", "OTP sent successfully",
                "messageId", message.getSid()
            ));
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "error", "Failed to send SMS: " + e.getMessage()
            ));
        }
    }
    
    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerifyRequest req) {
        try {
            boolean isValid = otpService.verifyOtp(
                req.getTenantSlug(),
                req.getMobile(),
                req.getCode()
            );
            
            if (!isValid) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Invalid or expired OTP"
                ));
            }
            
            // Generate auth token and return user
            String authToken = authService.generateToken(req.getMobile());
            return ResponseEntity.ok(Map.of(
                "token", authToken,
                "userId", user.getId(),
                "role", user.getRole()
            ));
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "error", "Verification failed"
            ));
        }
    }
}
```

### Step 3: Set Environment Variables

Add to your `.env` file or Docker Compose:

```bash
# Twilio
TWILIO_ACCOUNT_SID=your_account_sid
TWILIO_AUTH_TOKEN=your_auth_token
TWILIO_PHONE=+1234567890

# Or AWS SNS
AWS_SNS_REGION=ap-south-1
AWS_SNS_ACCESS_KEY=xxx
AWS_SNS_SECRET_KEY=xxx
```

### Step 4: Update Docker Compose

```yaml
backend:
  build:
    context: ../backend
    dockerfile: Dockerfile
  environment:
    # ... existing vars ...
    TWILIO_ACCOUNT_SID: ${TWILIO_ACCOUNT_SID}
    TWILIO_AUTH_TOKEN: ${TWILIO_AUTH_TOKEN}
    TWILIO_PHONE: ${TWILIO_PHONE}
```

### Step 5: Test Backend OTP SMS

```bash
# Test via curl
curl -X POST http://localhost:8080/api/auth/mobile/otp \
  -H "Content-Type: application/json" \
  -d '{
    "tenantSlug": "coachmohan",
    "mobile": "+919876543210"
  }'

# Check for SMS on your phone!
```

---

## 📊 Comparison: Firebase vs Backend SMS

| Feature | Firebase Phone OTP | Backend OTP + SMS |
|---------|---|---|
| Setup Time | 5-10 minutes | 30-60 minutes |
| Cost | Free (then ~$0.02/SMS) | Carrier dependent (~$0.05-0.10/SMS) |
| Reliability | 99.9% (Google) | Depends on provider |
| Control | Limited | Full control |
| Customization | Limited SMS text | Full customization |
| Best For | Quick launch | Custom branding |

---

## 🎯 My Recommendation

**For MVP/Development:**
✅ Use **Firebase Phone OTP** (takes 10 minutes to set up)

**For Production Gyms:**
✅ Implement **Backend OTP + Twilio** (more control & custom messaging)

---

## ⚠️ Important Notes

### Firebase Phone OTP
- Requires Play Integrity access (automatic on Play Store)
- Debug device: Uses reCAPTCHA fallback (may ask user to solve puzzle)
- Production: Use release SHA fingerprints in Firebase Console

### Backend OTP
- You control the OTP generation
- You control the SMS provider
- More responsibility for reliability
- Can customize SMS text for your brand

---

## 🚀 Quick Start: Firebase SMS (Recommended)

1. Go to Firebase Console
2. Enable Phone provider + India region
3. Add SHA fingerprints
4. Run: `flutterfire configure --project=gmmxapp --yes`
5. Hot reload app
6. Test with your phone number

**That's it!** Real SMS will work instantly.

---

## 🔍 Troubleshooting SMS

### SMS not received
- ✅ Verify phone number format starts with +91
- ✅ Check SMS logs in Firebase Console
- ✅ Ensure India is enabled in SMS regions
- ✅ Verify SHA fingerprints are registered

### "Invalid Play Integrity Token"
- ✅ Only happens on debug device
- ✅ Firebase falls back to reCAPTCHA
- ✅ User may need to solve puzzle
- ✅ Works normally on Play Store app

### "Too many requests"
- ✅ Firebase rate-limits to prevent abuse
- ✅ Wait 15 seconds and retry
- ✅ Don't spam OTP requests

---

## 📝 SMS Provider Signup

**Twilio** (Best for India)
- [twilio.com](https://www.twilio.com)
- Free tier: $15 credits
- India SMS: ~$0.04 per SMS

**AWS SNS** (If using AWS)
- Included in AWS Free Tier
- Pay-as-you-go pricing
- Integration with AWS ecosystem

**OTP.io** (India-specific)
- Local India-based provider
- Competitive pricing
- Good local support

---

**Next Step:** Choose Firebase Phone OTP for quick testing, or set up Twilio for production SMS!
