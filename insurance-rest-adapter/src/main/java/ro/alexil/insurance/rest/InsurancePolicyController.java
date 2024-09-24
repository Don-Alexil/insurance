package ro.alexil.insurance.rest;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ro.alexil.insurance.domain.exception.InsurancePolicyInvalidException;
import ro.alexil.insurance.domain.exception.InsurancePolicyNotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class InsurancePolicyController implements InsurancePolicyApi {

    InsurancePolicyDelegate insurancePolicyDelegate;

    public InsurancePolicyController(InsurancePolicyDelegate insurancePolicyDelegate) {
        this.insurancePolicyDelegate = insurancePolicyDelegate;
    }

    @ExceptionHandler({ InsurancePolicyNotFoundException.class})
    protected ResponseEntity<String> handleInsurancePolicyNotFound(InsurancePolicyNotFoundException exception){
        String bodyOfResponse = "{ \"error\" : \"" + exception.getMessage() + "\" }";
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InsurancePolicyInvalidException.class})
    protected ResponseEntity<String> handleInsurancePolicyInvalid(InsurancePolicyInvalidException exception){
        String bodyOfResponse = "{ \"error\" : \"" + exception.getMessage() + "\" }";
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ ConstraintViolationException.class})
    protected ResponseEntity<String> handleOperationNotSchedule(ConstraintViolationException exception){
        String bodyOfResponse = "{ \"error\" : \"" + exception.getMessage() + "\" }";
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public InsurancePolicyDelegate getDelegate() {
        return this.insurancePolicyDelegate;
    }
}
