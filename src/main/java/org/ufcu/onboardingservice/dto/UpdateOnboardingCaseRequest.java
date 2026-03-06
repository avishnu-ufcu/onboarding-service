package org.ufcu.onboardingservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOnboardingCaseRequest {
    @NotBlank
    @Pattern(regexp = "^OB-\\d{4}-[A-Z0-9]{12}$", message = "caseId must match pattern OB-YYYY-XXXXXXXXXXXX")
    private String caseId;
    @NotBlank
    private String customerId;
    @NotBlank
    private String status;
}
