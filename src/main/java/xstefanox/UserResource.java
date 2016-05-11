package xstefanox;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(
        value = "user",
        collectionRelation = "users")
public class UserResource extends ResourceSupport {

    private String id;

    private String firstName;

    private String lastName;

    private List<String> books = new ArrayList<>();

    @JsonProperty("id")
    public String getResourceId() {
        return id;
    }

    public UserResource setResourceId(String id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserResource setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserResource setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<String> getBooks() {
        return books;
    }

    public UserResource setBooks(List<String> books) {
        this.books = books;
        return this;
    }
}
