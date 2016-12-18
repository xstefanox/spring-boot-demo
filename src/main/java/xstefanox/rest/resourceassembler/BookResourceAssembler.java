package xstefanox.rest.resourceassembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;
import xstefanox.entity.Book;
import xstefanox.rest.controller.BookController;
import xstefanox.rest.resource.BookResource;

@Service
public class BookResourceAssembler extends ResourceAssemblerSupport<Book, BookResource> {

    public BookResourceAssembler() {
        super(BookController.class, BookResource.class);
    }

    @Override
    public BookResource toResource(Book entity) {

        return createResourceWithId(entity.getId(), entity)
                .setResourceId(entity.getId())
                .setAuthor(entity.getAuthor())
                .setTitle(entity.getTitle());
    }
}
