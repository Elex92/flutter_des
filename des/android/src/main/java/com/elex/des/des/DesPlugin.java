package com.elex.des.des;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** DesPlugin */
public class DesPlugin implements FlutterPlugin, MethodCallHandler {

  private MethodChannel channel;
  private DesUtils desUtils = null;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "des");
    channel.setMethodCallHandler(this);
  }

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "des");
    channel.setMethodCallHandler(new DesPlugin());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("init")) {
      String key = call.argument("key").toString();
      desUtils = new DesUtils(key);
      result.success("");
    }else if (call.method.equals("encrypt")) {
      String data = call.argument("data").toString();
      String s  = "";
      if (desUtils==null){
        result.error("400","未初始化key","");
      }
      try {
        s =  desUtils.encrypt(data);
      } catch (Exception e) {
        e.printStackTrace();
      }
      result.success(s);
    }else if (call.method.equals("decrypt")) {
      String data = call.argument("data").toString();
      String s  = "";
      if (desUtils==null){
        result.error("400","未初始化key","");
      }
      try {
        s =  desUtils.decrypt(data);
      } catch (Exception e) {
        e.printStackTrace();
      }
      result.success(s);
    } else {
      result.notImplemented();
    }
  }


  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
