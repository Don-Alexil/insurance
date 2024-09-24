package ro.alexil.insurance.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsurancePolicyDbRepository extends JpaRepository<InsurancePolicyEntity, Long> {

    // Find all policies by their status (e.g., ACTIVE or INACTIVE)
    List<InsurancePolicyEntity> findByPolicyStatus(InsurancePolicyEntity.PolicyStatus status);

    // Find policies by policy name (exact match)
    List<InsurancePolicyEntity> findByPolicyName(String policyName);

    // Find policies where the policy name contains a specific string (case-insensitive)
    List<InsurancePolicyEntity> findByPolicyNameContainingIgnoreCase(String policyNamePart);
}