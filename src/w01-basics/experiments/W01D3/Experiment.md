# W01D3 实验：数组与经典算法

> 前置：D3 的讲解已经结束，结论在 `src/w01-basics/notes.md` 的「D3 数组」一节（课前小测、讲解总结、三句话、常见误区、答案）。
> 这份文档只做一件事：把讲解里的结论变成你手上**能跑、能弄坏、能讲清**的代码。
> 规矩不变：**手敲，不复制粘贴；先写预测，再运行；每个实验最后都要「弄坏它」。**
>
> 日期：2026-10-20 生成；**主控 10-20 重排后定于 10-22 执行**（10-20 下半场先回收 D1，不直接跳到 D3）。

---

## 1. 实验目的与知识点对照

| 实验 | 要解决的问题 | 知识点 | 对应 notes |
|---|---|---|---|
| E09 | 数组到底是什么、怎么创建、长度从哪来、引用语义怎么表现 | 一维/二维数组、三种初始化、默认值、`length`、`main` 的 `args`、`Arrays` 工具类、引用别名 | T1–T5、T7、Q9、Q10 |
| E10 | 排序和查找为什么必须自己写一遍 | 冒泡、选择、二分查找、每趟的不变式、边界条件 | T6、Q11 |
| E11 | 数组上的常见算法套路 | 最大值/次大值、不排序去重、原地反转、矩阵转置 | T4、Q9 |
| E12 | 为什么需要 `HashMap` | O(n²) vs O(n)、`HashMap` 计数、计时方法 | Q10（对照 `Arrays.asList` 的坑） |

今天结束时，你应该不看资料就能说出下面三句（notes 里已有，实验就是去验证它们）：

1. 数组变量是引用，数组对象定长；别名改元素可见，改引用不可见。
2. 数组不能原地扩容，扩容 = 新数组 + 复制 + 重定向引用。
3. 二分查找的前提是有序；闭区间用 `low <= high`，排除 `mid` 用 `mid ± 1`。

---

## 2. 文件与运行方式

模块根目录：`C:\Users\23663\Documents\Codex\java-study\src\w01-basics`
源码目录：`src\main\java\w01\basics\`（包名统一 `w01.basics`）

今天新建四个文件：

| 实验 | 文件名 | 完整路径 |
|---|---|---|
| E09 | `E09ArrayBasics.java` | `C:\Users\23663\Documents\Codex\java-study\src\w01-basics\src\main\java\w01\basics\E09ArrayBasics.java` |
| E10 | `E10SortSearch.java` | `C:\Users\23663\Documents\Codex\java-study\src\w01-basics\src\main\java\w01\basics\E10SortSearch.java` |
| E11 | `E11ArrayAlgo.java` | `C:\Users\23663\Documents\Codex\java-study\src\w01-basics\src\main\java\w01\basics\E11ArrayAlgo.java` |
| E12 | `E12TwoSum.java` | `C:\Users\23663\Documents\Codex\java-study\src\w01-basics\src\main\java\w01\basics\E12TwoSum.java` |

运行命令（PowerShell，在模块根目录下）：

```powershell
cd C:\Users\23663\Documents\Codex\java-study\src\w01-basics
chcp 65001                                  # 中文乱码时先切 UTF-8

mvn -q compile                              # 每写完一个类就编译一次

