package stream;

import org.junit.Test;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 学习Stream流的终结操作
 */

public class StreamTest02 {

    /**
     * 终结操作 count    获取所有作家所出的书的总数
     */
    @Test
    public void test01(){
        List<Author> authors = getAuthors();
        long count = authors
                            .stream()
                            .flatMap(author -> { return author.getBooks().stream(); })
                            .distinct()
                            .count();
        System.out.println(count);

    }




    /**
     * 终结操作 max min    分别获取所处书籍的最高分和最低分
     */
    @Test
    public void test02(){
        List<Author> authors = getAuthors();

        Optional<Integer> max = authors
                                       .stream()
                                       .flatMap(author -> { return author.getBooks().stream(); })
                                       .map(book -> { return book.getScore(); })
                                       .distinct()
                                       .max((o1, o2) -> o1 - o2);
        System.out.println(max.get());

        Optional<Integer> min = authors
                                       .stream()
                                       .flatMap(author -> { return author.getBooks().stream(); })
                                       .map(book -> { return book.getScore(); })
                                       .distinct()
                                       .min((o1, o2) -> o1 - o2);
        System.out.println(min.get());
    }





    /**
     * 终结操作 collect
     */
    @Test
    public void test03(){
        List<Author> authors = getAuthors();
        
        
        //获取一个存放所有作者名字的List集合。
        List<String> list = authors
                .stream()
                .map(author -> author.getName())
                .distinct()
                .collect(Collectors.toList());

        System.out.println(list);
        
        
        //获取一个所有书名的Set集合。
        Set<Book> set = authors
                .stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .collect(Collectors.toSet());
        System.out.println(set);
        
        
        //获取一个Map集合，map的key为作者名，value为List<Book>
        Map<String, List<Book>> collect = authors
                .stream()
                .distinct()
                .collect(Collectors.toMap(author -> author.getName(), author -> author.getBooks()));
        System.out.println(collect);


    }




    /**
     * 终结操作 anymatch...........
     * @return
     */
    @Test
    public void tets04(){
        List<Author> authors = getAuthors();

        //判断是否有年龄在29以上的作家
        boolean result = authors
                .stream()
                .anyMatch(author -> author.getAge() > 29);
        System.out.println(result);

        //判断是否所有的作家都是成年人
        boolean result2 = authors
                .stream()
                .anyMatch(author -> author.getAge() > 18);
        System.out.println(result2);


    }


    

    /**
     * 终结操作  reduce
     */
    @Test
    public void test05(){
        //使用reduce获取所有作家年龄的和
        List<Author> authors = getAuthors();
        Integer reduce = authors
                               .stream()
                               .distinct()
                               .map(author -> author.getAge())
                               .reduce(0, (result, element) -> result + element);
        System.out.println(reduce);


    }

    /**
     * stream流对基本数据类型的优化
     */
    @Test
    public void test06(){
        List<Author> authors = getAuthors();
        authors.stream()
                .map(author -> author.getAge())
                .map(age -> age + 10)
                .filter(age->age>18)
                .map(age->age+2)
                .forEach(System.out::println);


        authors.stream()
                .mapToInt(author -> author.getAge())
                .map(age -> age + 10)
                .filter(age->age>18)
                .map(age->age+2)
                .forEach(System.out::println);
    }


    /**
     * 并行流
     */
    @Test
    public void test07(){
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sum = stream.parallel()
                .peek(num -> System.out.println(num+Thread.currentThread().getName()))
                .filter(num -> num > 5)
                .reduce((result, ele) -> result + ele)
                .get();
        System.out.println(sum);
    }


    private static List<Author> getAuthors() {
        //数据初始化
        Author author = new Author(1L,"蒙多",33,"一个从菜刀中明悟哲理的祖安人",null);
        Author author2 = new Author(2L,"亚拉索",15,"狂风也追逐不上他的思考速度",null);
        Author author3 = new Author(3L,"易",14,"是这个世界在限制他的思维",null);
        Author author4 = new Author(3L,"易",14,"是这个世界在限制他的思维",null);

        //书籍列表
        List<Book> books1 = new ArrayList<>();
        List<Book> books2 = new ArrayList<>();
        List<Book> books3 = new ArrayList<>();

        books1.add(new Book(1L,"刀的两侧是光明与黑暗","哲学,爱情",88,"用一把刀划分了爱恨"));
        books1.add(new Book(2L,"一个人不能死在同一把刀下","个人成长,爱情",99,"讲述如何从失败中明悟真理"));

        books2.add(new Book(3L,"那风吹不到的地方","哲学",85,"带你用思维去领略世界的尽头"));
        books2.add(new Book(3L,"那风吹不到的地方","哲学",85,"带你用思维去领略世界的尽头"));
        books2.add(new Book(4L,"吹或不吹","爱情,个人传记",56,"一个哲学家的恋爱观注定很难把他所在的时代理解"));

        books3.add(new Book(5L,"你的剑就是我的剑","爱情",56,"无法想象一个武者能对他的伴侣这么的宽容"));
        books3.add(new Book(6L,"风与剑","个人传记",100,"两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢？"));
        books3.add(new Book(6L,"风与剑","个人传记",100,"两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢？"));

        author.setBooks(books1);
        author2.setBooks(books2);
        author3.setBooks(books3);
        author4.setBooks(books3);

        List<Author> authorList = new ArrayList<>(Arrays.asList(author,author2,author3,author4));
        return authorList;
    }
}
