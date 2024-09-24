package ro.alexil.insurance.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ro.alexil.insurance.domain.exception.InsurancePolicyInvalidException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Validated
public record InsurancePolicy(
        Long id,
        @NotBlank String name,
        @NotNull PolicyStatus status,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        LocalDateTime creationDate,
        LocalDateTime updateDate) {

    public InsurancePolicy {
        LocalDate now = LocalDate.now();

        if (startDate.isBefore(now) || endDate.isBefore(now)) {
            throw new InsurancePolicyInvalidException("startDate of endDate cannot be in the past");
        }

        if(endDate.isBefore(startDate)){
            throw new InsurancePolicyInvalidException("endDate must be after startDate");
        }
    }
    public static InsurancePolicy newInsurancePolicy(String name,
                                                     PolicyStatus status, LocalDate startDate,
                                                     LocalDate endDate) {
        return new InsurancePolicy(-1L, name, status, startDate, endDate, LocalDateTime.now(), LocalDateTime.now());
    }

    public InsurancePolicy withId(Long id) {
        return new InsurancePolicy(id, name, status, startDate, endDate, creationDate, updateDate);
    }

    public InsurancePolicy withCreationDate(LocalDateTime creationDate) {
        return new InsurancePolicy(id, name, status, startDate, endDate, creationDate, updateDate);
    }

    public InsurancePolicy withUpdateDate(LocalDateTime updateDate) {
        return new InsurancePolicy(id, name, status, startDate, endDate, creationDate, updateDate);
    }
}