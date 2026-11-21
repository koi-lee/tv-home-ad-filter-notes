# 新手资料包

[在0.3测试版附件中下载 tv-home-ad-filter-starter-20261121-r2.zip](https://github.com/koi-lee/tv-home-ad-filter-notes/releases/tag/v0.3-tested)。

下载并解压后，用浏览器打开start-here.html。它是离线阅读入口，说明适配范围、准备材料、电视端操作和恢复步骤。三张原始实机设置图和两张经AI辅助遮盖网络地址的参考图均在包内，断网也能查看。

资料包包含本仓库的文档与源码快照、TvFilterHelper-0.3-tested.apk、baseline.yaml、tv-filter.yaml、LICENSE与SHA256SUMS.txt。版本依据见包内BUNDLE-INFO.txt。它不包含FlClash、JDK、Android SDK、签名密钥、原始抓包或私人日志。

这是资料整理与传输便利包，沿用0.3本机验证APK，没有增加兼容设备或新增播放验收。下载包后仍需核对设备适配、准备ADB与上游FlClash、完成本地权限及配置验收。电脑本地传输步骤见配图指南，手机或电视直接下载的网络兼容未新增验证。

Mac或Linux可以在解压目录运行以下命令，检查包内文件是否完整。

```sh
shasum -a 256 -c SHA256SUMS.txt
```

Windows可用Get-FileHash逐项核对；本文没有新增Windows端验收。若文件校验失败，停止安装并重新下载核对。

GitHub预发布页可能不会显示“Latest”标记，请使用上面的具体版本链接，查看Assets附件。ZIP为固定日期快照，后续文档更新以仓库main及新版资料包为准。

2026年11月21日r2资料版同步SEO/GEO文档入口、常见问题、英文摘要与引用信息，并修正Agent说明中过期的“无预编译APK”表述。助手APK和过滤配置未变；原ZIP保留为历史快照。
