package com.course.pontointeligente.repositories;

import com.course.pontointeligente.entities.Company;
import com.course.pontointeligente.entities.Employee;
import com.course.pontointeligente.entities.Launch;
import com.course.pontointeligente.enums.EProfile;
import com.course.pontointeligente.enums.EType;
import com.course.pontointeligente.utils.PasswordUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class LaunchRepositoryTest {
    @Autowired
    private LaunchRepository launchRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private Long employeeID;

    @BeforeEach
    public void setUp() throws Exception {
        Company company = this.companyRepository.save(getCompany());

        Employee employee = this.employeeRepository.save(getEmployee(company));
        this.employeeID = employee.getId();

        this.launchRepository.save(getLaunch(employee));
        this.launchRepository.save(getLaunch(employee));
    }

    @AfterEach
    public void tearDown() throws Exception {
        this.companyRepository.deleteAll();
        this.launchRepository.deleteAll();
        this.employeeRepository.deleteAll();
    }

    @Test
    public void testFindLaunchByEmployeeID() {
        List<Launch> launches = this.launchRepository.findByEmployeeId(this.employeeID);

        assertEquals(launches.size(), 2);
    }

    @Test
    public void testFindLaunchByEmployeeIDWithPagination() {
        PageRequest page = PageRequest.of(0, 10);
        Page<Launch> launches = this.launchRepository.findByEmployeeId(this.employeeID, page);

        assertEquals(launches.getTotalElements(), 2);
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
        employee.setPersonID("81231");
        employee.setEmail("email@test.com");
        employee.setCompany(company);
        return employee;
    }

    private Launch getLaunch(Employee employee) {
        Launch launch = new Launch();
        launch.setDate(new Date());
        launch.setType(EType.START_LUNCH);
        launch.setEmployee(employee);
        return launch;
    }
}
