package xstefanox.rest;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.VndErrors;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import xstefanox.rest.exception.ResourceNotFoundException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static xstefanox.rest.filter.RequestIdFilter.REQUEST_ID;

@ControllerAdvice
public class ExceptionHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlers.class);

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    public VndErrors.VndError onException(ResourceNotFoundException e, @RequestAttribute(REQUEST_ID) UUID requestId) {

        VndErrors.VndError vndError = new VndErrors.VndError(requestId.toString(), "resource not found");

        LOGGER.info("resource not found");

        return vndError;
    }
}
