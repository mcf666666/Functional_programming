package lambda;

public class LambdaTest {
    public static void main(String[] args) {

        //使用匿名内部类简化代码
        new Thread(new Runnable() {
            public void run() {
                System.out.println("xxxxxxx");
            }
        },"A").start();


        //使用Lambda表达式进一步简化匿名内部类的写法
        new Thread(()->{
            System.out.println("yyyyyyy");
        },"B").start();

    }
}