java -cp target\classes w01.basics.E09ArrayBasics a b c d e   # 带 5 个参数
java -cp target\classes w01.basics.E09ArrayBasics             # 不带参数（对照）
java -cp target\classes w01.basics.E10SortSearch
java -cp target\classes w01.basics.E11ArrayAlgo
java -cp target\classes w01.basics.E12TwoSum
```

> IDEA 里右键 `main` → Run 更省事；命令行跑一遍是为了证明「换台机器也能跑」，也是 E08 调试记录之外的第二重验证。

---

## 3. 今天第一次出现的包 / 类 / API（先读这段，再写代码）

> 引入规则：先知道它解决什么问题，再知道它在哪个包、怎么 `import`，最后才用。

| 名称 | 来自哪个包 | 为什么需要它 | 怎么引入 | 在本实验里用在哪 | 和已学概念的关系 |
|---|---|---|---|---|---|
| `Arrays` | `java.util` | 数组是对象，但没有暴露「排序 / 打印 / 复制 / 比较」的实例方法；JDK 用一个纯静态工具类补齐 | `import java.util.Arrays;` | E09 step4、E10/E11/E12 打印 | 数组只有 `length` 字段和 `Object` 那几个方法，功能缺口由工具类填 |
| `Arrays.toString` / `deepToString` | `java.util` | 直接 `println(array)` 只会打印 `[I@1b6d3586`，因为数组没重写 `toString` | 同上 | E09、E11 | 呼应 `Object.toString` 的默认实现：类名 + `@` + hashCode |
| `Arrays.sort` | `java.util` | 排序是二分查找的前置条件 | 同上 | E09 演示、E10 准备有序数组 | 基本类型走双轴快排，和后面 W3 的 `Collections.sort` 是两条线 |
| `Arrays.copyOf` | `java.util` | 数组不能原地变长，「扩容三步」的封装 | 同上 | E09 step4、E11 去重收尾 | 内部就是「新建 + `System.arraycopy`」，和 `ArrayList` 扩容同源 |
| `Arrays.fill` / `equals` / `binarySearch` | `java.util` | 填充、按内容比较、二分查找 | 同上 | E09 step4 | `equals` 是「按内容比」，和 `==` 按身份比相对 |
| `Arrays.asList` | `java.util` | 把数组包装成 `List`；同时是「泛型 + 可变参数」的经典坑 | 同上 | E09 弄坏它 | 和 W3 集合衔接，现在只需记住 `int[]` 会被当成一个元素 |
| `System.arraycopy` | `java.lang`（自动导入，不用写 import） | 内存块级复制，比手写 for 快；`copyOf` 的底层 | 无需 import | E09 step4b | 和「新数组 + 复制 + 重定向引用」是同一件事的两层封装 |
| `System.nanoTime` | `java.lang` | 计时；单调递增，不受系统时间调整影响 | 无需 import | E12 | 对比 `System.currentTimeMillis()`：精度粗、可能回拨 |
| `HashMap` / `Map` | `java.util` | O(1) 查找和计数，替代 O(n) 线性扫描 | `import java.util.HashMap;`<br>`import java.util.Map;` | E12 | **今天只学「怎么用」**；W3 会拆开讲数组 + 链表 + 红黑树、hash 扰动、扩容 |
| `Map.Entry` / `entrySet()` | `java.util`（`Map` 的嵌套类型） | 遍历键值对 | 随 `Map` 一起可用 | E12 打印词频 | 和 for-each 是同一套语法 |
| `Random` | `java.util` | 生成测试数据；固定种子保证每次数据一样，耗时才可比 | `import java.util.Random;` | E12 | 和 `Math.random()` 同源，但 `Math.random()` 不方便固定种子 |
| `Integer.MIN_VALUE` | `java.lang` | 当「不存在」的哨兵值 | 无需 import | E11 次大值 | E01 已经打印过 `Integer.MIN_VALUE`，这里第一次拿它当约定值 |

`Map` 的常用方法今天只用到四个：`put(k, v)`、`get(k)`、`containsKey(k)`、`entrySet()`。`getOrDefault(k, 默认值)` 是更短的写法，看到别慌，作用就是「没有就返回默认值」。

---

## 4. 实验一：E09 ArrayBasics

### 目的

把「数组是对象、变量存引用、长度定长」这句话，用五种可观察的现象钉死：初始化与默认值、二维数组、`args`、`Arrays` 工具类、别名语义。

### 步骤与骨架

```java
package w01.basics;

import java.util.Arrays;

public class E09ArrayBasics {

    public static void main(String[] args) {
        step1Init();
        step2TwoDim();
        step3Args(args);
        step4ArraysTools();
        step5Reference();
    }

    // ---------- 1. 三种初始化 + 默认值 ----------
    static void step1Init() {
        int[] a = new int[5];              // 动态初始化：长度 5，元素全 0
        int[] b = {3, 1, 4, 1, 5};         // 静态初始化（简写）
        int[] c = new int[]{2, 7, 1, 8};   // 静态初始化（完整写法）

        // 预测：a[0]、b[2]、c.length 各是多少？
        // TODO 打印 a、b、c 和 c.length（用 Arrays.toString）
        // 再写一行：System.out.println(a); 先猜它打印什么，再运行
    }

    // ---------- 2. 二维数组 ----------
    static void step2TwoDim() {
        int[][] m = new int[2][3];   // 规则二维：2 行 3 列
        int[][] n = {{1, 2}, {3}};   // 不规则：第二行长度 1
        int[][] k = new int[2][];    // 只分配外层，两行都还是 null

        // 预测：m.length、m[0].length、n[1].length 各是多少？
        // 预测：k[0] 是什么？直接读 k[0][0] 会发生什么？
        // TODO 分别用 Arrays.toString 和 Arrays.deepToString 打印 m 和 n，比较差异
        // TODO 打印 k[0]，再注释掉一行 k[0][0] 看异常
    }

    // ---------- 3. main 的 args ----------
    static void step3Args(String[] args) {
        // 预测：命令行传 5 个参数时，args.length 是多少？
        // TODO 打印 args.length
        // TODO 再打印 args[5]（先预测异常类型和信息）
    }

    // ---------- 4. Arrays 工具类 ----------
    static void step4ArraysTools() {
        int[] b = {3, 1, 4, 1, 5};

        // TODO a) 打印排序前的 b
        // TODO b) Arrays.sort(b)，再打印一次（注意：sort 是原地修改，返回 void）
        // TODO c) Arrays.copyOf(b, 3) 和 Arrays.copyOf(b, 7) 各打印一次，观察长出来的位置是几
        // TODO d) int[] d = new int[4]; Arrays.fill(d, 7); 打印 d
        // TODO e) 用 Arrays.equals 比较两个内容相同的新数组，再用 == 比较一次
        // TODO f) Arrays.binarySearch(b, 4) 和 Arrays.binarySearch(b, 2) 各打印一次
        //         预测：搜 2 返回负数，这个负数代表什么？

        // 4b. 对照 System.arraycopy：copyOf 的底层
        int[] src = {10, 20, 30, 40, 50};
        int[] dst = new int[5];
        // TODO 用 System.arraycopy(src, 1, dst, 0, 3)，打印 dst
        // 预测：dst 的前三个和后两个分别是什么？
    }

    // ---------- 5. 引用语义 ----------
    static void step5Reference() {
        int[] x = {1, 2, 3};
        int[] y = x;
        y[1] = 99;
        // 预测：x[1] 是多少？

        y = new int[]{7, 8};
        // 预测：x[1] 还是 99 吗？x == y 吗？Arrays.equals(x, y) 呢？

        // TODO 把上面四个预测都打印出来验证
    }
}
```

### 预期输出（对答案用）

| 观察点 | 预期 |
|---|---|
| `Arrays.toString(a)` | `[0, 0, 0, 0, 0]` |
| `Arrays.toString(b)` | `[3, 1, 4, 1, 5]` |
| `c.length` | `4` |
| `println(a)` 直接打印数组 | `[I@...`（一个地址样的东西，不是内容） |
| `m.length` / `m[0].length` | `2` / `3` |
| `n[1].length` | `1` |
| `k[0]` | `null` |
| `Arrays.toString(k)` | `[null, null]` |
| `Arrays.toString(m)` | `[[I@..., [I@...]` |
| `Arrays.deepToString(n)` | `[[1, 2], [3]]` |
| `args.length`（传 5 个） | `5` |
| `args[5]` | `ArrayIndexOutOfBoundsException: Index 5 out of bounds for length 5` |
| sort 后的 `b` | `[1, 1, 3, 4, 5]` |
| `Arrays.copyOf(b, 3)` | `[1, 1, 3]` |
| `Arrays.copyOf(b, 7)` | `[1, 1, 3, 4, 5, 0, 0]` |
| `Arrays.fill(d, 7)` 后的 `d` | `[7, 7, 7, 7]` |
| `Arrays.equals(new int[]{1,2}, new int[]{1,2})` | `true` |
| 上一行的 `==` 版本 | `false` |
| `Arrays.binarySearch(b, 4)` | `3` |
| `Arrays.binarySearch(b, 2)` | `-3`（`-(插入点) - 1`，插入点是 2） |
| `System.arraycopy` 后的 `dst` | `[20, 30, 40, 0, 0]` |
| `x[1]`（`y[1] = 99` 之后） | `99` |
| `x == y` | `false` |
| `Arrays.equals(x, y)` | `false`（长度都不同） |

### 弄坏它

1. **越界**：把 `args[5]` 改成 `args[args.length]`，不带参数再跑一次，异常信息里的 `length` 变成了多少？这说明下标合法范围是什么？
2. **`Arrays.asList` 的坑**：打印 `Arrays.asList(new int[]{1, 2, 3}).size()`，预期 `1`；把 `int[]` 换成 `Integer[]` 再打印一次，预期 `3`。用「可变参数 `T...` + 泛型」解释这两次结果为什么不同（notes Q10）。
3. **编译期拦截**：写 `int[] c = new int[4]{1, 2, 3, 4};`，看编译器的报错——为什么「既不写长度又写初值」才合法？

---

## 5. 实验二：E10 SortSearch

### 目的

手写冒泡、选择、二分，目的不是「会写排序」，而是能讲清：每趟结束后哪个区间已经有序、循环边界为什么是那个值、改一个符号会漏掉什么场景。

### 步骤与骨架

```java
package w01.basics;

import java.util.Arrays;

public class E10SortSearch {

    public static void main(String[] args) {
        System.out.println("== 冒泡 ==");
        bubbleSort(new int[]{5, 2, 9, 1, 5, 6});

        System.out.println("== 选择 ==");
        selectionSort(new int[]{5, 2, 9, 1, 5, 6});

        System.out.println("== 二分 ==");
        int[] sorted = {1, 2, 5, 5, 6, 9};   // 已经是升序
        // TODO 分别打印 binarySearch(sorted, 6)、binarySearch(sorted, 7)、binarySearch(sorted, 1)
        // 预测：搜 7 返回负数，是几？为什么是几？
    }

    // 每趟结束后打印：第 i 趟 + Arrays.toString(a)
    static void bubbleSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            // TODO 内层 j 从 0 到 n - 2 - i：比较 a[j] 与 a[j + 1]，逆序就交换
            // TODO 打印本趟结果
        }
    }

    static void selectionSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            // TODO 在 i+1 .. n-1 里找比 a[minIdx] 更小的下标，更新 minIdx
            // TODO 若 minIdx != i，交换 a[i] 与 a[minIdx]
            // TODO 打印本趟结果
        }
    }

    static int binarySearch(int[] a, int target) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {                    // 弄坏它：把 <= 改成 <
            int mid = low + (high - low) / 2;    // 为什么不是 (low + high) / 2？
            // TODO 三种情况：
            //   a[mid] == target → 返回 mid
            //   a[mid] <  target → low = ?
            //   a[mid] >  target → high = ?
        }
        return -1;
    }
}
```

### 预期输出（对答案用）

冒泡（每趟内层都跑满、不做提前退出）：

| 趟 | 结果 |
|---|---|
| 1 | `[2, 5, 1, 5, 6, 9]` |
| 2 | `[2, 1, 5, 5, 6, 9]` |
| 3 | `[1, 2, 5, 5, 6, 9]` |
| 4 | `[1, 2, 5, 5, 6, 9]` |
| 5 | `[1, 2, 5, 5, 6, 9]` |

> 如果你加了「本趟没发生交换就 break」的优化，会只打印 4 趟。两种都对，区别是你要能说出为什么第 4 趟之后可以停。

选择：

| 趟 | 结果 |
|---|---|
| 1 | `[1, 2, 9, 5, 5, 6]` |
| 2 | `[1, 2, 9, 5, 5, 6]` |
| 3 | `[1, 2, 5, 9, 5, 6]` |
| 4 | `[1, 2, 5, 5, 9, 6]` |
| 5 | `[1, 2, 5, 5, 6, 9]` |

二分（数组 `[1, 2, 5, 5, 6, 9]`）：

| 调用 | 预期 |
|---|---|
| `binarySearch(sorted, 6)` | `4` |
| `binarySearch(sorted, 7)` | `-1`（自己实现的约定：找不到返回 `-1`） |
| `binarySearch(sorted, 1)` | `0` |

> 顺带对比：JDK 的 `Arrays.binarySearch(sorted, 7)` 返回 `-6`，它用 `-(插入点) - 1` 把「没找到」和「找到了 0 号下标」区分开。两种约定都行，但要在方法注释里写清你用的是哪种。

### 弄坏它

1. **差一错误**：把 `low <= high` 改成 `low < high`，用 `{1, 3, 5, 7}` 搜 `7`，手动推演 `low`/`high` 的每一步，说明为什么返回 `-1`。（这一条同时是 E08 的第 3 个 bug 素材。）
2. **死循环**：把 `low = mid + 1` 写成 `low = mid`，用 `{1, 3}` 搜 `3`。用 IDEA 的调试器挂起，观察 `low`/`high` 不再变化——这是 E08 的第 1 个 bug 素材。
3. **溢出**：把 `mid` 写成 `(low + high) / 2`。数组长度到不了 `2^31`，但请说清「两个约 10 亿的数相加会变成负数」这个逻辑，这是面试原题。
4. **无序数组**：直接对 `{5, 2, 9, 1, 5, 6}` 调 `binarySearch(..., 9)`，看它返回什么。结论不是「返回错误下标」，而是「结果没有意义」。

---

## 6. 实验三：E11 ArrayAlgo

### 目的

把数组上的四类基本套路各写一遍：求最值（一次遍历维护两个变量）、去重（O(n²) 查重 + 收尾复制）、原地反转（双指针）、矩阵转置（行列互换）。这四个模式在后面刷题和写业务代码时反复出现。

### 步骤与骨架

```java
package w01.basics;

