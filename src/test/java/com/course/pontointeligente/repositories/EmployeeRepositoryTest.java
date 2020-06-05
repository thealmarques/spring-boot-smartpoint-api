package com.course.pontointeligente.repositories;

import com.course.pontointeligente.entities.Company;
import com.course.pontointeligente.entities.Employee;
import com.course.pontointeligente.enums.EProfile;
import com.course.pontointeligente.utils.PasswordUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired CompanyRepository companyRepository;

    private static final String EMAIL = "email@test.com";
    private static final String PERSONID = "8772451";

    @BeforeEach
    public void setUp() throws Exception {
        Company company = this.companyRepository.save(getCompany());
        this.employeeRepository.save(getEmployee(company));
    }

    @AfterEach
    public void tearDown() {
        this.companyRepository.deleteAll();
    }

    @Test
    public void testFindByEmail() {
        Employee employee = this.employeeRepository.findByEmail(EMAIL);

        assertEquals(EMAIL, employee.getEmail());
    }

    @Test
    public void testFindByPersonID() {
        Employee employee = this.employeeRepository.findByPersonID(PERSONID);

        assertEquals(PERSONID, employee.getPersonID());
    }

    @Test
    public void testFindByPersonIDOrEmail() {
        Employee employee = this.employeeRepository.findByPersonIDOrEmail(PERSONID, EMAIL);

        assertNotNull(employee);
    }

    private Company getCompany() {
        Company company = new Company();
        company.setSocialReason("Test company");
        company.setCompanyID("9999");
        return company;
    }

    private Employee getEmployee(Company company) {
        Employee employee = new Employee();
        employee.setName("James Dames");
        employee.setProfile(EProfile.ROLE_USER);
        employee.setPassword(PasswordUtils.generateBCrypt("hello_james_dames"));
        employee.setPersonID(PERSONID);
        employee.setEmail(EMAIL);
        employee.setCompany(company);
        return employee;
    }
}
