# Agent 执行与验收流程

## 1. 确认现场

询问用户要解决的是片头、中插、暂停还是开机广告，以及是否坚持原首页看剧。记录一台电视的地址、型号、固件及一集免费测试内容。首次不要启动变更；先确定是否有人正在观看。

以下命令适用于已有ADB、且电视已完成本地调试授权的电脑终端。示例是POSIX shell，Windows需换等价语法。先把占位符替换为用户当前电视地址。ADB未开放时，让用户在自己的机型说明或可见设置中找正式入口，历史隐藏按键不能跨固件照搬。

```sh
TV_SERIAL='TV_IP:5555'
adb connect "$TV_SERIAL"
adb -s "$TV_SERIAL" get-state
adb -s "$TV_SERIAL" shell getprop ro.product.model
adb -s "$TV_SERIAL" shell getprop ro.build.display.id
adb -s "$TV_SERIAL" shell getprop ro.build.version.sdk
adb -s "$TV_SERIAL" shell getprop ro.product.cpu.abi
adb -s "$TV_SERIAL" shell pidof com.follow.clash
adb -s "$TV_SERIAL" shell getprop debug.ottsdk.config
adb -s "$TV_SERIAL" shell getprop debug.ottsdk.ups_ad
adb -s "$TV_SERIAL" shell getprop debug.disable.screen.info
adb -s "$TV_SERIAL" shell getprop debug.ottad.mid.forbiddentime
```

get-state预期为device。unauthorized须用户本地确认，offline或连接失败先核对网络与IP。pidof为空可表示过滤器未运行，也可能尚未安装。属性输出为空须记录为空，不能自行补成false或0。

让用户从原首页打开指定节目，再只读检查前台组件与活动播放器。可在本地查看dumpsys activity activities定位mResumedActivity；该输出可能含私有参数，分享前只保留组件名。确认包名后，用dumpsys package查看版本，区分系统备份版本与活动版本。首页版权标识不足以判断实际请求属于哪个插件。

## 2. 选择适配分支

| 当前证据 | 后续动作 |
| --- | --- |
| 与本文机型、固件、酷喵插件链路匹配 | 完成原入口基线后，评估候选规则与属性实验 |
| 同品牌但固件或播放器不同 | 先观察实际广告请求，参数保持原值 |
| 其他Android电视 | 先验证安装、本地VPN与后台能力，再分析对应播放器 |
| 非Android或无本地VPN能力 | 当前仓库无直接部署实现，说明平台缺口 |

本仓库历史实测使用FlClash v0.9.3 ARMv7。下载从[上游发行页](https://github.com/chen08209/FlClash/releases)开始，按实际ABI选择并核验上游校验资料。安装命令需指向用户本地核验过的APK，不能把文档版本号当下载完成。新版本支持情况须查对应上游资料。

## 3. 先建立能恢复的直连基线

经授权安装过滤器，用户本地完成VPN授权。在FlClash中导入[baseline.yaml](../examples/baseline.yaml)，正常启动本地VPN，回原首页播放指定免费集。记录首帧、广告与正片，保存原DNS与原有配置。

配置YAML里的mixed-port只定义本地监听端口。流量是否进入过滤器要看客户端运行状态、系统VPN和目标连接记录。导入后仍需检查客户端覆盖设置。没有正常直连基线时，先处理联网问题。

## 4. 单变量验证

导入[tv-filter.yaml](../examples/tv-filter.yaml)，从相同原入口重进同一集。核对精确域名命中，同时观察正片恢复。等待超出原基线、持续黑屏或播放报错时恢复baseline配置再测。

若仍有广告，采集最短必要日志，在本地按触发时序识别广告Host。不要把含youku的所有域名都拒绝。共享接口与素材域名均可能影响正片或错误恢复，失败实验见[复盘](troubleshooting.md)。

只有确认本机插件支持对应读取路径且用户授权写入后，才按[架构文档](architecture.md)做属性实验。此处有意不提供跨机型一键写入脚本。Agent需先给出四项原值、拟改值、读取依据和回退方法，再执行授权内变更。写入失败可能留下部分状态，须逐项核对。

## 5. 开机恢复的当前缺口

仓库尚无助手源码、APK及独立构建入口。Agent可以依据架构实现，但这属于新增实现，必须完成源码审查、构建、安装授权及真机验收。缺少这些材料时，交付状态只能记录为手动开启过滤后验证通过，自动恢复仍待实现。

本机测试签名、私有工具目录与回滚包均未公开。不能假设他人拥有这些文件，也不能照文档安装不存在的包。需要自己实现时应使用自己的签名并保留兼容升级与回退策略。

## 6. 验收与回退

使用[验收记录模板](acceptance-template.md)。首轮覆盖同集开播、正片至少一分钟、暂停恢复、换集和已知中插点；次轮独立验证系统重启、遥控关开、实际断电恢复及电脑离线。断电需用户本地操作。每种操作单独记录，未测的写未验证。

失败先正常停止过滤、停用启动助手，恢复原配置、DNS和记录过的属性，重新打开原播放器核对。属性恢复后可能需重开播放器才重新读取；打断播放须符合用户当时授权。保留故障发生时间和最短脱敏摘要，避免靠清数据消除现场。

当前没有统一的跨平台日志解析器。少儿教育播放器等链路需独立匹配，不能沿用酷喵日志空结果判断无广告。完成报告以真实播放画面及用户结果为主，有限抽样和无对照样本注明其限制。
