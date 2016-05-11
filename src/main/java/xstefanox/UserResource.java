package xstefanox;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(
        value = "user",
        collectionRelation = "users")
public class UserResource extends ResourceSupport {

    private String id;

    private String firstName;

    private String lastName;

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
}
