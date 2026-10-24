# W01D1 实验：类型范围、强制转换与表达式的计算类型

> 前置：D1 的讲解在 10-16~10-17 已完成；2026-10-20 的「查缺补漏 1」把强制转换这一块补到预测通过（见 `notes.md` 同节）。
> 这份文档补写于 2026-10-20（D1 原定 10-16/17 手敲）。四个实验里，**实验一已经走到"预测核对完、等运行"**，实验二/三/四标「待做」，做完一批就把标题后的 `[ ]` 改成 `[x]`。
> 规矩不变：**手敲，不复制粘贴；先写预测，再运行；每个实验最后都要「弄坏它」。**

---

## 1. 实验目的与知识点对照

| 实验 | 要解决的问题 | 知识点 | 对应 notes |
|---|---|---|---|
| E01（核心） | 强制转换到底改了哪些位、结果怎么读出来；为什么 `long` 接不住 `int` 的乘法 | 8 种基本类型范围、补码、窄化转换、表达式计算类型 vs 赋值目标类型、溢出 | T3、查缺补漏 1 的 5 问 |
| E02 | 为什么 `100 == 100` 是 `true`，`1000 == 1000` 是 `false` | 包装类、自动装箱拆箱、`valueOf`、`IntegerCache`、拆箱 NPE、装箱的成本 | T8、T9、Q2、Q4 |
| E03 | 方法里改参数，调用方到底能不能看到 | 值传递、引用副本、别名与重新绑定 | T1、T2、T5、T6、T7、Q1 |
| E04 | 混算时谁提升成谁、字面量和精度 | 隐式/显式转换、`double` 误差、`char` 与 `int`、`L`/`f` 后缀、二元数值提升 | T4、Q5 |

今天结束时，你应该不看资料就能说出下面三句：

1. 窄化转换 = 截断高位 + 按目标类型的补码解释：`(byte)200` 是 `-56`，`(byte)130` 是 `-126`，`(byte)1300` 是 `20`。
2. Java 只有值传递：方法拿到的是副本，改元素可见，改引用不可见。
3. 装箱走 `Integer.valueOf`，`-128~127` 直接复用缓存对象；所以 `==` 的结果取决于数值落在不在这个范围。

---

## 2. 文件与运行方式

模块根目录：`C:\Users\23663\Documents\Codex\java-study\src\w01-basics`
源码目录：`src\main\java\w01\basics\`（包名统一 `w01.basics`）

| 实验 | 文件名 | 完整路径 | 本次动作 |
|---|---|---|---|
| E01 | `E01PrimitiveRange.java` | `...\src\main\java\w01\basics\E01PrimitiveRange.java` | 改：补打印 `b1/s1/b2` + 加 `130`/`1300` 两段 |
| E02 | `E02BoxUnbox.java` | `...\src\main\java\w01\basics\E02BoxUnbox.java` | **新建**（还不存在） |
| E03 | `E03ValuePass.java` | `...\src\main\java\w01\basics\E03ValuePass.java` | 改：四个方法接进 `main` |
| E04 | `E04CastDemo.java` | `...\src\main\java\w01\basics\E04CastDemo.java` | 不动逻辑，只补一张预测表 |

运行命令（PowerShell，在模块根目录下）：

```powershell
cd C:\Users\23663\Documents\Codex\java-study\src\w01-basics
chcp 65001                                  # 中文乱码时先切 UTF-8

mvn -q compile                              # 每改完一个类就编译一次
java -cp target\classes w01.basics.E01PrimitiveRange
java -cp target\classes w01.basics.E02BoxUnbox
java -cp target\classes w01.basics.E03ValuePass
java -cp target\classes w01.basics.E04CastDemo