import java.util.Arrays;

public class E11ArrayAlgo {

    public static void main(String[] args) {
        int[] data = {3, 9, 9, 2, 7};

        // TODO 1) 打印 max(data) 和 secondMax(data)
        // TODO 2) 打印 dedupe(data) 的结果
        // TODO 3) int[] rev = {1, 2, 3, 4, 5}; 原地反转后打印
        // TODO 4) int[][] m = {{1, 2, 3}, {4, 5, 6}}; 转置后打印（deepToString）
    }

    // 约定：空数组抛 IllegalArgumentException
    static int max(int[] a) {
        // TODO
        return 0;
    }

    // 约定：次大值 = 严格小于最大值的最大值；不存在时返回 Integer.MIN_VALUE
    static int secondMax(int[] a) {
        // TODO 一次遍历，同时维护 max 和 second
        return 0;
    }

    // 不排序、不用集合；结果保持首次出现的顺序
    static int[] dedupe(int[] a) {
        // TODO 先分配和 a 等长的临时数组，内层线性查重
        // TODO 最后用 Arrays.copyOf(临时数组, 有效个数) 收尾
        return new int[0];
    }

    static void reverseInPlace(int[] a) {
        // TODO 双指针 i 从 0 往后、j 从末尾往前，i < j 时交换
    }

