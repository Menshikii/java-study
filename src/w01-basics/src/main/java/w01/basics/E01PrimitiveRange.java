package w01.basics;

public class E01PrimitiveRange {
    int a;
    boolean b;
    char c;

    public static void main(String[] args) {
        E01PrimitiveRange e = new E01PrimitiveRange();
        System.out.println(e.a);
        System.out.println(e.b);
        int codeX = e.c;
        System.out.println(codeX);

        /*int temp;
        System.out.println(temp);
        */

        // 1. 打印四种整数类型的 MIN_VALUE 和 MAX_VALUE
        // byte、short、int、long
        System.out.println(Byte.MAX_VALUE);
        System.out.println(Byte.MIN_VALUE);

        System.out.println(Short.MAX_VALUE);
        System.out.println(Short.MIN_VALUE);

        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);

        System.out.println(Long.MAX_VALUE);
        System.out.println(Long.MIN_VALUE);
        // 2. 预测并验证：
        int max = Integer.MAX_VALUE;
        int afterMax = max + 1;
        System.out.println(afterMax);
        // 3. 预测并验证：
        int min = Integer.MIN_VALUE;
        int afterMin = min - 1;
        System.out.println(afterMin);

        // 4. 预测并验证：
        long wrong = 2147483647 * 2;
        System.out.println(wrong);
        // 5. 预测并验证：
        long right = 2147483647L * 2;
        System.out.println(right);

        // 1. int 200 强转 byte 会得到什么？
        int i1 = 200;
        byte b1 = (byte) i1;
        System.out.println(b1);

        // 2. int 65535 强转 short 会得到什么？
        int i2 = 65535;
        short s1 = (short) i2;
        System.out.println(s1);

        // 3. int 257 强转 byte 会得到什么？
        int i3 = 257;
        byte b2 = (byte) i3;
        System.out.println(b2);
        // 4. 两个 byte 相加，结果是什么类型？
        byte x = 1;
        byte y = 2;
        int sum = x + y;
        byte error = (byte)(x + y);
        System.out.println(sum);
        // 5. 复合赋值
        byte z = 1;
        z += 1;
        System.out.println(z);
        // 6. char 与 int
        char letter = 'A';
        int code = letter;
        System.out.println(code);
        int code2 = 66;
        char letter2 = (char) code2;
        System.out.println(letter2);
    }
}
