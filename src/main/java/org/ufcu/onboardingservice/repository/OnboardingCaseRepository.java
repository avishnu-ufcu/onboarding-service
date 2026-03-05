package org.ufcu.onboardingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ufcu.onboardingservice.domain.Onboarding_Case;

@Repository
public interface OnboardingCaseRepository extends JpaRepository<Onboarding_Case, String> {
    // Additional query methods can be added here
}

