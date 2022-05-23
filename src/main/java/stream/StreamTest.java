package stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * 练习创建流、流的中间操作
 */
public class StreamTest {
    public static void main(String[] args) {
        List<Author> authors = getAuthors();
        //打印所有年龄小于18的作家的名字，并且注意去重
        authors
                .stream()//将集合转化为流
                .distinct()//去重
                .filter(new Predicate<Author>() {
                    @Override
                    public boolean test(Author author) {
                        return author.getAge() < 18;
                    }
                })
                .forEach(author -> System.out.println(author.getName()));
    }


    /**
     * 中间操作 map
     */
    @Test
    public void test01(){
        //使用map中间操作，将作者类型转换为String类型
        List<Author> authors = getAuthors();
        authors
                .stream()
                .map(new Function<Author, String>() {
                    @Override
                    public String apply(Author author) {
                        return author.getName();
                    }
                })
                .forEach(s -> System.out.println(s));
    }


    /**
     * 中间操作 sorted    按照年龄降序 输出所有的作者的名字，注意去重
     * @return
     */
    @Test
    public void test02(){
        List<Author> authors = getAuthors();
        authors
                .stream()
                .distinct()
                .sorted((o1, o2) -> o2.getAge()-o1.getAge())
                .forEach(author -> System.out.println(author.getAge()));
    }


    /**
     * 中间操作 limit  按照年龄降序 输出前两位的作者的名字，注意去重
     */
    @Test
    public void test03(){
        List<Author> authors = getAuthors();
        authors
                .stream()
                .distinct()
                .sorted((o1, o2) -> o2.getAge()-o1.getAge())
                .limit(2)
                .forEach(author -> System.out.println(author.getName()+author.getAge()));
    }


    /**
     * 中间操作 skip  按照年龄降序 输出所有作者的名字，但是去除年龄最大的，注意去重
     */
    @Test
    public void test04(){
        List<Author> authors = getAuthors();
        authors
                .stream()
                .distinct()
                .sorted((o1, o2) -> o2.getAge()-o1.getAge())
                .skip(1)
                .forEach(author -> System.out.println(author.getName()+author.getAge()));
    }


    /**
     * 中间操作 flatMap 打印所有书籍的名字。要求对重复的元素进行去重。
     */
    @Test
    public void test05(){
        List<Author> authors = getAuthors();
        authors
                .stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .forEach(book -> System.out.println(book.getName()));
    }

    /**
     * 中间操作 flatMap 打印现有数据的所有分类。要求对分类进行去重。不能出现这种格式：哲学,爱情。
     */
    @Test
    public void test06(){
        List<Author> authors = getAuthors();
        authors
                .stream()
                .flatMap((Function<Author, Stream<Book>>) author -> author.getBooks().stream())
                .distinct()
                .flatMap((Function<Book, Stream<String>>) book -> Arrays.stream(book.getCategory().split(",")))
                .distinct()
                .forEach(s -> System.out.println(s));
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
