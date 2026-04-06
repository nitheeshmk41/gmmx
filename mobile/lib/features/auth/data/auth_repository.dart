import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../../core/config.dart';
import '../../../core/network.dart';

final authRepositoryProvider = Provider<AuthRepository>((ref) {
  return AuthRepository(ref.watch(dioProvider));
});

class AuthRepository {
  AuthRepository(this._dio);

  final Dio _dio;

  Future<String> requestOtp(String mobile) async {
    final response = await _dio.post(
      '/api/auth/mobile/otp',
      data: {'tenantSlug': AppConfig.tenantSlug, 'mobile': mobile},
    );
    return response.data['debugCode'] as String? ?? '';
  }

  Future<AuthUser> verifyOtp({required String mobile, required String code}) async {
    final response = await _dio.post(
      '/api/auth/mobile/verify',
      data: {'tenantSlug': AppConfig.tenantSlug, 'mobile': mobile, 'code': code},
    );
    final data = response.data as Map<String, dynamic>;
    return AuthUser(
      userId: data['userId'] as String,
      role: data['role'] as String,
      tenantSlug: data['tenantSlug'] as String,
    );
  }
}

class AuthUser {
  AuthUser({required this.userId, required this.role, required this.tenantSlug});

  final String userId;
  final String role;
  final String tenantSlug;
}
