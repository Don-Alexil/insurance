package ro.alexil.insurance.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsurancePolicyServiceImpl implements InsurancePolicyService {

    private final InsurancePolicyRepository insurancePolicyRepository;

    public InsurancePolicyServiceImpl(InsurancePolicyRepository insurancePolicyRepository) {
        this.insurancePolicyRepository = insurancePolicyRepository;
    }

    @Override
    public InsurancePolicy create(InsurancePolicy insurancePolicy) {
        return this.insurancePolicyRepository.create(insurancePolicy);
    }

    @Override
    public InsurancePolicy update(InsurancePolicy insurancePolicy) {
        return this.insurancePolicyRepository.update(insurancePolicy);
    }

    @Override
    public InsurancePolicy findById(Long id) {
        return this.insurancePolicyRepository.findById(id);
    }

    @Override
    public List<InsurancePolicy> findAll() {
        return  this.insurancePolicyRepository.findAll();
    }
}
