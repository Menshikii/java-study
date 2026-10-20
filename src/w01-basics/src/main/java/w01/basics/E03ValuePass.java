package w01.basics;

public class E03ValuePass {
    static void changePrimitive(int x) {
        x=100;
    }

    static void changeArray(int[] b) {
        b[0]=100;
    }

    static void rebindArray(int[] b) {
        // TODO：让 b 指向一个新数组
        b = new int[] {100};
    }

    static void changeString(String text) {
        // TODO：尝试通过拼接修改 text
        text += 'd';
    }
    public static void main(String[] args) {

        // 实验 1：创建数组 a 和引用副本 b
        // 输出 a == b
        int[] a = new int[]{1,9};
        int[] b = a;
        System.out.println(a==b);

        // 实验 2：通过 b 修改数组元素
        // 再通过 a 读取同一个元素
        b[0] = 9;
        System.out.println(a[0]);
        // 实验 3：让 b 指向一个新数组
        // 再比较 a 和 b
        b = new  int[]{3,8};
        System.out.println(a==b);
        // 实验 4：创建两个内容相同的数组
        // 比较它们的引用是否相同
        int[] c = new int[]{1,9};
        int[] d = new int[]{1,9};
        System.out.println(c==d);


    }
}