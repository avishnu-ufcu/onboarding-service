package org.ufcu.onboardingservice.util;


import java.time.Year;
import java.util.UUID;

public class OnboardingCaseIdGenerator {

    public static String generateCaseId() {
        // CS-7digits-YYYY
        // Generate a short UUID (8 characters) for uniqueness is it unique
        String shortUuid = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();

        return "OB-" + Year.now().getValue() + "-" + shortUuid;
    }
}
