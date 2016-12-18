package xstefanox.entity;

import org.springframework.data.annotation.Id;

public class Book {

    @Id
    private String id;

    private String title;

    private String author;

    public String getId() {
        return id;
    }

    public Book setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }
}
