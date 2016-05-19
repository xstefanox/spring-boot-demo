package xstefanox;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(
        value = "book",
        collectionRelation = "books")
public class BookResource extends ResourceSupport {

    private String id;

    private String title;

    private String author;

    @JsonProperty("id")
    public String getResourceId() {
        return id;
    }

    public BookResource setResourceId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookResource setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public BookResource setAuthor(String author) {
        this.author = author;
        return this;
    }
}
