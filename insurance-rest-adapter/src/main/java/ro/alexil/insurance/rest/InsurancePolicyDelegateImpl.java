package ro.alexil.insurance.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ro.alexil.insurance.domain.InsurancePolicy;
import ro.alexil.insurance.domain.InsurancePolicyService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsurancePolicyDelegateImpl implements InsurancePolicyDelegate {

    private final InsurancePolicyService insurancePolicyService;

    public InsurancePolicyDelegateImpl(InsurancePolicyService insurancePolicyService) {
        this.insurancePolicyService = insurancePolicyService;
    }

    @Override
    public ResponseEntity<InsurancePolicyResponse> getInsurancePolicy(@Positive Long id) {
        return ResponseEntity.ok(map(this.insurancePolicyService.findById(id)));
    }

    @Override
    public ResponseEntity<List<InsurancePolicyResponse>> getAllInsurancePolicies() {
        List<InsurancePolicy> insurancePolicyAll = this.insurancePolicyService.findAll();
        var response = insurancePolicyAll.stream().map(this::map).toList();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<InsurancePolicyResponse> createInsurancePolicy(
            @Valid InsurancePolicyRequest insurancePolicyRequest){
        var insurancePolicy = insurancePolicyService.create(InsurancePolicy.newInsurancePolicy(
                insurancePolicyRequest.name(),
                insurancePolicyRequest.status(),
                insurancePolicyRequest.startDate(),
                insurancePolicyRequest.endDate()));
        return ResponseEntity.ok(map(insurancePolicy));
    }

    @Override
    public ResponseEntity<InsurancePolicyResponse> updateInsurancePolicy(
            @Positive Long id,
            @Valid InsurancePolicyRequest insurancePolicyRequest){
        var insurancePolicy = insurancePolicyService.update(
                InsurancePolicy.newInsurancePolicy(
                        insurancePolicyRequest.name(),
                        insurancePolicyRequest.status(),
                        insurancePolicyRequest.startDate(),
                        insurancePolicyRequest.endDate()
                ).withId(id));
        return ResponseEntity.ok(map(insurancePolicy));
    }

    private InsurancePolicyResponse map(InsurancePolicy insurancePolicy) {
        return new InsurancePolicyResponse(
                insurancePolicy.id(),
                insurancePolicy.name(),
                insurancePolicy.status(),
                insurancePolicy.startDate(),
                insurancePolicy.endDate(),
                insurancePolicy.creationDate(),
                insurancePolicy.updateDate());
    }
}
