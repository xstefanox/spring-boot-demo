package xstefanox.rest.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.util.IdGenerator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import xstefanox.entity.Book;
import xstefanox.rest.exception.ResourceNotFoundException;
import xstefanox.rest.resource.BookResource;
import xstefanox.rest.resourceassembler.BookResourceAssembler;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static xstefanox.ExampleApplication.BOOKS;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    private final PagedResourcesAssembler<Book> bookPagedResourcesAssembler;
    private final BookResourceAssembler bookResourceAssembler;
    private final IdGenerator idGenerator;

    @Autowired
    public BookController(
            final PagedResourcesAssembler<Book> bookPagedResourcesAssembler,
            final BookResourceAssembler bookResourceAssembler,
            final IdGenerator idGenerator) {
        this.bookPagedResourcesAssembler = bookPagedResourcesAssembler;
        this.bookResourceAssembler = bookResourceAssembler;
        this.idGenerator = idGenerator;
    }

    @RequestMapping(method = GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", paramType = "query", dataType = "int", allowableValues = "range[1, infinity]", defaultValue = "0"),
            @ApiImplicitParam(name = "size", paramType = "query", dataType = "int", allowableValues = "range[1, infinity]"),
            @ApiImplicitParam(name = "sort", paramType = "query", dataType = "string")
    })
    public PagedResources<BookResource> getBooks(@ApiIgnore Pageable pageable) {

        LOGGER.debug("pageable = {}", pageable);

        return bookPagedResourcesAssembler.toResource(new PageImpl<>(new ArrayList<>(BOOKS.values())), bookResourceAssembler);
    }

    @RequestMapping(
            method = GET,
            path = "/{bookId}")
    public BookResource getBook(@PathVariable String bookId) {

        LOGGER.debug("bookId = {}", bookId);

        Book book = BOOKS.get(bookId);

        if (book == null) {
            throw new ResourceNotFoundException();
        }

        return bookResourceAssembler.toResource(book);
    }

    @RequestMapping(method = POST)
    public BookResource addBook(@RequestBody Book book) {

        book.setId(idGenerator.generateId().toString());

        BOOKS.put(book.getId(), book);

        return bookResourceAssembler.toResource(book);
    }
}
