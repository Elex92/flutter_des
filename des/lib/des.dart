
import 'dart:async';

import 'package:flutter/services.dart';

class Des {

  static const MethodChannel _channel =
      const MethodChannel('des');

  /**
   * 初始化key
   */
  static Future<String> initKey(String key) async {
    Map map = Map();
    map["key"] = key;
    final String version = await _channel.invokeMethod('init',map);
    return version;
  }

  /**
   * 数据加密
   */
  static Future<String> encrypt(String data) async{
    Map map = Map();
    map["data"] = data;
    final String result = await _channel.invokeMethod('encrypt',map);

    return result;
  }

  /**
   * 数据界面
   */
  static Future<String> decrypt(String data) async{
    Map map = Map();
    map["data"] = data;
    final String result = await _channel.invokeMethod('decrypt',map);

    return result;
  }
}
