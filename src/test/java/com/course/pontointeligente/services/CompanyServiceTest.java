package com.course.pontointeligente.services;

import com.course.pontointeligente.entities.Company;
import com.course.pontointeligente.repositories.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class CompanyServiceTest {
    @MockBean
    CompanyRepository companyRepository;

    @Autowired
    CompanyService companyService;

    private static final String COMPANYID = "321555";

    @BeforeEach
    public void setUp() {
        BDDMockito.given(this.companyRepository.findByCompanyID(Mockito.anyString())).willReturn(new Company());
        BDDMockito.given(this.companyRepository.save(Mockito.any(Company.class))).willReturn(new Company());
    }

    @Test
    public void testFindCompanyByID() {
        Optional<Company> company = this.companyService.findByCompanyID(COMPANYID);

        assertTrue(company.isPresent());
    }

    @Test
    public void testPersistCompany() {
        Company company = this.companyService.persist(new Company());

        assertNotNull(company);
    }
}
