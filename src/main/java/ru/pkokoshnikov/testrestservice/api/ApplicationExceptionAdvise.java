package ru.pkokoshnikov.testrestservice.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.pkokoshnikov.testrestservice.exception.ApplicationServiceException;

@ControllerAdvice
public class ApplicationExceptionAdvise {
    private Logger logger = LoggerFactory.getLogger(ApplicationExceptionAdvise.class);

    @ResponseBody
    @ExceptionHandler(ApplicationServiceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String employeeNotFoundHandler(ApplicationServiceException ex) {
        return ex.getMessage();
    }


    @ResponseBody
    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRunTimeException(RuntimeException ex) {
        logger.error("Handle runtime exception", ex);

        return "Internal server error";
    }
}
