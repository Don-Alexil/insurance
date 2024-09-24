package ro.alexil.insurance.domain.exception;

public class InsurancePolicyInvalidException extends RuntimeException
{
    public InsurancePolicyInvalidException(String message) {
        super(message);
    }
}
