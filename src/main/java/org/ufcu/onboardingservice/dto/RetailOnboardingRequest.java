package org.ufcu.onboardingservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RetailOnboardingRequest {

    @NotBlank(message = "Channel is required")
    private String customerOnboardingChannel;

    @NotBlank(message = "Product service type is required")
    private String productServiceType;

    @NotBlank(message = "Onboarding schedule is required")
    private String customerOnboardingSchedule;

    @NotBlank(message = "Prospect type is required")
    private String prospectType;

    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^[0-9\\-+\\s()]{10,}$",
             message = "Phone number must be at least 10 characters long and contain only numbers, hyphens, plus signs, spaces, or parentheses")
    private String phoneNumber;

    @NotBlank(message = "Full name is required")
    private String fullName;
}
