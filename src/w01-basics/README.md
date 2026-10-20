# w01-basics —— W1 语法速通（20 个小练习）

包名约定：每个练习一个类，统一放 `w01.basics` 包（`src/main/java/w01/basics/`）。
类名 = 练习编号对应的主题名，例如练习 1 写 `E01PrimitiveRange.java`。**每个类都要有 `main`，能单独跑。**

> 实验文档：当天讲解全部结束后，统一写入 `experiments/W01Dn/Experiment.md`（如 `experiments/W01D2/Experiment.md`），每天一份，不覆盖前一天的。

## 怎么跑

```powershell
cd src/w01-basics
mvn -q compile                     # 编译全部练习
java -cp target/classes w01.basics.E01PrimitiveRange     # 跑某一个
```

在 IDEA 里直接右键 `main` → Run 更省事；命令行跑是为了证明「换台机器也能跑」。

> Windows 终端中文乱码时：`chcp 65001` 切到 UTF-8。
> 想看字节码：`javap -c -p -cp target/classes w01.basics.E02BoxUnbox`

## 三条纪律

1. **手敲，不复制粘贴。** 抄一遍不如写错一遍。
2. **先预测再运行。** 每个练习里凡是打 `// 预测：` 的地方，先写下你猜的输出，再跑，不一致就记进 `notes.md`。
3. **弄坏它。** 每个练习末尾都有「弄坏它」的指令，不做等于没做。

---

## 练习清单

### D1 类型系统 / 值传递 / 装箱拆箱

**E01 PrimitiveRange** —— 8 种基本类型的位数、范围、默认值；打印 `Integer.MAX_VALUE`、`+1` 之后是什么；`byte`/`short` 强制转换的截断规则。
弄坏它：让一个 `int` 循环自增到溢出，观察它变成负数；把 `double` 转 `int` 看截断（不是四舍五入）。

**E02 BoxUnbox** —— 装箱与拆箱：`Integer.valueOf(100) == Integer.valueOf(100)` 是 `true`，换成 1000 是 `false`，先预测再验证；`new Integer` 已于 9 废弃，说明为什么。
弄坏它：写 `Integer n = null; int x = n;` 看 `NullPointerException` 的栈信息长什么样；用 `javap -c -p` 找出是哪条字节码抛的（`invokevirtual Integer.intValue`）。
弄坏它：循环里做 1 亿次 `Long sum = 0L; sum += i;` 计时，再改成 `long`，解释差距（这个坑将来在性能优化里会再现）。

**E03 ValuePass** —— 写 6 个方法分别传：基本类型、对象引用、数组、String、包装类、`List`，在方法里修改后观察调用方。分两轮写：第一轮先写预测，第二轮运行后对照。
结论必须能一句话说清：Java 只有值传递，传对象时传的是引用的副本。
弄坏它：在方法里对引用参数重新赋值 `list = new ArrayList<>()`，看调用方为什么没变。

**E04 CastDemo** —— 隐式/显式转换、`0.1 + 0.2 == 0.3`、`char` 与 `int` 互转、字面量后缀（`L`/`f`/`d`）、`long` 与 `int` 混合运算谁提升。
弄坏它：写 `float f = 1.0;` 看编译错误；写 `long big = 2147483647 * 2;` 看结果（先溢出再赋值）。

### D2 运算符 / 流程控制 / 调试器

**E05 OperatorDrill** —— `%` 遇负数、位运算 `& | ^ ~ << >> >>>`、短路 `&&`/`||`、三元、`+=` 的隐式转换。
弄坏它：把 `a & b` 写成 `a && b`（或反过来）看编译错误；`-1 >>> 1` 和 `-1 >> 1` 对比结果并解释。

**E06 ControlFlow** —— `if`/`switch`（传统 `case` 与箭头语法 `->`）、`for`/`while`/`do-while`、`break`/`continue`、带标签的 `break`。
弄坏它：把 `do-while` 的条件改成永远为真，看它和 `while` 的行为差异；给 `switch` 漏写 `default` 看会发生什么。

**E07 LoopPattern** —— 打印三角形 / 菱形、九九乘法表、FizzBuzz。
弄坏它：改内层循环边界，让图形错位，自己算出正确的边界表达式。

**E08 DebugPractice**（ROADMAP 硬要求）—— 故意写三个 bug：① 死循环 ② 数组越界 ③ 差一错误（off-by-one）。**只用调试器**定位：会设条件断点、看变量、Watches、Evaluate Expression、Drop Frame。
交付：把三段的「现象 / 断点设在哪 / 变量怎么变 / 根因」写进 `notes.md`。
弄坏它：在调试器里手改变量的值，让死循环提前退出。

### D3 数组 + 经典算法