javap -c -p -cp target\classes w01.basics.E01PrimitiveRange   # 看窄化转换的字节码
javap -c -p -cp target\classes w01.basics.E02BoxUnbox         # 看装箱 / 拆箱的调用点
```

> IDEA 里右键 `main` → Run 更省事；命令行再跑一遍，是为了证明「换台机器也能跑」。

---

## 3. 本日第一次出现的类 / API（先读这段，再写代码）

> 引入规则：先知道它解决什么问题，再知道它在哪个包、怎么 `import`，最后才用。

| 名称 | 来自哪个包 | 为什么需要它 | 怎么引入 | 在本实验里用在哪 | 和已学概念的关系 |
|---|---|---|---|---|---|
| `Integer` / `Long` | `java.lang` | 基本类型不是对象，装不进泛型和集合（`List<int>` 不合法）；需要「对象版 int」时用它 | 不用 import（`java.lang` 自动可见） | E02 全部 | 有了它才有"装箱"，也才有 `==` 比引用的坑 |
| `Integer.valueOf(int)` | `java.lang` | 编译器的自动装箱会插这个方法；它内部先查缓存再决定要不要 `new` | 不用 import | E02 step1、`javap` 对照 | 这就是 T8「1000 不等」的根源 |
| `Integer.intValue()` | `java.lang` | 自动拆箱时插的方法，把对象里的 `value` 取出来 | 不用 import | E02 step3（`null` 拆箱） | `Integer n = null; int x = n;` 就是在这条指令上炸 |
| `IntegerCache` | `java.lang.Integer` 的内部类 | `-128~127` 的对象只造一次，避免高频小整数反复 `new` | 不需要直接用，只用 `-128~127` 这个边界 | E02 step2 的边界实验 | 用 `javap` 看不到它，但 `valueOf` 源码里就是查它的数组 |
| `System.nanoTime()` | `java.lang` | `currentTimeMillis` 精度只有毫秒，测微秒级差异不够用 | 不用 import | E02 计时对比 | 对应「装箱在循环里有多贵」这个性能坑 |

---

## 4. 实验一 `[ ]`（核心）：E01 强制转换闭环

### 目的

把「窄化转换」从纸面预测变成看得见的输出，并回答三个问题：

- 转换后到底是哪几位活下来了？
- 为什么 `(byte)200` 不是 `200`、也不是 `-200`，而是 `-56`？
- 为什么 `long wrong = 2147483647 * 2` 得到 `-2`，改成 `2147483647L * 2` 就对了？

### 现状（为什么这一节必须做）

`E01PrimitiveRange.java` 第 48–61 行现在是：

```java
int i1 = 200;
byte b1 = (byte) i1;
System.out.println(i1);      // ← 打印的是 i1，不是 b1

int i2 = 65535;
short s1 = (short) i2;
System.out.println(i2);      // ← 同上

int i3 = 257;
byte b2 = (byte) i3;
System.out.println(i3);      // ← 同上
```

`b1`、`s1`、`b2` 声明了却一次都没被读过，所以「转换结果」至今没有被观察过。这就是补漏 1 要闭环的第一个洞。

### 步骤与骨架

**step1** 把上面三处打印改成「源变量 + 转换结果一起打」，并在每行上方写预测：

```java
int i1 = 200;
byte b1 = (byte) i1;
// 预测：i1 = ____，b1 = ____
System.out.println("i1=" + i1 + ", b1=" + b1);
```

`i2/s1`、`i3/b2` 照同样格式写。

**step2** 在同一个 `main` 里追加 130 与 1300 两段（这两段是「弄坏它」的前置，先当正式实验写）：

```java
int source = 130;
byte target = (byte) source;
// 预测：source = ____，target = ____
System.out.println("source=" + source + ", target=" + target);

int big = 1300;
byte small = (byte) big;
// 预测：big = ____，small = ____（提示：1300 的十六进制是多少？）
System.out.println("big=" + big + ", small=" + small);
```

**step3** 先填下面的预测表，再编译运行；不一致的行用红笔（或 `// 差在：`）标出。

### 预测表（先填，再运行）

| 表达式 | 我的预测 | 实际输出 | 一致？差在哪 |
|---|---|---|---|
| `(byte)200` | | | |
| `(short)65535` | | | |
| `(byte)257` | | | |
| `(byte)130` | | | |
| `(byte)1300` | | | |
| `(byte)256`（对照，可加一行） | | | |

### 预期输出（对答案用）

```
i1=200, b1=-56
i2=65535, s1=-1
i3=257, b2=1
source=130, target=-126
big=1300, small=20
```

自己算的捷径（用来复核，不是让你背）：把源值取低 8 位得到 `v`（`v = 源值 mod 256`，`0..255`）；`v <= 127` 就是 `v`，`v >= 128` 就是 `v - 256`。
`1300 = 0x0514`，低 8 位是 `0x14 = 20`，所以 `20`。

### 看字节码

```powershell
javap -c -p -cp target\classes w01.basics.E01PrimitiveRange
```

在输出里找这两条指令：

- `i2b` —— int 转 byte
- `i2s` —— int 转 short

要观察的现象：`b1 = (byte) i1` 这一句一定先 `iload`（把 int 压栈）、再 `i2b`（截断）、最后 `istore` 进 `b1`。**转换是字节码里一条显式指令，不是运行时自动发生的**，这也是"窄化必须写强制转换、而放宽可以隐式"的机器层原因。

### 弄坏它

