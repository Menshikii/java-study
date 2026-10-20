package w01.basics;

import java.math.BigDecimal;

public class E04CastDemo {

    public static void main(String[] args) {

        // 实验 1：double 的近似计算
        // 打印 0.1 + 0.2
        // 判断它是否等于 0.3
        boolean b1 = 0.1 + 0.2 == 0.3;
        System.out.println(b1);

        // 实验 2：用字符串构造 BigDecimal
        // 计算 "0.1" + "0.2"
        // 和 "0.3" 比较
        BigDecimal x = new BigDecimal("0.1");
        BigDecimal y = new BigDecimal("0.2");
        BigDecimal z = new BigDecimal("0.3");
        System.out.println(z.compareTo(x.add(y)));
        // 实验 3：比较两种 BigDecimal 构造方式
        // new BigDecimal(0.1)
        // new BigDecimal("0.1")
        BigDecimal floatBuild = new BigDecimal("0.1");
        BigDecimal StringBuild = new BigDecimal(0.1);
        System.out.println(floatBuild);
        System.out.println(StringBuild);
        // 实验 4：scale 与 equals
        // new BigDecimal("1.0")
        // new BigDecimal("1.00")
        // 分别测试 equals 和 compareTo
        BigDecimal TwoScaleBuild = new BigDecimal("1.11");
        BigDecimal OneScaleBuild = new BigDecimal("1.1100");
        System.out.println(TwoScaleBuild.compareTo(OneScaleBuild));
        System.out.println(TwoScaleBuild.equals(OneScaleBuild));
        // 实验 5：double 转 int
        // 3.99 和 -3.99
        double d1 = 3.99f;
        double d2 = -3.99f;
        System.out.println((int)d1);
        System.out.println((int)d2);
        float f1 = (float)2.1;
    }
}