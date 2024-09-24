package ro.alexil.insurance.rest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import ro.alexil.insurance.domain.PolicyStatus;

import java.time.LocalDate;

public record InsurancePolicyRequest(
        @NotBlank String name,
        @NotNull PolicyStatus status,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate
) {
}