    static int[][] transpose(int[][] m) {
        // TODO 新数组的行数 = m[0].length，列数 = m.length
        return new int[0][0];
    }
}
```

### 预期输出（对答案用）

| 调用 | 预期 |
|---|---|
| `max({3, 9, 9, 2, 7})` | `9` |
| `secondMax({3, 9, 9, 2, 7})` | `7`（严格小于 9 的最大值） |
| `secondMax({5, 5})` | `Integer.MIN_VALUE` |
| `dedupe({3, 9, 9, 2, 7})` | `[3, 9, 2, 7]` |
| `reverseInPlace({1, 2, 3, 4, 5})` | `[5, 4, 3, 2, 1]` |
| `transpose({{1,2,3},{4,5,6}})` | `[[1, 4], [2, 5], [3, 6]]` |

### 弄坏它

1. **只复制引用**：把转置写成 `int[][] t = m;` 再改 `t[0][0] = 99`，打印 `m[0][0]` 和 `m == t`，解释为什么「转置没生效」。
2. **行列写反**：`new int[m.length][m[0].length]` 配 `t[j][i] = m[i][j]`，制造一次越界或错位，再把正确写法推出来。
3. **反转做两次**：把循环写成 `for (int i = 0; i < a.length; i++)` 里交换 `a[i]` 与 `a[a.length - 1 - i]`，看结果怎么变回原样。
4. **去重不收尾**：直接返回等长的临时数组，观察末尾多出来的 `0`，再用 `copyOf` 修掉——这就是「扩容三步」的逆向操作。

---

## 7. 实验四：E12 TwoSum

### 目的

用同一个问题跑出两个复杂度，亲手看到「O(n²) 和 O(n) 的差距随规模平方拉大」；同时第一次用 `HashMap` 做计数。今天不打开 `HashMap` 源码，W3 再拆。

### 步骤与骨架

```java
package w01.basics;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class E12TwoSum {

    public static void main(String[] args) {
        int[] small = {2, 7, 11, 15};
        // TODO 打印 twoSumBrute(small, 9) 和 twoSumHash(small, 9)，预期都是 [0, 1]
        // TODO 打印 countOccurrences({3, 9, 9, 2, 7}) 的结果

        bench(1_000);
        bench(5_000);
        bench(20_000);
        // 可选：bench(100_000)，先看下面「弄坏它」第 2 条的警告
    }

    // 暴力版：双重循环，O(n²)
    static int[] twoSumBrute(int[] nums, int target) {
        // TODO 找不到返回 new int[]{-1, -1}
        return new int[]{-1, -1};
    }

    // 哈希版：一次遍历，O(n)
    static int[] twoSumHash(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>();
        // TODO 遍历 nums：先查 target - nums[i] 是否已在 seen 里，再 put(nums[i], i)
        // 想一想：这两步的顺序能不能反？
        return new int[]{-1, -1};
    }

    // 计数：每个数字出现几次
    static Map<Integer, Integer> countOccurrences(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<>();
        // TODO 遍历，用 containsKey / get / put 完成计数
        return counts;
    }

    // 计时：固定种子，保证每次数据一样
    static void bench(int n) {
        int[] nums = randomArray(n, 42);
        int target = -1;   // 元素都 >= 0，-1 保证不存在，逼暴力版跑满 n²

        long t1 = System.nanoTime();
        int[] r1 = twoSumBrute(nums, target);
        long cost1 = System.nanoTime() - t1;

        long t2 = System.nanoTime();
        int[] r2 = twoSumHash(nums, target);
        long cost2 = System.nanoTime() - t2;

        // TODO 打印 n、两个耗时（毫秒，保留 1 位小数）、两个返回值
        // 预测：n 从 1000 到 20000（20 倍），暴力版耗时大约涨多少倍？哈希版呢？
    }

    static int[] randomArray(int n, long seed) {
        Random r = new Random(seed);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = r.nextInt(1000);   // 0 ~ 999
        }
        return a;
    }
}
```

### 预期结果

| 观察点 | 预期 |
|---|---|
| `twoSumBrute({2,7,11,15}, 9)` | `[0, 1]` |
| `twoSumHash({2,7,11,15}, 9)` | `[0, 1]` |
| `countOccurrences({3,9,9,2,7})` | `{2=1, 3=1, 7=1, 9=2}`（打印顺序不保证） |
| 1,000 档 | 暴力几毫秒；哈希 1 毫秒以内 |
| 5,000 档 | 暴力约 25 倍于 1,000 档（规模 5 倍 → 平方 25 倍） |
| 20,000 档 | 暴力约 400 倍于 1,000 档；哈希仍是毫秒级 |
| 100,000 档（可选） | 暴力秒级；哈希几十毫秒内 |

> 具体毫秒数取决于机器和 JIT 预热，**只看数量级和增长倍数**，不要对着小数点纠结。第一次调用偏慢是正常的。

### 弄坏它

1. **顺序反了**：把 `put` 放到查之前，用 `{2}` 搜 `4`，观察返回 `[0, 0]`——同一个元素被用了两次，这是 TwoSum 最经典的 bug。
2. **10 万档暴力版**：n = 100,000 时约 `5 × 10^9` 次比较。**别在 Debug 模式跑**；先在纸上预测耗时，再决定要不要真的跑。跑完把实测值和预测值对一下。
3. **接口 vs 实现**：把 `HashMap<Integer, Integer> seen` 改成 `Map<Integer, Integer> seen = new HashMap<>();`，说明为什么左边写接口、右边写实现（后面 Spring 里到处是这个写法）。
4. **负数取模**：`randomArray` 里换成 `r.nextInt()`（有正有负），再看哈希版是否还正确——顺便复习 D2 的 `%` 遇负数。

---

## 8. 收尾：把结果写回 notes.md

### 8.1 E08 调试记录（ROADMAP 本周硬要求）

D3 的三个「弄坏它」正好凑齐 E08 要的三个 bug。用 IDEA 调试器各走一遍，把下面三行填进 `notes.md` 的「调试记录（E08）」表：

| # | 现象 | 断点设在哪 / 类型 | 变量怎么变 | 根因 |
|---|---|---|---|---|
| 1 死循环 | 程序一直不结束 | 在 `while` 行设普通断点或条件断点 `low == high` | `low`/`high` 不再变化 | `low = mid` 没有排除 `mid`，区间不缩小 |
| 2 数组越界 | `ArrayIndexOutOfBoundsException` | 在 `a[i]` 行设断点，`Watches` 加 `i` 和 `a.length` | `i` 涨到 `a.length` 后循环还在进 | 循环条件写成 `i <= a.length`，合法下标是 `0 .. length - 1` |
| 3 差一错误 | 二分查不到最后一个元素 | 在 `return -1` 行设断点 | `low == high` 时循环已经退出 | `while (low < high)` 丢掉了只剩一个元素的区间 |

> 表格里「断点设在哪、变量怎么变」必须是你**亲眼看到**的，照着这份文档抄不算。

### 8.2 算法题

在 `notes.md` 的「算法题记录」里把 10-19、10-20 两行补上。今天推荐 LeetCode 704（二分查找），正好和 E10 对照；也可以自选一道简单题。

### 8.3 自检清单

- [ ] 四个类都能单独 `java -cp target\classes` 跑起来
- [ ] `mvn -q compile` 无错误
- [ ] 每个「预测」都在运行前写下了，不一致的地方记进 `notes.md`
- [ ] E09 的三个「弄坏它」各做了一次
- [ ] E10 的四个「弄坏它」各做了一次（至少前两个）
- [ ] E11 的转置「只复制引用」实验做了，并能一句话解释
- [ ] E12 的耗时表有三档数据，能说出「为什么是平方关系」
- [ ] E08 三行记录写进 `notes.md`
- [ ] 能白板默写二分查找（含 `low <= high` 和 `mid ± 1`）

### 8.4 时间盒

| 实验 | 预计 |
|---|---|
| E09 | 30 分钟 |
| E10 | 50 分钟 |
| E11 | 40 分钟 |
| E12 | 40 分钟 |
| 记录 + E08 三行 | 20 分钟 |
| 合计 | 约 3 小时 |

超时先停下写记录，没跑完的部分记进「卡点」，不要无限延长。
