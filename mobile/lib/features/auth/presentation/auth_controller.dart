import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../data/auth_repository.dart';

final authControllerProvider = StateNotifierProvider<AuthController, AsyncValue<AuthState>>((ref) {
  return AuthController(ref.watch(authRepositoryProvider));
});

class AuthController extends StateNotifier<AsyncValue<AuthState>> {
  AuthController(this._repository) : super(const AsyncData(AuthState()));

  final AuthRepository _repository;

  Future<void> requestOtp(String mobile) async {
    state = const AsyncLoading();
    try {
      final debugCode = await _repository.requestOtp(mobile);
      state = AsyncData(AuthState(mobile: mobile, debugCode: debugCode));
    } catch (e, st) {
      state = AsyncError(e, st);
    }
  }

  Future<AuthUser?> verifyOtp(String code) async {
    final current = state.asData?.value;
    if (current == null || current.mobile.isEmpty) {
      return null;
    }

    state = const AsyncLoading();
    try {
      final user = await _repository.verifyOtp(mobile: current.mobile, code: code);
      state = AsyncData(AuthState(mobile: current.mobile, debugCode: current.debugCode, user: user));
      return user;
    } catch (e, st) {
      state = AsyncError(e, st);
      return null;
    }
  }
}

class AuthState {
  const AuthState({this.mobile = '', this.debugCode = '', this.user});

  final String mobile;
  final String debugCode;
  final AuthUser? user;
}
