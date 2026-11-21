# 技术架构

## 为什么公共DNS没有解决本例

把电视DNS设为AdGuard公共DNS后，同一目标集广告仍出现。后续本机抓包发现播放器会访问已知广告Host，静态IP规则也未解决。仅查看DNS命中无法证明实际媒体连接已被阻止。

最终实验使用FlClash承载Mihomo，在电视本机经用户授权的Android VPN捕获流量，用HTTP/TLS嗅探识别目标域名。parse-pure-ip开启，override-destination关闭，保留实际目的地址。其余连接按MATCH,DIRECT放行。

三个域名为yk-ssp-ad.cp31.ott.cibntv.net、valipl.cp31.ott.cibntv.net、adx-open-service.youku.com。规则随服务端可能变化，新增域名须同时验证广告机会和正片恢复。

## 播放器适配

助手0.3源码记录以下属性，每次启动时尝试重新设置。

| 属性 | 实验值 |
| --- | --- |
| debug.ottsdk.ups_ad | false |
| debug.disable.screen.info | true |
| debug.ottad.mid.forbiddentime | 86400 |
| debug.ottsdk.config | 1 |

这些属性来自本机酷喵插件分析与实验。属性名称不能代替语义证据，也不能推断其他播放器支持。mid.forbiddentime在本机读取路径中是递减保护计数，86400不能解释为已经通过24小时连续验收。普通应用能写入这些属性是本机固件的已观察行为，其他系统可能拒绝。失败时应停止该适配。

## 开机恢复

助手接收BOOT_COMPLETED，在前台服务内尝试写入属性、正常启动FlClash，观察具有INTERNET能力的VPN连续三次成立后回原创维首页，最多等待60秒。该VPN判断不能识别具体规则是否正确，存在其他VPN时可能误判。

记录权限为RECEIVE_BOOT_COMPLETED、ACCESS_NETWORK_STATE、FOREGROUND_SERVICE。助手还需厂商后台自启动及保持后台运行授权。VPN首次授权由用户在电视本地完成。助手无INTERNET权限，不承担上传或抓包。

原首页组件为com.tianci.movieplatform/com.coocaa.homepage.vast.HomePageActivity，仅用于本机定位。其他固件应重新核对组件，避免进入占位首页。
