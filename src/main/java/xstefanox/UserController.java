package xstefanox;

import com.fasterxml.jackson.core.JsonProcessingException;
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
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private static final Map<String, User> USERS;

    static {
        Map<String, User> users = new HashMap<>();

        users.put("123", new User()
                .setId("123")
                .setFirstName("Leeroy")
                .setLastName("Jenkins"));

        users.put("456", new User()
                .setId("456")
                .setFirstName("Anakin")
                .setLastName("Skywalker"));

        users.put("789", new User()
                .setId("789")
                .setFirstName("Oliver")
                .setLastName("Hutton"));

        USERS = Collections.unmodifiableMap(users);
    }

    private PagedResourcesAssembler<User> userPagedResourcesAssembler;

    @Autowired
    public UserController(PagedResourcesAssembler<User> userPagedResourcesAssembler) {
        this.userPagedResourcesAssembler = userPagedResourcesAssembler;
    }

    @RequestMapping(method = GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", paramType = "query", dataType = "int", allowableValues = "range[1, infinity]", defaultValue = "0"),
            @ApiImplicitParam(name = "size", paramType = "query", dataType = "int", allowableValues = "range[1, infinity]"),
            @ApiImplicitParam(name = "sort", paramType = "query", dataType = "string")
    })
    public PagedResources<UserResource> getUsers(@ApiIgnore Pageable pageable) throws JsonProcessingException {

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
}
