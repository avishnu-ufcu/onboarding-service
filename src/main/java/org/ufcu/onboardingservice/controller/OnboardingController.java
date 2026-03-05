package org.ufcu.onboardingservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ufcu.onboardingservice.dto.RetailOnboardingRequest;
import org.ufcu.onboardingservice.dto.RetailOnboardingResponse;
import org.ufcu.onboardingservice.domain.Onboarding_Case;
import org.ufcu.onboardingservice.service.OnboardingService;
import org.ufcu.onboardingservice.util.ValidationUtil;

@RestController
@RequestMapping("/api/v1/onboarding")
@RequiredArgsConstructor
@Slf4j
public class OnboardingController {

    private final OnboardingService onboardingService;

    @PostMapping("/retail")
    public ResponseEntity<RetailOnboardingResponse> initiateRetailOnboarding(@Valid @RequestBody RetailOnboardingRequest request) {

        log.info("Initiating retail onboarding for email: {}, phone: {}", request.getEmail(), request.getPhoneNumber());
        ValidationUtil.validateEmailOrPhone(request);
        // Process the request and create onboarding entity
        Onboarding_Case onboarding = onboardingService.initiateRetailOnboarding(request);

        // Map entity to response DTO
        RetailOnboardingResponse response = new RetailOnboardingResponse(onboarding.getCaseId(), onboarding.getStatus().name());
        log.info("Retail onboarding initiated successfully with caseId: {}", onboarding.getCaseId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/cases/{caseId}/approvals")
    public ResponseEntity<RetailOnboardingResponse> approveOnboardingCase(
            @PathVariable String caseId) {

        ValidationUtil.validateCaseId(caseId);
        // Process the approval request using service
        Onboarding_Case onboarding = onboardingService.approveOnboardingCase(caseId);

        // Map entity to response DTO
        RetailOnboardingResponse response = new RetailOnboardingResponse(onboarding.getCaseId(), onboarding.getStatus().name());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
