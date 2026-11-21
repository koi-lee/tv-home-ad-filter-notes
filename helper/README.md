# 创维本机启动助手源码 0.3

本目录包含原实验助手0.3的Java源码与AndroidManifest.xml，按字节保留历史已安装实现。此次只改写构建入口以支持外部JDK与Android SDK。未复制原签名密钥、APK或私有日志。

适用记录为创维75V58F Pro、酷开10.01.260911、原酷喵插件链路。助手会尝试写入四项播放器属性，开启本地过滤器并返回指定原首页。它没有自动机型门槛或完整规则健康检查，安装前由操作者确认适配条件。其他设备须先评估，不能直接试装后当作兼容。

## 构建

需要JDK17、包含aapt2/zipalign/d8/apksigner的Android构建工具，以及Android API28的android.jar。源码minSdk26、targetSdk28。构建脚本适用于具备zip的macOS或Linux。Windows使用等价环境或自行移植，本轮未测。

从仓库根目录执行。先把以下三个路径替换为本机实际目录，再运行。

```sh
export TV_JAVA_DIR='/path/to/jdk-17'
export TV_ANDROID_JAR='/path/to/android-sdk/platforms/android-28/android.jar'
export TV_BUILD_TOOLS='/path/to/android-sdk/build-tools/35.0.0'
bash helper/build.sh
```

成功时输出helper/build/TvFilterHelper.apk，并通过apksigner验证。缺工具会在编译前停止。所有构建输出、临时目录及local-signing均被Git忽略。

首次构建在local-signing生成新的本地测试签名，密码固定为android，不能作为正式分发签名使用。同一用户后续覆盖更新须保留该密钥；不要上传它。新签名无法覆盖原作者电视中不同签名的同包名助手，遇到INSTALL_FAILED_UPDATE_INCOMPATIBLE须先制定原助手停用、备份及恢复方案，不能直接清除数据或卸载来绕过错误。

当前仓库尚未授予开源许可证，源码可供阅读与本人验证。再分发或产品化前需确定授权。

## 安装与启用

遵循[Agent流程](../docs/agent-workflow.md)先验证直连、规则和播放器适配，并记录四属性原值。先确认已授权电视地址和现有助手版本。安装和打断观看需用户授权。

```sh
TV_SERIAL='TV_IP:5555'
adb -s "$TV_SERIAL" install -r helper/build/TvFilterHelper.apk
adb -s "$TV_SERIAL" shell am start -n local.skyworth.filterhelper/.MainActivity
```

安装后默认开机开关关闭。由用户在电视上开启助手的“开机自动恢复过滤并返回首页”，点击“测试启动并返回首页”，并在厂商设置中授予后台自启动及保持后台运行。FlClash的VPN首次授权仍需本人确认。停用其他启动工具，避免重复拉起。

界面沿用历史文字，其中“不修改播放器数据”指未改应用持久化数据；当前0.3确实写入系统debug属性，以架构文档和源码为准。屏幕“最多60秒”描述VPN等待流程，不能当作开播耗时承诺。

## 失败行为与恢复

属性写入失败记录警告，可能已写入前面几项，VPN流程仍会继续。VPN检测只要求任意VPN具备INTERNET能力，不能保证目标过滤器与配置正确。失败通知不等于自动回滚。

关闭助手的开机开关后，停止过滤并恢复原配置与四属性。按原值恢复并重开播放器核对，随后按用户授权重启验收。检查开机功能停用，避免再次写入。原设备既有助手及测试签名没有作为公共回滚包提供。卸载与清数据需要单独确认。

构建通过只验证源码可打包、签名有效。新构建使用新密钥，此次没有安装到电视，不能用历史0.3真机结果替代该产物的安装与两轮真机验收。
