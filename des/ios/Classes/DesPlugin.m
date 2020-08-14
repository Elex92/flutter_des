#import "DesPlugin.h"
#import "FYDesHandle.h"
@interface DesPlugin()
@property(nonatomic,strong)FYDesHandle * desHandle;
@end
@implementation DesPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"des"
            binaryMessenger:[registrar messenger]];
  DesPlugin* instance = [[DesPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  if ([@"init" isEqualToString:call.method]) {
      NSString * key = call.arguments[@"key"];
      self.desHandle = [FYDesHandle initWithKey:key];
      
    result(@"初始化成功");
  }else if([@"encrypt" isEqualToString:call.method]){
      NSString * data = call.arguments[@"data"];
      if (!self.desHandle) {
           result(@"未初始化key");
      }
      result([self.desHandle encryptWithText:data]);
  }else if([@"decrypt" isEqualToString:call.method]){
      NSString * data = call.arguments[@"data"];
      if (!self.desHandle) {
           result(@"未初始化key");
      }
      result([self.desHandle decryptWithText:data]);
  }else {
    result(FlutterMethodNotImplemented);
  }
}

@end
