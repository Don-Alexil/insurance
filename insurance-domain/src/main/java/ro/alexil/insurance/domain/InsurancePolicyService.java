package ro.alexil.insurance.domain;

import java.util.List;

public interface InsurancePolicyService {

    InsurancePolicy create(InsurancePolicy insurancePolicy);

    InsurancePolicy update(InsurancePolicy insurancePolicy);

    InsurancePolicy findById(Long id);

    void deleteById(Long id);

    List<InsurancePolicy> findAll();


}
