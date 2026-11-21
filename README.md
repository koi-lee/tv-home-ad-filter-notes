# 创维电视去广告实测：保留原首页与遥控器的播放广告过滤实践

保留家人原来的遥控器和电视首页，在自用创维电视上排查片头与中插广告，并验证重启后的恢复。

本文更新于2026年11月21日，包含11月16日至18日的排查记录，以及11月21日公开源码构建后的覆盖安装、两次系统重启、旧包覆盖回退和实际断电恢复验证。项目当前是单机实践文档，其他机型需要独立验证。

## 这份方法能解决什么？

本仓库记录创维75V58F Pro（酷开10.01.260911）原首页播放广告的单机排查方法：电视本机FlClash/Mihomo域名过滤、酷喵播放器参数适配和启动助手0.3配合使用。指定样本已记录无片头广告及重启恢复；不是换DNS就能复现的通用方案，也不解锁VIP内容。

**开始操作：** [先判断是否适用](docs/quick-start.md) → [查看安装配图](docs/illustrated-setup.md) → [下载测试版](https://github.com/koi-lee/tv-home-ad-filter-notes/releases/tag/v0.3-tested) → [按模板验收](docs/acceptance-template.md)。

查找“创维电视广告怎么关闭”“AdGuard DNS为什么挡不住电视广告”“重启后过滤怎么恢复”的读者，可先看[常见问题与直接答案](docs/faq.md)。AI与Agent应同时引用[验证范围](docs/validation.md)，不能省略机型、固件与观察窗口。

**English:** A single-device Skyworth TV ad-filtering case study using on-device FlClash/Mihomo, player-specific settings and a boot helper. Keeps the original TV home screen and remote. No VIP unlocking or universal compatibility claim. [English overview](docs/overview.en.md).

## 整包下载

[下载新手资料包](docs/offline-bundle.md)，解压后打开start-here.html，可离线阅读说明和配图，并取得助手APK与配置。它沿用本机0.3测试版，首次安装配置仍需协助。

## 第一次阅读

普通用户先看[新手指南](docs/quick-start.md)，了解适用范围、需要准备的信息、电视端操作与故障恢复。当前需要电脑辅助配置。可从[0.3测试版](https://github.com/koi-lee/tv-home-ad-filter-notes/releases/tag/v0.3-tested)下载助手APK，并参考[实机配图](docs/illustrated-setup.md)。该版本限定本机验证范围。

有Agent协助时，可以直接复制新手指南中的任务文本，让它先核对设备，再决定后续操作。

## 实测设备与结果

- 创维75V58F Pro，酷开10.01.260911，酷喵插件记录版本1000023。
- 原首页《人民的名义》《谷雨》等样本在指定窗口内播放正常，用户确认未见片头视频广告和广告倒计时。
- 曾出现约35秒加载，后续用户测得目标集约14至18秒进入正片。时间受节目、网络和服务端影响。
- 启动助手0.3已有系统重启及实际断电恢复记录。用户也曾关闭Mac Wi-Fi后确认电视正常播放。
- 六分类各取一个样本，观察到两种播放链路。有限样本不能代表全目录、所有平台或以后版本。

## 方案由哪些部分组成

电视本机的FlClash/Mihomo负责识别并拒绝指定广告域名，播放器适配参数处理本机酷喵SDK的广告获取与等待流程，启动助手负责开机恢复过滤并回到原首页。只导入域名配置不能复现全部结果。

## 交给自己的 Agent

先让 Agent 阅读 [AGENTS.md](AGENTS.md)，再按[执行与验收流程](docs/agent-workflow.md)采集设备基线。首次只做只读盘点，核对你家的具体型号、系统与真实播放器。当前仓库提供方案说明、基线与过滤配置，以及[启动助手源码和构建入口](helper/README.md)。可下载预编译测试APK，也可本地构建；安装及自动恢复仍需目标设备验收。

给Agent的可复制任务统一放在[新手指南](docs/quick-start.md#交给agent前准备四项信息)，同时列出你要准备的四项信息。

## 阅读顺序

1. [新手指南](docs/quick-start.md)
2. [技术架构与配置](docs/architecture.md)
3. [部署与回退](docs/setup.md)
4. [验证结果与覆盖边界](docs/validation.md)
5. [失败实验与定位方法](docs/troubleshooting.md)
6. [公开检查记录](docs/release-review.md)

[配置示例](examples/tv-filter.yaml)来自本机已记录配置。默认直连，仅对三个精确域名拒绝连接。配置包含可公开的服务域名，不含代理节点或账号信息。

仓库包含助手0.3源码与构建脚本。JDK、Android SDK与签名材料由使用者本地准备，已提供带SHA256的预编译测试包。新构建需要在目标电视完成安装、自启与回退验证。

广告过滤保留平台正常内容授权。会员专属内容仍按平台资格访问。开机广告曾另行联系客服处理，与本文播放广告实验分别记录。

## 引用与维护

维护者：[koi-lee](https://github.com/koi-lee)（Koi的AI转型手记）。引用本案例时请注明型号、系统、测试日期与[覆盖边界](docs/validation.md)，并通过GitHub提交永久链接固定所用版本。机器可读引用信息见[CITATION.cff](CITATION.cff)。

[搜索与AI可发现性维护记录](docs/discoverability.md)记录关键词承接、官方依据及未验证的收录/引用状态；文档可读、可下载不等于获得搜索排名或AI推荐。

## 许可证

本仓库源码、文档与配置示例采用[MIT许可证](LICENSE)。FlClash、Mihomo、Android SDK等第三方项目保持各自许可证。
