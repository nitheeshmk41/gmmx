import 'package:flutter/material.dart';

class QrAttendancePage extends StatelessWidget {
  const QrAttendancePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('QR Attendance')),
      body: const Center(
        child: Padding(
          padding: EdgeInsets.all(20),
          child: Text(
            'MVP placeholder: integrate scanner package in next step and post token to /api/attendance/scan',
            textAlign: TextAlign.center,
          ),
        ),
      ),
    );
  }
}
