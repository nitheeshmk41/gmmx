import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../home/presentation/home_page.dart';
import 'auth_controller.dart';

class LoginPage extends ConsumerStatefulWidget {
  const LoginPage({super.key});

  @override
  ConsumerState<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends ConsumerState<LoginPage> {
  final mobileController = TextEditingController();
  final otpController = TextEditingController();
  bool otpRequested = false;

  @override
  Widget build(BuildContext context) {
    final auth = ref.watch(authControllerProvider);

    ref.listen(authControllerProvider, (previous, next) {
      final user = next.asData?.value.user;
      if (user != null) {
        Navigator.of(context).pushReplacement(
          MaterialPageRoute(builder: (_) => HomePage(user: user)),
        );
      }
    });

    return Scaffold(
      appBar: AppBar(title: const Text('GMMX Login')),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            TextField(
              controller: mobileController,
              keyboardType: TextInputType.phone,
              decoration: const InputDecoration(labelText: 'Mobile Number'),
            ),
            const SizedBox(height: 12),
            if (otpRequested)
              TextField(
                controller: otpController,
                keyboardType: TextInputType.number,
                decoration: const InputDecoration(labelText: '6-digit OTP'),
              ),
            const SizedBox(height: 16),
            if (!otpRequested)
              FilledButton(
                onPressed: auth.isLoading
                    ? null
                    : () async {
                        await ref.read(authControllerProvider.notifier).requestOtp(mobileController.text.trim());
                        if (mounted) {
                          setState(() {
                            otpRequested = true;
                          });
                        }
                      },
                child: const Text('Send OTP'),
              )
            else
              FilledButton(
                onPressed: auth.isLoading
                    ? null
                    : () async {
                        await ref.read(authControllerProvider.notifier).verifyOtp(otpController.text.trim());
                      },
                child: const Text('Verify OTP'),
              ),
            const SizedBox(height: 8),
            Text('Debug OTP: ${auth.asData?.value.debugCode ?? '-'}'),
            if (auth.hasError) Text(auth.error.toString(), style: const TextStyle(color: Colors.red)),
          ],
        ),
      ),
    );
  }
}
