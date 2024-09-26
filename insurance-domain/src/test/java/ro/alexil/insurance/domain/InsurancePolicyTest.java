package ro.alexil.insurance.domain;

import org.junit.jupiter.api.Test;
import ro.alexil.insurance.domain.exception.InsurancePolicyInvalidException;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InsurancePolicyTest {

    @Test
    public void whenStartDateIsAfterEndDateThenAnExceptionIsThrown(){
        LocalDate startDate = LocalDate.parse("2000-10-20");
        LocalDate endDate = LocalDate.parse("1999-10-20");

        var exception = assertThrows(InsurancePolicyInvalidException.class,
                () -> new InsurancePolicy(0L, "toto", PolicyStatus.ACTIVE,
                        startDate, endDate, LocalDateTime.now(), LocalDateTime.now()));

        assertEquals("endDate must be after startDate", exception.getMessage());
    }
}