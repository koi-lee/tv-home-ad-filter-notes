# GitHub SEO / GEO 维护记录

核对日期：2026年11月21日。适用于本仓库的公开文档与Release；本次不新增独立网站、不修改电视、不扩充兼容性结论。

## 谁会搜索，找到后做什么

目标读者是在电视原首页看剧、希望排查播放广告的用户，以及协助其操作的Agent。主要路径是搜索或分享链接 → README适用性判断 → 新手指南 → 已校验资料 → 本地授权与设备验收。下载、Star、阅读和实际去广告成功是不同结果。

以下候选查询来自本项目真实提问，不是关键词工具的搜索量结论。

| 用户问题 | 主要承接位置 | 用户获得什么 |
| --- | --- | --- |
| 创维电视广告怎么关闭，保留原首页看剧 | README、新手指南 | 判断设备是否符合已测环境，找到配置入口 |
| AdGuard DNS为什么挡不住电视广告 | FAQ、技术架构 | 理解本例DNS实验失败与本机过滤的区别 |
| 电视去广告后等35秒、重启又有广告 | FAQ、架构、回归记录 | 区分超时、自启、权限与规则健康 |
| Skyworth TV ad filtering / FlClash Mihomo | English overview | 英文适用条件、限制及中文操作入口 |

## 已实施的仓库层措施

- README标题包含设备、任务与保留原首页的场景，首段直接回答方法和适用范围。
- About简介去掉过期的“待验收”，Topics描述真实技术与设备类别；不增加无关热门词。
- FAQ回答本项目真实问题，并链接已有架构、回退与验证记录；不另造多份结果事实源。
- 英文摘要保留单设备、权限和会员访问边界，避免翻译扩大效果。
- CITATION.cff提供维护者、项目地址、许可证与引用说明；引用结果时仍须带型号、固件、日期和观察窗口。引用文件不保证被AI引用。
- AGENTS.md要求未来公开更新复核简介、标签、下载、事实边界、英文摘要和链接。离线资料包需注明对应提交。

## 官方依据与采用决定

1. [GitHub：使用Topics分类仓库](https://docs.github.com/en/repositories/managing-your-repositorys-settings-and-features/customizing-your-repository/classifying-your-repository-with-topics)。采用与项目相关的主题，帮助GitHub内按主题发现；不把主题数量当排名分数。
2. [Google：生成式AI搜索优化指南](https://developers.google.com/search/docs/fundamentals/ai-optimization-guide)。采用清楚、可靠、可读、可引用的事实和内链；不声称有通用GEO标签或特殊Schema能保证推荐。
3. [GitHub：关于CITATION文件](https://docs.github.com/en/repositories/managing-your-repositorys-settings-and-features/customizing-your-repository/about-citation-files)。用于明确如何引用项目，不伪造论文、DOI、评价或独立背书。

仓库页面由GitHub托管。本轮没有GitHub Pages站点，因此不在代码根目录放无实际站点控制作用的robots.txt、sitemap.xml、canonical或JSON-LD。已有AGENTS和文档导航能满足本项目Agent接续，不把llms.txt当作搜索收录要求。后续只有独立站确有需求时再评估站点层配置。

## 验收与效果边界

| 层次 | 本轮结果或状态 |
| --- | --- |
| 内容与结构 | 已检查主入口、FAQ、英文摘要、引用文件与验证记录的一致性 |
| GitHub公开配置 | 更新后用GitHub API回读简介、Topics、可见性及远端提交 |
| 文件可取得 | 推送后回读公开README；新版ZIP重新下载并校验全部文件 |
| 搜索引擎收录与排名 | 未验证；公开页面可取得不等于已索引，无排名或流量承诺 |
| AI搜索引用与准确性 | 未验证；没有本轮真实引用样本，不报告“GEO已生效” |
| 用户独立安装与兼容性 | 不新增结论，仍以验证范围为准 |

后续若观察搜索或AI回答，记录查询原文、平台、日期、页面/引用链接及是否准确保留限制；单次无结果不等于永久未收录。GitHub Search不是Google/Bing的收录证据。只有真实读者完成独立设备验收，才能补充新的适用样本。
