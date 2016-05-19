package xstefanox;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    private static final Map<String, Book> BOOKS;

    static {
        Map<String, Book> books = new HashMap<>();

        books.put("111", new Book()
                .setId("111")
                .setTitle("The Lord of the Rings")
                .setAuthor("J.R.R. Tolkien"));

        books.put("222", new Book()
                .setId("222")
                .setTitle("The Hobbit")
                .setAuthor("J.R.R. Tolkien"));

        books.put("333", new Book()
                .setId("333")
                .setTitle("The Hunt for Red October")
                .setAuthor("Tom Clancy"));

        BOOKS = Collections.unmodifiableMap(books);
    }

    private PagedResourcesAssembler<Book> bookPagedResourcesAssembler;

    @Autowired
    public BookController(PagedResourcesAssembler<Book> bookPagedResourcesAssembler) {
        this.bookPagedResourcesAssembler = bookPagedResourcesAssembler;
    }

    @RequestMapping(method = GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", paramType = "query", dataType = "int", allowableValues = "range[1, infinity]", defaultValue = "0"),
            @ApiImplicitParam(name = "size", paramType = "query", dataType = "int", allowableValues = "range[1, infinity]"),
            @ApiImplicitParam(name = "sort", paramType = "query", dataType = "string")
    })
    public PagedResources<BookResource> getBooks(@ApiIgnore Pageable pageable) {

        LOGGER.debug("pageable = {}", pageable);

        BookResourceAssembler bookResourceAssembler = new BookResourceAssembler(BookController.class, BookResource.class);

        return bookPagedResourcesAssembler.toResource(new PageImpl<>(new ArrayList<>(BOOKS.values())), bookResourceAssembler);
    }

    @RequestMapping(
            path = "/{bookId}",
            method = GET)
    public BookResource getBook(@PathVariable String bookId) {

        LOGGER.debug("bookId = {}", bookId);

        Book book = BOOKS.get(bookId);

        if (book == null) {
            throw new ResourceNotFoundException();
        }

        BookResourceAssembler bookResourceAssembler = new BookResourceAssembler(BookController.class, BookResource.class);

        return bookResourceAssembler.toResource(book);
    }
}
