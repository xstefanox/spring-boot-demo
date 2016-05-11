package xstefanox;

import java.util.Collections;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {

    public UserResourceAssembler(Class<?> controllerClass, Class<UserResource> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public UserResource toResource(User entity) {

        UserResource userResource = createResourceWithId(entity.getId(), entity)
                .setResourceId(entity.getId())
                .setFirstName(entity.getFirstName())
                .setLastName(entity.getLastName());

//        if (Math.random() >= 0.5) {
//            userResource.setBooks(Collections.singletonList("TEST"));
//        }

        return userResource;
    }
}
