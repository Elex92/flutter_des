import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:des/des.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _enString = 'Unknown';
  String _deString = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String enString;
    String deString;

    await Des.initKey("P&jFu6rzZ4");
    enString = await Des.encrypt("加密内容");
    deString = await Des.decrypt(enString);

    setState(() {
      _enString = enString;
      _deString = deString;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: [
              Text("$_enString"),
              Text("$_deString")
            ],
          ),
        ),
      ),
    );
  }
}
