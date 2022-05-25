package optional;

import org.junit.Test;
import stream.Author;
import stream.Book;

import java.util.List;
import java.util.Optional;

public class OptionalTest01 {
    public static void main(String[] args) {
        Optional<Author> autor = getAutor();
//        autor.ifPresent(author -> System.out.println(author.getName()));
        //不安全的get
//        System.out.println(autor.get());
        //安全的get，获取的对象为null时，返回默认值
//        System.out.println(autor.orElseGet(()-> new Author(1l,"范冰冰",26,"演员",null)));
        //安全的get，获取的对象为null时，抛出异常
        System.out.println(autor.orElseThrow(()-> new RuntimeException("获取的值为空")));

    }


    @Test
    public void test01(){
        Optional<Author> autor = getAutor();
        autor.filter(author -> author.getAge()>88).ifPresent(author -> System.out.println(author.getName()));
    }

    @Test
    public void test02(){
        Optional<Author> autor = getAutor();
        Optional<List<Book>> books = autor.map(author -> author.getBooks());
        books.ifPresent(books1 -> System.out.println(books1));
    }


    //一般在get的时候 返回Optional类
    public static Optional<Author> getAutor(){
        Author author = new Author(1L, "liudehua", 38, "歌手", null);
        return Optional.ofNullable(author);
    }
}
