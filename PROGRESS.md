# 学习进度

> 每次对话开始先读这份文件；每周结束前更新它。
> 最后更新：2026-10-14

## 当前位置

| 项 | 内容 |
|---|---|
| 当前周 | **W0 —— 环境搭建**（进行中，环境已实测核查） |
| 当前阶段 | 阶段 0 |
| 本周开始日期 | 2026-10-13（周二）～ 2026-10-15（周四），共 3 天 |
| 本周主题 | 环境搭建：JDK 21、Maven、IDEA、Git/GitHub、WSL2/Docker、AI 工具、云服务器 |
| 本周产出物 | GitHub 仓库有提交，README 写明 16 周计划 |
| 本周状态 | 进行中：JDK 21 已装好（10-14 实测通过）；独立 Maven / HelloWorld / GitHub remote / WSL2 待完成 |
| 下周起始 | W1 = 2026-10-16（周五）～ 2026-10-22（周四），6 个学习日 + 1 个复盘日 |

## 环境实测底账（2026-10-14 复核）

| 项 | 实测结果 |
|---|---|
| Git | ✅ 2.55.0，全局 `user.name/email` 已配；仓库 `java-study` 在 `main` 上已有 1 个提交 |
| IntelliJ IDEA | ✅ 2026.2.3（`C:\Dev\IntelliJ IDEA 2026.2.3`），已启动过；⚠️ Ultimate 模块被禁用（等同社区版功能），未装任何第三方插件 |
| JDK | ✅ **Temurin 21.0.12.1**（`C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot`）；系统变量 `JAVA_HOME` 已配；PATH 第一条就是它的 `bin`；`javac` 可用。Oracle JRE 8 条目仍在 PATH 但已排在后面，不再抢占 |
| Maven | ❌ 仍只有 IDEA 内置的 3.9.16（`plugins\maven-plugin\lib\maven3`），未装独立版、`mvn` 不在 PATH；无 `~/.m2\settings.xml`（无镜像） |
| 远程仓库 | ❌ 无 `git remote`；`~/.ssh` 只有 `known_hosts`，**没有 SSH key** |
| WSL2 / Docker | ❌ 都没装（`wsl --status` 提示未安装 Linux 子系统） |
| 网络 | ✅ github.com、Maven Central 均可达（HTTP 200）；winget / curl / ssh / node 可用 |
| 练习代码 | ❌ `src/w01-basics/` 只有 pom/README/notes，尚无 `.java` 代码 |

## 里程碑进度

| 里程碑 | 目标周 | 状态 |
|---|---|---|
| M1 Java 基础扎实（独立写 500 行程序） | W2 | 未开始 |
| M2 集合源码能讲（白板画 HashMap 扩容） | W3 | 未开始 |
| M3 并发能讲（线程池参数、锁升级、AQS） | W4 | 未开始 |
| M4 JVM 会排查（现场定位内存/CPU 问题） | W5 | 未开始 |
| M5 懂 Web 底层（手写 Servlet 并讲清链路） | W6 | 未开始 |
| M6 懂 Spring 原理（手写 mini-Spring） | W8 | 未开始 |
| M7 项目核心可用（登录 + 多租户 + 业务） | W11 | 未开始 |
| M8 项目能上线（公网访问 + 部署文档） | W12 | 未开始 |
| M9 项目能深挖（连续讲 30 分钟） | W14 | 未开始 |
| M10 面试就绪（3 场模拟面试 + 投递） | W16 | 未开始 |

## 每周记录

| 周次 | 主题 | 产出物 | 完成度 | 未掌握 / 卡点 |
|---|---|---|---|---|
| W0 | 环境搭建（10-13 ~ 10-15） | 仓库 + 首个提交 + HelloWorld 上云 | 45% | JDK 21 已完成；缺独立 Maven、HelloWorld、GitHub remote、WSL2 |
| W1 | 语法速通（10-16 ~ 10-22） | 20 个小练习 | 0% | 待开始 |
| W2 | OOP + 异常 + 泛型 + 反射 | 控制台图书/银行系统 | 0% | 无 |
| W3 | 集合与源码 | 手写 ArrayList/HashMap | 0% | 无 |
| W4 | 并发编程 | 手写线程池 + 实验报告 | 0% | 无 |
| W5 | JVM | 排查报告 3 份 | 0% | 无 |
| W6 | Servlet + JDBC + Web 底层 | 无框架 Web 应用 | 0% | 无 |
| W7 | Spring 核心原理 | mini-Spring | 0% | 无 |
| W8 | Spring MVC + Boot + 项目启动 | 自定义 Starter + 项目骨架 | 0% | 无 |
| W9 | MyBatis + 权限模块 | 登录 + RBAC | 0% | 无 |
| W10 | 数据库深化 + 多租户 | SQL 调优报告 + 业务模块 | 0% | 无 |
| W11 | Redis + 测试 + 文档 | 缓存 + 接口文档 + 测试用例 | 0% | 无 |
| W12 | Linux + Docker + 上线 | 公网实例 + 部署文档 | 0% | 无 |
| W13 | 调优 + 项目收尾 | 压测对比数据 + 文档集 | 0% | 无 |
| W14 | 项目复盘 + 简历 + 八股一 | 简历初稿 + Hot 100 | 0% | 无 |
| W15 | 八股二 + 手写题 + 模拟面试 | 问答笔记 | 0% | 无 |
| W16 | 查漏补缺 + 投递 | 简历定稿 + 3 场模拟面试 | 0% | 无 |

## 未掌握清单（滚动更新）

> 每周把没搞懂的点追加进来，真正搞懂了才划掉。这张表是面试前最该看的清单。

- JDK vs JRE 的区别，以及 `JAVA_HOME` 到底被谁用（Maven 启动脚本、IDEA、CI 都读它）——2026-10-13 记，10-14 已用 `where.exe java` 实测验证顺序
- Windows 的 **系统 PATH 优先于用户 PATH**（`java` 被 Oracle JRE 8 抢占就是这个原因）——2026-10-13 记，10-14 实测修好
- `settings.xml` / `localRepository` / 镜像在企业内网和国内网络里的作用——2026-10-13 记

## 当前卡点

- 独立 Maven 未装、不在 PATH ⇒ 命令行跑不了 `mvn`，只有 IDEA 内部能构建
- 没有 Git remote + SSH key ⇒ W0 产出物（推到 GitHub）卡住
- `git config user.name` 目前是真实姓名+学号（`202400150073网安赵鹏翔`），若仓库设为 Public，每次提交都会公开暴露 ⇒ 建仓库前先决定是改昵称还是用 Private

## 下次开局第一件事

- 装独立 Maven（3.9.16，与 IDEA 内置版一致）+ 配 `settings.xml` 阿里云镜像 → IDEA 打开 `src/w01-basics` 手敲 HelloWorld 并跑通 → 配 SSH key 推到 GitHub → `wsl --install -d Ubuntu` 后重启
