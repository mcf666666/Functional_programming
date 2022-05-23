package lambda;

import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;

public class LambdaTest02 {
    public static void main(String[] args) {

        //使用匿名内部类的形式实现IntBinaryOperator的operator方法
        int i = calculateNum(new IntBinaryOperator() {
            @Override
            public int applyAsInt(int left, int right) {
                return left * right;
            }
        });

        //光标在接口上，按下alt+enter
        int i2 = calculateNum((left, right) -> left * right);


        //***************************************************************************
        printNum(new IntPredicate() {
            @Override
            public boolean test(int value) {
                if (value<5){return true;}
                return false;
            }
        });

        System.out.println("**********************************");
        printNum(value -> {
            if (value>8){return true;}
            return false;
        });



    }

    public static int calculateNum(IntBinaryOperator operator){
        int a = 10;
        int b = 10;
        return operator.applyAsInt(a,b);
    }

    public static void printNum(IntPredicate predicate){
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        for (int i : arr) {
            if(predicate.test(i)){
                System.out.println(i);
            }
        }
    }
}
