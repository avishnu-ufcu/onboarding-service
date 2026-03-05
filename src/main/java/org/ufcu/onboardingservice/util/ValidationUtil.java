package org.ufcu.onboardingservice.util;

import org.ufcu.onboardingservice.dto.RetailOnboardingRequest;

public class ValidationUtil {
    private ValidationUtil() {}

    public static void validateEmailOrPhone(RetailOnboardingRequest request) {
        if ((request.getEmail() == null || request.getEmail().isBlank()) &&
            (request.getPhoneNumber() == null || request.getPhoneNumber().isBlank())) {
            throw new IllegalArgumentException("Either email or phoneNumber must be provided");
        }
    }

    public static void validateCaseId(String caseId) {
        if (caseId == null || caseId.trim().isEmpty()) {
            throw new IllegalArgumentException("Case ID is required and cannot be empty");
        }
    }
}
