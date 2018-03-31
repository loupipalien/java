package chapter7;

/**
 * @author : ltchen
 * @date : 2018/3/31
 * @desc : 字段解析
 */
public class FieldResolution {

    interface Interface0 {
        int A = 0;
    }

    interface Interface1 extends Interface0 {
        int A = 1;
    }

    interface Interface2 {
        int A = 2;
    }

    static class Parent implements Interface1 {
        public static int A = 3;
    }

    static class Child extends Parent implements Interface2 {
        public static int A = 4;
    }

    public static void main(String[] args) {
        // 如果注释掉 Child 类中的 A 字段, 由于接口和父类同时存在这个字段 A, 编译器会提示 "The field A is ambiguous."
        System.out.println(Child.A);
    }

}
