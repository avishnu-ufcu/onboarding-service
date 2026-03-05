package org.ufcu.onboardingservice.exception;

public class CaseCreationFailedException extends RuntimeException {

    public CaseCreationFailedException(String message) {
        super(message);
    }

    public CaseCreationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}

