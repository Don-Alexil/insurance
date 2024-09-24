package ro.alexil.insurance.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import ro.alexil.insurance.domain.PolicyStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
class InsurancePolicyControllerTest {

    @Configuration
    static class ContextConfiguration {
        @Bean
        public MethodValidationPostProcessor methodValidationPostProcessor() {
            return new MethodValidationPostProcessor();
        }
    }

    @MockBean
    private InsurancePolicyDelegate serviceDelegate;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new InsurancePolicyController(serviceDelegate))
                .setHandlerExceptionResolvers().build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"""
        {
          "name": "Health Insurance",
          "status" : "ACTIVE",
          "startDate" : "2024-10-20",
          "endDate" : "2025-11-15"
        }"""})
    void posAccountsShouldReturnCreatedAccount(String content) throws Exception {
        InsurancePolicyRequest insurancePolicyRequest = new InsurancePolicyRequest("Health Insurance",
                PolicyStatus.ACTIVE,
                LocalDate.parse("2024-10-20"), LocalDate.parse("2025-11-15"));
        InsurancePolicyResponse insurancePolicyResponse = new InsurancePolicyResponse(1L, "toto",
                PolicyStatus.ACTIVE, LocalDate.now(),LocalDate.now(), LocalDateTime.now(), LocalDateTime.now());
        when(serviceDelegate.createInsurancePolicy(eq(insurancePolicyRequest)))
                .thenReturn(ResponseEntity.ok(insurancePolicyResponse));
        this.mockMvc.perform(post("/insurancepolicies")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(insurancePolicyResponse.name()));
    }

}