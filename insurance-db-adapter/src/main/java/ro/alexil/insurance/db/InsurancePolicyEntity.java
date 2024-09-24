package ro.alexil.insurance.db;


import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "insurance_policy")
public class InsurancePolicyEntity {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsurancePolicyEntity.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Policy identifier, Auto-generated, Primary key

    @Column(name = "policy_name", nullable = false)
    private String policyName;  // Policy name, Not null, Cannot be empty

    @Enumerated(EnumType.STRING)
    @Column(name = "policy_status", nullable = false)
    private PolicyStatus policyStatus;  // Policy status, Enum, Not null

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;  // Coverage start date, Not null

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;  // Coverage end date, Not null

    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;  // Creation date, Not null, Auto-generated

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;  // Last update date, Not null, Auto-generated

    // Constructors
    public InsurancePolicyEntity() {
    }

    public InsurancePolicyEntity(String policyName, PolicyStatus policyStatus, LocalDate startDate, LocalDate endDate) {
        this.policyName = policyName;
        this.policyStatus = policyStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.creationDate = LocalDateTime.now();  // Automatically set to current date
        this.updatedDate = LocalDateTime.now();   // Automatically set to current date
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public PolicyStatus getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(PolicyStatus policyStatus) {
        this.policyStatus = policyStatus;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    // Enum for policy status
    public enum PolicyStatus {
        ACTIVE,
        INACTIVE
    }

    // PrePersist method to automatically set creation date and update date
    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    // PreUpdate method to automatically update the update date on every update
    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
