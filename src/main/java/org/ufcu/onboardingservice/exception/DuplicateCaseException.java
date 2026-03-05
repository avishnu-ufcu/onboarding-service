package org.ufcu.onboardingservice.exception;

public class DuplicateCaseException extends RuntimeException {

    public DuplicateCaseException(String message) {
        super(message);
    }

    public DuplicateCaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

