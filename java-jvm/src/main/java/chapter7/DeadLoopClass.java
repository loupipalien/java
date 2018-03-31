package chapter7;

/**
 * @author : ltchen
 * @date : 2018/3/31
 * @desc : 多线程环境中加载一个类的 <clint>() 方法, 当 <clint>() 方法耗时很久, 就可能造成多个进程阻塞
 */
public class DeadLoopClass {

    static class DeadLoop {
        static {
            // 不加此 if, 编译器会提示 "Initializer does not complete normally", 并拒绝编译
            if (true) {
                System.out.println(Thread.currentThread() + " inti DeadLoopClass.");
                while (true) {}
            }
        }
    }

    public static void main(String[] args) {
        Runnable script = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + " start.");
                new DeadLoop();
                System.out.println(Thread.currentThread() + " end.");
            }
        };

        new Thread(script).start();
        new Thread(script).start();
    }
}
