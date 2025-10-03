import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Profile UI',
      theme: ThemeData(
        useMaterial3: true,
        scaffoldBackgroundColor: Colors.white,
      ),
      home: const ProfileScreen(),
    );
  }
}

class ProfileScreen extends StatelessWidget {
  const ProfileScreen({super.key});

  static const Color _accent = Color(0xFF24B38B); // xanh mint cho nút Edit

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: SafeArea(
        child: Stack(
          children: [
            // BACK
            Positioned(
              top: 12,
              left: 12,
              child: _SquareActionButton(
                onTap: () {
                  // TODO: Navigator.pop(context);
                },
                child: const Icon(
                  Icons.west_rounded,
                  size: 18,
                  color: Colors.black87,
                ),
              ),
            ),

            // EDIT 
            Positioned(
              top: 12,
              right: 12,
              child: _SquareActionButton(
                onTap: () {},
                child: const _EditGlyph(color: _accent),
              ),
            ),

            // Nội dung chính
            Center(
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  CircleAvatar(
                    radius: 60,
                    backgroundColor: const Color(0xFFEAE6FF),
                    backgroundImage:
                    const AssetImage('assets/images/anh1.png'),
                  ),
                  const SizedBox(height: 20),
                  const Text(
                    'Johan Smith',
                    style: TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.w700,
                      color: Colors.black,
                    ),
                  ),
                  const SizedBox(height: 6),
                  const Text(
                    'California, USA',
                    style: TextStyle(fontSize: 16, color: Colors.grey),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

/// Vỏ nút vuông bo góc + viền mảnh + bóng nhẹ
class _SquareActionButton extends StatelessWidget {
  final Widget child;
  final VoidCallback onTap;
  final double size;

  const _SquareActionButton({
    super.key,
    required this.child,
    required this.onTap,
    this.size = 40,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      width: size,
      height: size,
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(10),
        border: Border.all(color: const Color(0xFFE6E6E6)), // viền xám mảnh
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.05),
            blurRadius: 8,
            offset: const Offset(0, 2),
          ),
        ],
      ),
      child: Material(
        color: Colors.transparent,
        child: InkWell(
          borderRadius: BorderRadius.circular(10),
          onTap: onTap,
          child: Center(child: child),
        ),
      ),
    );
  }
}

/// Biểu tượng bút + khung vuông màu xanh (giống ảnh mẫu)
class _EditGlyph extends StatelessWidget {
  final Color color;
  const _EditGlyph({super.key, required this.color});

  @override
  Widget build(BuildContext context) {
    return Stack(
      alignment: Alignment.center,
      children: [
        // Khung vuông xanh
        Container(
          width: 18,
          height: 18,
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(4),
            border: Border.all(color: color, width: 1.8),
          ),
        ),
        // Cây bút chéo
        Transform.translate(
          offset: const Offset(1.0, 0.5),
          child: Icon(Icons.edit_outlined, size: 16, color: color),
        ),
      ],
    );
  }
}