1. **删掉强制转换**：把 `byte b1 = (byte) i1;` 改成 `byte b1 = i1;`。预期编译失败，错误里有 `possible lossy conversion from int to byte`。把错误原文抄进记录。
2. **换个值**：`(byte)257` 改 `(byte)256`，预测并验证是 `0`（低 8 位全 0）。
3. **加一个反直觉的**：`(byte)1300` 如果是 `1300 - 1024` 也等于 `276`，说明它既不是"减 1024"也不是"减 256 直到范围"，而是**只看低 8 位**。
4. **溢出方向**：把 `2147483647 * 2` 改成 `2147483647L * 2`，对比两个输出。

### 记录

把预测表填满，并在表下面补一句结论：**窄化转换先截断高位，再按目标类型的补码读位模式；赋给 `long` 不会把右边的乘法变宽，它只改变"结果存进哪里"。**

---

## 5. 实验二 `[ ]`（待做）：E03 值传递接线

### 目的

`E03ValuePass.java` 里 `changePrimitive`、`changeArray`、`rebindArray`、`changeString` 四个方法都写好了，但 `main` 一次都没调用（第 21–44 行）。值传递这条主线还没闭环，`notes.md` 里 T7 那段"new 了新的相当于重新分配内存？不知道对不对"也没被验证。

### 步骤与骨架

在 `main` 里依次调用，每次调用前后各打一行：

```java
int x = 1;
System.out.println("x 调用前=" + x);
changePrimitive(x);
System.out.println("x 调用后=" + x);     // 预测：____

int[] arr = {1, 2};
System.out.println("arr[0] 调用前=" + arr[0]);
changeArray(arr);
System.out.println("arr[0] 调用后=" + arr[0]);   // 预测：____

rebindArray(arr);
System.out.println("arr[0] rebind 后=" + arr[0]); // 预测：____

String s = "abc";
System.out.println("s 调用前=" + s);
changeString(s);
System.out.println("s 调用后=" + s);      // 预测：____
```

### 预期输出（对答案用）

```
x 调用前=1
x 调用后=1
arr[0] 调用前=1
arr[0] 调用后=100
arr[0] rebind 后=100
s 调用前=abc
s 调用后=abc
```

四行结论：基本类型改不动；数组元素能改（引用副本指向同一对象）；`rebindArray` 里 `b = new int[]{100}` 只改副本指向，调用方看不到；`text += 'd'` 让方法内的 `text` 指向了新字符串，调用方也看不到。

### 弄坏它

把 `rebindArray` 的 `b = new int[]{100};` 改成 `b[0] = 100;`，再跑一次——同一个方法名、几乎一样的代码，输出却变了。用这一组对比回答：「值传递」传的到底是值还是引用。

---

## 6. 实验三 `[ ]`（待做）：E02 装箱与缓存

### 目的

`E02BoxUnbox.java` 还不存在。它是 T8/Q2 的实物证据，也是后面性能优化里"无意识装箱"这个坑的第一次预演。

### 背景（三个必须分清的概念）

- **包装类**：`Integer` 是对象，能放进 `List`、能是 `null`；`int` 不能。
- **自动装箱**：写 `Integer a = 100;` 时编译器插一句 `Integer.valueOf(100)`；`int x = a;` 插 `a.intValue()`。
- **缓存**：`Integer.valueOf` 在 `-128~127` 直接返回缓存数组里的同一个对象，出了这个范围每次 `new` 一个。所以 `==`（比引用）在这个范围里"看起来像比值"。

### 步骤与骨架

```java
Integer a = 100;
Integer b = 100;
// 预测：a == b 是 ____，为什么？
System.out.println(a == b);

Integer c = 1000;
Integer d = 1000;
// 预测：c == d 是 ____
System.out.println(c == d);
System.out.println(c.equals(d));      // 预测：____

// 边界两侧
System.out.println(Integer.valueOf(127) == Integer.valueOf(127));   // 预测：____
System.out.println(Integer.valueOf(128) == Integer.valueOf(128));   // 预测：____
```

拆箱 NPE（用 try-catch 接住，把异常信息打成文本）：

```java
Integer n = null;
try {
    int x = n;          // 预测：炸在哪一步、异常信息长什么样
} catch (NullPointerException e) {
    System.out.println(e.getMessage());
    e.printStackTrace();
}
```

计时对比（`n` 用 `1_000_0000`，跑两次就知道数量级；README 的 1 亿次可以留到最后再试）：

