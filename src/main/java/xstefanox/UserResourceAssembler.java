package xstefanox;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.AnnotationRelProvider;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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

        BookResourceAssembler bookResourceAssembler = new BookResourceAssembler(BookController.class, BookResource.class);

        List<EmbeddedWrapper> embeddeds = entity.getBooks().stream()
                .map(bookResourceAssembler::toResource)
                .map(new EmbeddedWrappers(true)::wrap)
                .collect(Collectors.toList());

        userResource.setEmbeddeds(new Resources<>(embeddeds));

        userResource.add(linkTo(methodOn(UserController.class)
                .getUserBooks(userResource.getResourceId(), null))
                .withRel(new AnnotationRelProvider().getCollectionResourceRelFor(BookResource.class)));

        return userResource;
    }
}
