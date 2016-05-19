package xstefanox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.data.annotation.Id;

public class User {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private List<Book> books = new ArrayList<>();

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public User addBook(Book book) {
        this.books.add(book);
        return this;
    }

    public User removeBook(Book book) {
        this.books.remove(book);
        return this;
    }
}
