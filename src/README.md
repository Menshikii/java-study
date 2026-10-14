# src 目录约定

每个主题一个独立包或模块，命名统一为 `wNN-主题名`，例如：

```
src/
├── w01-basics/           # 语法速通练习（20 个小练习）
├── w02-oop-bank/         # 控制台银行/图书系统
├── w03-collections/      # 手写 ArrayList / HashMap
├── w04-concurrency/      # 手写线程池 + 并发实验
├── w05-jvm/              # 内存泄漏 / CPU 飙高 / 死锁 三个复现
├── w06-servlet-jdbc/     # 不用框架的 Web 应用
├── w07-mini-spring/      # 手写 IoC + AOP
└── project/              # W8 起的主力项目（ToB 后台）
```

规则：

- 每写一个主题，先在这里补上它的目录和一句话说明
- 每个主题目录下必须有 `notes.md`，用「面试问 → 我怎么答」的格式记录
- 能跑起来的才算完成，跑不起来的不算
