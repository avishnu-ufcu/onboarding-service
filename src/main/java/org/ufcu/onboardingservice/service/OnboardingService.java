package org.ufcu.onboardingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ufcu.onboardingservice.dto.RetailOnboardingRequest;
import org.ufcu.onboardingservice.domain.Onboarding_Case;
import org.ufcu.onboardingservice.exception.CaseCreationFailedException;
import org.ufcu.onboardingservice.repository.OnboardingCaseRepository;
import org.ufcu.onboardingservice.util.OnboardingCaseIdGenerator;

@Service
@Slf4j
@RequiredArgsConstructor
public class OnboardingService {

    private final OnboardingCaseRepository onboardingCaseRepository;

    /**
     * Process retail onboarding request and persist Onboarding_Case entity
     */
    @Transactional
    public Onboarding_Case initiateRetailOnboarding(RetailOnboardingRequest request) {

        try {
            Onboarding_Case onboarding = new Onboarding_Case();

            // Generate UUID for caseId
            String caseId = OnboardingCaseIdGenerator.generateCaseId();
            onboarding.setCaseId(caseId);

            // Map all fields from request to entity
            onboarding.setChannel(request.getCustomerOnboardingChannel());
            onboarding.setRequestedProductType(request.getProductServiceType());
            onboarding.setEmail(request.getEmail());
            onboarding.setPhoneNumber(request.getPhoneNumber());
            onboarding.setFullName(request.getFullName());
            onboarding.setStatus(Onboarding_Case.OnboardingStatus.INITIATED); // Set enum, not String

            // Status and timestamps handled by entity @PrePersist
            Onboarding_Case saved = onboardingCaseRepository.save(onboarding);

            log.info("Onboarding case persisted successfully with caseId: {}", saved.getCaseId());
            return saved;

        } catch (Exception ex) {
            log.error("Unexpected error while processing retail onboarding: {}", ex.getMessage());
            throw new CaseCreationFailedException("Failed to create onboarding case: " + ex.getMessage(), ex);
        }
    }

    @Transactional
    public Onboarding_Case approveOnboardingCase(String caseId) {
        // Validation moved to controller
        try {
            Onboarding_Case onboarding = onboardingCaseRepository.findById(caseId)
                    .orElseThrow(() -> new CaseCreationFailedException("Onboarding case not found: " + caseId));

            onboarding.setStatus(Onboarding_Case.OnboardingStatus.APPROVED);
            Onboarding_Case saved = onboardingCaseRepository.save(onboarding);

            log.info("Onboarding case approved successfully with caseId: {}", saved.getCaseId());
            return saved;

        } catch (Exception ex) {
            log.error("Unexpected error while approving onboarding case: {}", ex.getMessage());
            throw new CaseCreationFailedException("Failed to approve onboarding case: " + ex.getMessage(), ex);
        }
    }

    /**
     * Update onboarding case with caseId, customerId, and status
     */
    @Transactional
    public Onboarding_Case updateOnboardingCase(String caseId, String customerId, String status) {
        try {
            Onboarding_Case onboarding = onboardingCaseRepository.findById(caseId)
                    .orElseThrow(() -> new CaseCreationFailedException("Onboarding case not found: " + caseId));

            onboarding.setCustomerId(customerId);
            onboarding.setStatus(Onboarding_Case.OnboardingStatus.valueOf(status));
            Onboarding_Case saved = onboardingCaseRepository.save(onboarding);

            log.info("Onboarding case updated successfully with caseId: {}", saved.getCaseId());
            return saved;
        } catch (Exception ex) {
            log.error("Unexpected error while updating onboarding case: {}", ex.getMessage());
            throw new CaseCreationFailedException("Failed to update onboarding case: " + ex.getMessage(), ex);
        }
    }
}