**E09 ArrayBasics** —— 一维/二维数组的三种初始化方式、默认值、`length`、`args` 命令行参数、`Arrays.toString/sort/copyOf/fill/binarySearch`、数组的引用语义。
弄坏它：命令行传 5 个参数，故意越界读 `args[5]`；用 `Arrays.asList(intArray)` 看它返回什么（这是个经典坑）。

**E10 SortSearch** —— 手写冒泡排序、选择排序、二分查找（不许用 `Arrays.sort`），并打印每一趟结果。
弄坏它：把二分查找的 `while (low <= high)` 换成 `<`，找到那个会漏掉的目标值。

**E11 ArrayAlgo** —— 找最大值/次大值、不排序去重、原地反转、矩阵转置。
弄坏它：把矩阵转置写成 `int[][] b = a;`（只复制引用），看输出为什么没变。

**E12 TwoSum** —— 两数之和：暴力 O(n²) 版 + `HashMap` 版，打印两者耗时；再统计数组中每个数字出现次数（`HashMap` 计数）。
弄坏它：把数组造到 10 万元素，看两版耗时的差距怎么拉大。

### D4 方法与递归

**E13 OverloadDemo** —— 重载解析顺序实验：写 `f(int)` / `f(long)` / `f(Integer)` / `f(Object)` / `f(int...)`，用不同实参调用，先预测命中哪个再运行。
写清「重载是编译期静态绑定、重写是运行期动态绑定」，各配一个反例。
弄坏它：写两个只差参数顺序或只差返回值的方法，看哪个能编译、哪个不能。

**E14 Recursion** —— 阶乘、斐波那契（递归 vs 迭代，计时对比）、汉诺塔、二分查找递归版。
弄坏它：去掉递归的终止条件，实测 `StackOverflowError` 在多少层触发（顺便看看默认栈深度）。

**E15 MethodDesign** —— 把 E10–E12 的代码重构成小方法：单一职责、命名、参数校验、`void` vs 返回值取舍、可变参数 `int...` 的使用与陷阱。

### D5 String / StringBuilder

**E16 StringImmutable** —— 常量池：`"abc" == "abc"`、`new String("abc") == "abc"`、`intern()` 前后对比；不可变性实验。
弄坏它：循环 1 万次 `s += "x"` 计时，再用 `StringBuilder` 做同样的事，记录秒表数据（提到 10 万次再看差距）。

**E17 StringBuilderDemo** —— `StringBuilder` vs `StringBuffer`（线程安全 vs 快）的 API 演练：反转、插入、删除、替换；写一个「文本统计器」统计行数、字符数、词频 Top 5。
弄坏它：多线程同时 `append` 一个 `StringBuilder` 看结果错乱（这是 W4 的伏笔）。

**E18 StringParse** —— 手写解析 CSV 行 / 日志行：`split`、`substring`、`indexOf`、`valueOf`、`String.join`、`formatted()`；用 `try-catch` 兜住脏数据；正则入门 `matches` / `String.split(regex)` / `Pattern`。
弄坏它：喂进一行只有 2 个字段的脏数据，看 `ArrayIndexOutOfBoundsException`，再改成返回「解析失败」而程序不崩。

### D6 包 / 修饰符 / static / final + 综合

**E19 PackageAccess** —— 建 `w01.basics.a` / `w01.basics.b` 两个包：用真实编译错误验证 `public` / `protected` / 默认（包私有）/ `private` 的可见性边界（画一张 4×4 的表）；`static` 成员 vs 实例成员、`static` 初始化块执行顺序、`final` 修饰变量/方法/类、静态导入。
弄坏它：在另一个包里 `new` 一个只有包私有构造器的类，看报错；把 `static` 字段改成实例字段，看编译错误。

**E20 MiniTool** —— 综合小工具（二选一，150 行以上）：
① 命令行「文本统计分析器」：读文件 → 行数/词频/最长行/关键词搜索，含参数校验与友好报错；
② 「表达式计算器」入门：只支持 `+ - * /` 与括号，用栈实现（这一步在给 W3 集合做铺垫）。
要求：至少拆成 3 个类/方法、有输入校验、异常不裸抛到用户面前、README 写清怎么跑。

---

## 本周验收标准

1. `mvn -q compile` 全绿；20 个练习类都能单独跑出结果
2. `notes.md` ≥ 15 条「面试问 → 我怎么答」，必须覆盖：值传递、装箱缓存、`equals` vs `==`、String 不可变、重载 vs 重写
3. E08 的 3 个 bug 定位记录完整
4. 每日 1 道算法题，本周 ≥ 6 题（题解写进 `notes.md` 末尾或 `algo.md`）
5. 全部 push 到 GitHub

跑不起来的不算完成；「我看懂了」不算完成。
