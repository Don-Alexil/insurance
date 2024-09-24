package ro.alexil.insurance.db;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import ro.alexil.insurance.domain.InsurancePolicy;
import ro.alexil.insurance.domain.InsurancePolicyRepository;
import ro.alexil.insurance.domain.PolicyStatus;
import ro.alexil.insurance.domain.exception.InsurancePolicyNotFoundException;

import java.util.List;

@Component
@Transactional
public class InsurancePolicyRepositoryAdapter implements InsurancePolicyRepository {

    private InsurancePolicyDbRepository insurancePolicyDbRepository;

    public InsurancePolicyRepositoryAdapter(InsurancePolicyDbRepository insurancePolicyDbRepository) {
        this.insurancePolicyDbRepository = insurancePolicyDbRepository;
    }

    @Override
    public InsurancePolicy create(InsurancePolicy insurancePolicy) {
        var insurancePolicyEntity = new InsurancePolicyEntity(
                insurancePolicy.name(),
                InsurancePolicyEntity.PolicyStatus.valueOf(insurancePolicy.status().name()),
                insurancePolicy.startDate(),
                insurancePolicy.endDate());
        insurancePolicyEntity = this.insurancePolicyDbRepository.saveAndFlush(insurancePolicyEntity);
        return map(insurancePolicyEntity);

    }

    @Override
    public InsurancePolicy update(InsurancePolicy insurancePolicy) {
        try  {
            var insurancePolicyEntity = this.insurancePolicyDbRepository.getReferenceById(insurancePolicy.id());
            insurancePolicyEntity.setPolicyName(insurancePolicy.name());
            insurancePolicyEntity.setPolicyStatus(InsurancePolicyEntity.PolicyStatus.valueOf(insurancePolicy.status().name()));
            insurancePolicyEntity.setStartDate(insurancePolicy.startDate());
            insurancePolicyEntity.setEndDate(insurancePolicy.endDate());
            insurancePolicyEntity = this.insurancePolicyDbRepository.saveAndFlush(insurancePolicyEntity);
            return map(insurancePolicyEntity);
        }
        catch (EntityNotFoundException e) {
            throw new InsurancePolicyNotFoundException(insurancePolicy.id());
        }
    }

    @Override
    public InsurancePolicy findById(Long id) {
        var optionalEntity = this.insurancePolicyDbRepository.findById(id);
        if(optionalEntity.isEmpty()) {
            throw new InsurancePolicyNotFoundException(id);
        }
        return map(optionalEntity.get());

    }

    @Override
    public List<InsurancePolicy> findAll() {
        return this.insurancePolicyDbRepository.findAll()
                .stream().map(this::map).toList();
    }

    private InsurancePolicy map(InsurancePolicyEntity entity) {
        return new InsurancePolicy(
                entity.getId(),
                entity.getPolicyName(),
                PolicyStatus.valueOf(entity.getPolicyStatus().name()),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getCreationDate(),
                entity.getUpdatedDate());
    }
}
