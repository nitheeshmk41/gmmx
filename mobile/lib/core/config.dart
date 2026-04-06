class AppConfig {
  static const apiBaseUrl = String.fromEnvironment('API_BASE_URL', defaultValue: 'http://10.0.2.2:8080');
  static const tenantSlug = String.fromEnvironment('TENANT_SLUG', defaultValue: 'coachmohan');
}
