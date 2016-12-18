package xstefanox;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.util.IdGenerator;
import org.springframework.util.JdkIdGenerator;
import xstefanox.entity.Book;
import xstefanox.entity.User;
import xstefanox.rest.RestConfiguration;

@EnableAutoConfiguration
@Import(RestConfiguration.class)
public class ExampleApplication {

    public static final Map<String, User> USERS = new HashMap<>();
    public static final Map<String, Book> BOOKS = new HashMap<>();

    static {

        BOOKS.put("111", new Book()
                .setId("111")
                .setTitle("The Lord of the Rings")
                .setAuthor("J.R.R. Tolkien"));

        BOOKS.put("222", new Book()
                .setId("222")
                .setTitle("The Hobbit")
                .setAuthor("J.R.R. Tolkien"));

        BOOKS.put("333", new Book()
                .setId("333")
                .setTitle("The Hunt for Red October")
                .setAuthor("Tom Clancy"));


        USERS.put("123", new User()
                .setId("123")
                .setFirstName("Leeroy")
                .setLastName("Jenkins")
                .addBook(BOOKS.get("111"))
                .addBook(BOOKS.get("222")));

        USERS.put("456", new User()
                .setId("456")
                .setFirstName("Anakin")
                .setLastName("Skywalker")
                .addBook(BOOKS.get("333")));

        USERS.put("789", new User()
                .setId("789")
                .setFirstName("Oliver")
                .setLastName("Hutton"));

    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @Bean
    public IdGenerator idGenerator() {
        return new JdkIdGenerator();
    }
}