```java
long t0 = System.nanoTime();
Long sumBoxed = 0L;
for (int i = 0; i < 10_000_000; i++) {
    sumBoxed += i;      // 每轮都在装箱
}
long t1 = System.nanoTime();

long sumPrimitive = 0L;
for (int i = 0; i < 10_000_000; i++) {
    sumPrimitive += i;
}
long t2 = System.nanoTime();

System.out.println("装箱用时 ms = " + (t1 - t0) / 1_000_000);
System.out.println("原生用时 ms = " + (t2 - t1) / 1_000_000);
```

### 预期输出（对答案用）

- `true`、`false`、`true`、`true`、`false`
- 拆箱 NPE 的 message 类似：`Cannot invoke "java.lang.Integer.intValue()" because "n" is null`（JDK 14+ 的 helpful NPE；JDK 8 只有一句 `null`）
- 计时：装箱那轮通常慢一个数量级，具体数字每台机器不同，记录你测到的就行

### 看字节码

`javap -c -p -cp target\classes w01.basics.E02BoxUnbox`，找：

- `invokestatic java/lang/Integer.valueOf:(I)Ljava/lang/Integer;` —— 装箱
- `invokevirtual java/lang/Integer.intValue:()I` —— 拆箱，NPE 就是它抛的

### 弄坏它

1. 把 `1000` 改成 `127`，`c == d` 变 `true`——说明"范围"才是分界，不是"数字大小感觉"。
2. 把 `Integer n = null;` 改成 `Integer n = 0;`，NPE 消失。
3. 循环量从 1000 万加到 1 亿，看装箱那版的耗时是线性涨还是有更差的表现（关联 W4 之后的性能问题）。

---

## 7. 实验四 `[ ]`（待做）：E04 转换与字面量

### 目的

`E04CastDemo.java` 10-17 已经写并跑过，但没留下"先预测再运行"的记录。这个实验只补一张表，不重写代码。

### 预测表（先填，再对照现有输出）

| 表达式 / 语句 | 我的预测 | 实际 | 一句话理由 |
|---|---|---|---|
| `0.1 + 0.2 == 0.3` | | | |
| `(int) 3.99` | | | |
| `(int) -3.99` | | | |
| `long big = 2147483647 * 2;` | | | |
| `'A' + 1` | | | |
| `(char) 66` | | | |
| `1 / 2` 与 `1.0 / 2` | | | |
| `"A" + 1` | | | |
| `float f = 1.0;` 能编译吗 | | | |

### 弄坏它

`short s = 1; s = s + 1;` 编译失败，而 `s += 1;` 通过——把两条错误/通过的原因各写一句（这条和 D2 小测 T1 是同一件事，别答成两个答案）。

---

## 8. 收尾：写回 notes、提交、日报

### 8.1 写回 notes.md

| 实验 | 写回哪 | 写什么 |
|---|---|---|
| E01 | 「查缺补漏 1」 | 实际输出三行 + `(byte)1300` 的解释 + 一条变体 |
| E02 | D1 的 Q2、Q4 | 缓存边界一句话 + NPE 的字节码位置 |
| E03 | D1 的 Q1、T7 那段 | 用 `rebindArray` 的输出订正"重新分配内存"的猜测 |
| E04 | D1 的 Q5 | `0.1+0.2` 的误差来源 + 工程里用 `BigDecimal`（W2 再展开） |

### 8.2 提交

```powershell
git add src/w01-basics/src/main/java/w01/basics
git commit -m "feat(w01): D1 回收——E01 强转闭环、E03 接线、E02 装箱"
```

只提交自己核对过的 `.java`；`AGENTS.md` 等别的对话的改动不要捎带。

### 8.3 自检清单

- [ ] `mvn -q compile` 全绿
- [ ] E01 能看到 `b1=-56`、`s1=-1`、`b2=1`、`target=-126`、`small=20`
- [ ] E03 四个方法都进了 `main`，输出和预期一致
- [ ] E02 的 `==` 五连、NPE、计时三块都跑过
- [ ] 每张预测表都先填后跑，差异有记录
- [ ] 每个实验至少做过一条「弄坏它」

### 8.4 时间盒

| 段 | 内容 | 预计 |
|---|---|---|
| 实验一 | 改 E01 + 跑 + `javap` + 变体 | 25 min |
| 实验二 | E03 接线 + 预测 | 20 min |
| 实验三 | E02 从零写 + 计时 | 40 min |
| 实验四 | E04 补表 | 15 min |
| 收尾 | 写回 notes + commit + 日报 3 句 | 15 min |

合计约 1 小时 55 分。今天只要求把**实验一**做完并跑出结果（补漏 1 的验收），实验二/三/四属于 D1 剩余项，做不完按日顺延。
