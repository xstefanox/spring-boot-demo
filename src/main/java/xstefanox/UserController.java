package xstefanox;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static xstefanox.ExampleApplication.USERS;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private PagedResourcesAssembler<User> userPagedResourcesAssembler;
    private PagedResourcesAssembler<Book> bookPagedResourcesAssembler;

    @Autowired
    public UserController(
            PagedResourcesAssembler<User> userPagedResourcesAssembler,
            PagedResourcesAssembler<Book> bookPagedResourcesAssembler) {
        this.userPagedResourcesAssembler = userPagedResourcesAssembler;
        this.bookPagedResourcesAssembler = bookPagedResourcesAssembler;
    }

    @RequestMapping(method = GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", paramType = "query", dataType = "int", allowableValues = "range[1, infinity]", defaultValue = "0"),
            @ApiImplicitParam(name = "size", paramType = "query", dataType = "int", allowableValues = "range[1, infinity]"),
            @ApiImplicitParam(name = "sort", paramType = "query", dataType = "string")
    })
    public PagedResources<UserResource> getUsers(@ApiIgnore Pageable pageable) {

        LOGGER.debug("pageable = {}", pageable);

        UserResourceAssembler userResourceAssembler = new UserResourceAssembler(UserController.class, UserResource.class);

        return userPagedResourcesAssembler.toResource(new PageImpl<>(new ArrayList<>(USERS.values())), userResourceAssembler);
    }

    @RequestMapping(
            path = "/{userId}",
            method = GET)
    public UserResource getUser(@PathVariable String userId) {

        LOGGER.debug("userId = {}", userId);

        User user = USERS.get(userId);

        if (user == null) {
            throw new ResourceNotFoundException();
        }

        UserResourceAssembler userResourceAssembler = new UserResourceAssembler(UserController.class, UserResource.class);

        return userResourceAssembler.toResource(user);
    }

    @RequestMapping(
            path = "/{userId}/books",
            method = GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", paramType = "query", dataType = "int", allowableValues = "range[1, infinity]", defaultValue = "0"),
            @ApiImplicitParam(name = "size", paramType = "query", dataType = "int", allowableValues = "range[1, infinity]"),
            @ApiImplicitParam(name = "sort", paramType = "query", dataType = "string")
    })
    public PagedResources<BookResource> getUserBooks(@PathVariable String userId, @ApiIgnore Pageable pageable) {

        LOGGER.debug("userId = {}, pageable = {}", userId, pageable);

        User user = USERS.get(userId);

        if (user == null) {
            throw new ResourceNotFoundException();
        }

        Page<Book> books = new PageImpl<>(user.getBooks());

        BookResourceAssembler bookResourceAssembler = new BookResourceAssembler(BookController.class, BookResource.class);

        return bookPagedResourcesAssembler.toResource(books, bookResourceAssembler);

    }
}
