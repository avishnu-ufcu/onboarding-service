package org.ufcu.onboardingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetailOnboardingResponse {

    private String caseId;
    private String caseStatus;
}
