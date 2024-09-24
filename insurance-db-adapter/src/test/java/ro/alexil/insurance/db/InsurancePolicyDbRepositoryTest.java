package ro.alexil.insurance.db;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Testcontainers
class InsurancePolicyDbRepositoryTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0").withDatabaseName("insurances");

    @Autowired
    InsurancePolicyDbRepository insurancePolicyDbRepository;


    @BeforeAll
    static void beforeAll() {
        mysql.start();
    }

    @AfterAll
    static void afterAll() {
        mysql.stop();
    }

    @BeforeEach
    void setUp(){
        insurancePolicyDbRepository.deleteAllInBatch();
    }

    @Test
    void shouldFindInsurancePolicies(){
        InsurancePolicyEntity insurancePolicy = new InsurancePolicyEntity("toto",
                InsurancePolicyEntity.PolicyStatus.ACTIVE, LocalDate.now(), LocalDate.now());
        InsurancePolicyEntity savedInsurancePolicy = insurancePolicyDbRepository.saveAndFlush(insurancePolicy);

        List<InsurancePolicyEntity> insurancePolicyEntities = insurancePolicyDbRepository.findAll();
        assertAll("Insurance Policies",
                () -> assertEquals(1, insurancePolicyEntities.size()));
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);

        registry.add("spring.sql.init.mode", () -> DatabaseInitializationMode.ALWAYS);
    }
}