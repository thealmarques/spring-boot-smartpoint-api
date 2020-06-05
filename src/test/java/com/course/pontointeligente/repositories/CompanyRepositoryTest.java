package com.course.pontointeligente.repositories;

import com.course.pontointeligente.entities.Company;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CompanyRepositoryTest {
    @Autowired
    private CompanyRepository companyRepository;

    private static final String COMPANYID = "762123";

    @BeforeEach
    public void setUp() throws Exception {
        Company company = new Company();
        company.setSocialReason("Example company");
        company.setCompanyID(COMPANYID);
        this.companyRepository.save(company);
    }

    @AfterEach
    public void tearDown() {
        this.companyRepository.deleteAll();
    }

    @Test
    public void testFindByCompanyID() {
        // Given
        // When
        Company company = this.companyRepository.findByCompanyID(COMPANYID);

        // Then
        assertEquals(COMPANYID, company.getCompanyID());
    }
}
