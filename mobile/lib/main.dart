import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'features/auth/presentation/login_page.dart';

void main() {
  runApp(const ProviderScope(child: GmmxApp()));
}

class GmmxApp extends StatelessWidget {
  const GmmxApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'GMMX',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: const Color(0xFFFF5C73)),
        useMaterial3: true,
      ),
      darkTheme: ThemeData(
        brightness: Brightness.dark,
        colorScheme: ColorScheme.fromSeed(seedColor: const Color(0xFFFF5C73), brightness: Brightness.dark),
        useMaterial3: true,
      ),
      home: const LoginPage(),
    );
  }
}
