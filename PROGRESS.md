# 学习进度

> 每次对话开始先读这份文件；每周结束前更新它。
> 最后更新：2026-10-16

## 当前位置

| 项 | 内容 |
|---|---|
| 当前周 | **W1 —— 语法速通**（进行中，D1） |
| 当前阶段 | 阶段 1 |
| 本周开始日期 | 2026-10-16（周五）～ 2026-10-22（周四）：6 个学习日 + 1 个复盘日 |
| 本周主题 | 语法速通：类型/值传递/装箱拆箱、运算符与流程控制、数组、方法重载、String/StringBuilder、包与访问修饰符、IDE 调试器 |
| 本周产出物 | `src/w01-basics/` 下 20 个可运行练习 + `notes.md` 问答笔记 |
| 本周状态 | D1 进行中：类型系统 / 值传递 / 装箱拆箱（练习 E01–E04） |
| W0 结论 | 2026-10-16 判定 **达标**（技术门槛实测全过，见下方底账）；顺延 3 项记账类任务：Docker Desktop、云服务器、AI 工具 |

## 环境实测底账（2026-10-16 复核）

| 项 | 实测结果 |
|---|---|
| Git | ✅ 2.55.0；`user.name = Menshikii`，邮箱用 GitHub noreply（不暴露真实姓名/学号）；remote = `git@github.com:Menshikii/java-study.git`；5 条提交已 push |
| IntelliJ IDEA | ✅ 2026.2.3；已在 `src/w01-basics` 打开项目并跑通；⚠️ Ultimate 模块被禁用（等同社区版功能），未装第三方插件 |
| JDK | ✅ Temurin **21.0.12.1**；系统变量 `JAVA_HOME` 已配；PATH 第一条是它；`javac 21.0.12.1` 可用。Oracle JRE 8 条目仍在 PATH 但已排在后面 |
| Maven | ✅ 独立版 **3.9.16**（`C:\Dev\tools\apache-maven-3.9.16`，与 IDEA 内置版同版本）；`mvn -v` 显示 `Java version: 21.0.12.1`；`~/.m2/settings.xml` 已配阿里云镜像（10-14 写入） |
| 远程仓库 | ✅ SSH key 已配置（10-14 生成）；`ssh -T git@github.com` 通过；仓库已推送并跟踪 `origin/main` |
| WSL2 / Docker | ✅ WSL2 + Ubuntu，`uname -a` = `microsoft-standard-WSL2`（内核 6.18.54.1）；❌ Docker Desktop 未装 |
| 网络 | ✅ github / Maven Central / 阿里云镜像（`maven.aliyun.com/repository/public` 返回 200）均可达 |
| 练习代码 | ✅ `src/w01-basics` 已跑通 HelloWorld（IDEA 与 `java -cp target\classes` 双验证）；W1 练习进度 0/20 |

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
| W0 | 环境搭建（10-13 ~ 10-15，实际收到 10-16） | 仓库 + 首个提交 + HelloWorld 推上 GitHub | 90% | 达标；顺延：Docker Desktop、云服务器注册、AI 工具（学生包） |
| W1 | 语法速通（10-16 ~ 10-22） | 20 个小练习 | 5% | D1 进行中（E01–E04） |
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

- JDK vs JRE 的区别，以及 `JAVA_HOME` 到底被谁用（Maven 启动脚本、IDEA、CI 都读它）——2026-10-13 记，10-16 待口述验收
- Windows 的 **系统 PATH 优先于用户 PATH**（`java` 被 Oracle JRE 8 抢占就是这个原因）——2026-10-13 记，10-14 实测修好，10-16 待口述验收
- `settings.xml` / `localRepository` / 镜像在企业内网和国内网络里的作用——2026-10-14 已亲手写 `settings.xml` 实践一次

## 当前卡点

- 无技术卡点（环境门槛已全部实测通过）
- 待办（不影响进 W1）：Docker Desktop 未装；云服务器未注册；AI 工具（GitHub Student Pack / Copilot）未申请

## 下次开局第一件事

- W1 D2：运算符与流程控制（E05–E07）+ **调试器专项**（E08：只用调试器定位死循环 / 数组越界 / 差一错误三个 bug）
