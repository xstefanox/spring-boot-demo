package xstefanox;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {

    public UserResourceAssembler(Class<?> controllerClass, Class<UserResource> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public UserResource toResource(User entity) {

        return createResourceWithId(entity.getId(), entity)
                .setResourceId(entity.getId())
                .setFirstName(entity.getFirstName())
                .setLastName(entity.getLastName());
    }
}
