package xstefanox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.VndErrors;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ExceptionHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlers.class);

    @Autowired
    private RequestIdProvider requestIdProvider;

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    public VndErrors.VndError onException(ResourceNotFoundException e) {

        VndErrors.VndError vndError = new VndErrors.VndError(requestIdProvider.get(), "resource not found");

//        vndError.add(new Link("http://example.org", "example"));

        LOGGER.info("resource not found");

        return vndError;
    }
}
