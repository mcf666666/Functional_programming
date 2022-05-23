package stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 将数组或集合转换成流对象
 */

public class CreateStream {
    public static void main(String[] args) {

        //1.将单列集合转换成流
        ArrayList<Object> objects = new ArrayList<>();
        objects.stream();


        //2.将数组转换成流
        Integer[] arr = {1,2,3,4,5};
        Stream<Integer> stream = Arrays.stream(arr);
        Stream<Integer> arr1 = Stream.of(arr);

        //3.将双列集合转换成流
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        Stream<Map.Entry<Object, Object>> stream1 = objectObjectHashMap.entrySet().stream();
    }
}
