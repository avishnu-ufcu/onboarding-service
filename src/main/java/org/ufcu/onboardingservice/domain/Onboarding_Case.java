package org.ufcu.onboardingservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "onboarding_case")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Onboarding_Case {

    @Id
    @Column(name = "case_id", length = 36)
    private String caseId;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "customer_id", length = 36)
    private String customerId;

    @Column(name = "account_id", length = 36)
    private String accountId;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
//    @Column(name = "status", nullable = false)
    @Column(columnDefinition = "onboarding_status", nullable = false)
    private OnboardingStatus status;

    @Column(name = "current_step", length = 100)
    private String currentStep;

    @Column(name = "requested_product_type", length = 100, nullable = false)
    private String requestedProductType;

    @Column(name = "selected_product_code", length = 50)
    private String selectedProductCode;

    @Column(name = "channel", length = 50, nullable = false)
    private String channel;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "full_name", length = 255)
    private String fullName;

    public enum OnboardingStatus {
        INITIATED,
        IN_PROGRESS,
        PENDING_APPROVAL,
        APPROVED,
        REJECTED,
        COMPLETED,
        EXPIRED,
        CANCELLED
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = OnboardingStatus.INITIATED;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
