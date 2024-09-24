package ro.alexil.insurance.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface InsurancePolicyDelegate {

    default ResponseEntity<InsurancePolicyResponse> getInsurancePolicy(@Positive Long id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    default ResponseEntity<List<InsurancePolicyResponse>> getAllInsurancePolicies() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    default ResponseEntity<InsurancePolicyResponse> createInsurancePolicy(
            @Valid InsurancePolicyRequest insurancePolicyRequest){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    default ResponseEntity<InsurancePolicyResponse> updateInsurancePolicy(
            @Positive Long id,
            @Valid InsurancePolicyRequest insurancePolicyRequest){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    default ResponseEntity<Void> deleteInsurancePolicy(
            @Positive Long id){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
