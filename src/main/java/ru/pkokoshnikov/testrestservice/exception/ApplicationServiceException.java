package ru.pkokoshnikov.testrestservice.exception;

public class ApplicationServiceException extends RuntimeException {
    public ApplicationServiceException(String message) {
        super(message);
    }
}
