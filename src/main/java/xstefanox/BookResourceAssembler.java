package xstefanox;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class BookResourceAssembler extends ResourceAssemblerSupport<Book, BookResource> {

    public BookResourceAssembler(Class<?> controllerClass, Class<BookResource> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public BookResource toResource(Book entity) {

        return createResourceWithId(entity.getId(), entity)
                .setResourceId(entity.getId())
                .setAuthor(entity.getAuthor())
                .setTitle(entity.getTitle());
    }
}
