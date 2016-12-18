package xstefanox.rest.resourceassembler;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.AnnotationRelProvider;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;
import xstefanox.entity.User;
import xstefanox.rest.controller.UserController;
import xstefanox.rest.resource.BookResource;
import xstefanox.rest.resource.UserResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {

    private final BookResourceAssembler bookResourceAssembler;

    @Autowired
    public UserResourceAssembler(final BookResourceAssembler bookResourceAssembler) {
        super(UserController.class, UserResource.class);
        this.bookResourceAssembler = bookResourceAssembler;
    }

    @Override
    public UserResource toResource(User entity) {

        UserResource userResource = createResourceWithId(entity.getId(), entity)
                .setResourceId(entity.getId())
                .setFirstName(entity.getFirstName())
                .setLastName(entity.getLastName());

        List<EmbeddedWrapper> embeddeds = entity.getBooks().stream()
                .map(bookResourceAssembler::toResource)
                .map(new EmbeddedWrappers(true)::wrap)
                .collect(Collectors.toList());

        userResource.setEmbeddeds(new Resources<>(embeddeds));

        userResource.add(ControllerLinkBuilder.linkTo(methodOn(UserController.class)
                .getUserBooks(userResource.getResourceId(), null))
                .withRel(new AnnotationRelProvider().getCollectionResourceRelFor(BookResource.class)));

        return userResource;
    }
}
