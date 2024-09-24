package ro.alexil.insurance.domain.exception;

public class InsurancePolicyNotFoundException extends RuntimeException {
    public static final String ERROR_MESSAGE = "Insurance policy with id [%d] not found.";

    public InsurancePolicyNotFoundException(Long insurancePolicyId) {
        super(String.format(ERROR_MESSAGE, insurancePolicyId));
    }
}
