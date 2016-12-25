package xstefanox.rest.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(
        value = "customer",
        collectionRelation = "customers")
public class CustomerResource extends ResourceSupport {

    private Long id;

    private String firstName;

    private String lastName;

    @JsonProperty("id")
    public Long getResourceId() {
        return id;
    }

    public void setResourceId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
