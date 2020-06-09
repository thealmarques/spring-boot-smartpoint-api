package com.course.pontointeligente.controllers;

import com.course.pontointeligente.dto.RegisterCompanyAdminDto;
import com.course.pontointeligente.entities.Company;
import com.course.pontointeligente.entities.Employee;
import com.course.pontointeligente.enums.EProfile;
import com.course.pontointeligente.response.Response;
import com.course.pontointeligente.services.CompanyService;
import com.course.pontointeligente.services.EmployeeService;
import com.course.pontointeligente.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/register-admin")
@CrossOrigin(origins = "*")
public class RegisterCompanyAdminController {
    private static final Logger log = LoggerFactory.getLogger(RegisterCompanyAdminController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompanyService companyService;

    /**
     * Registers a company administrator
     * @param registerCompanyAdminDto
     * @param result
     * @return ResponseEntity<Response<RegisterCompanyAdminDto>>
     * @throws NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<Response<RegisterCompanyAdminDto>> register(@Valid
                                                                      @RequestBody
                                                                              RegisterCompanyAdminDto registerCompanyAdminDto,
                                                                      BindingResult result) throws NoSuchAlgorithmException {
        log.info("Registering admin... {}", registerCompanyAdminDto.toString());
        Response<RegisterCompanyAdminDto> response = new Response<RegisterCompanyAdminDto>();
        validateData(registerCompanyAdminDto, result);

        if (result.hasErrors()) {
            log.error("Error validating data... {}", registerCompanyAdminDto);
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Company company = this.getCompanyFromDto(registerCompanyAdminDto);
        this.companyService.persist(company);

        Employee employee = this.getEmployeeFromDto(registerCompanyAdminDto);
        employee.setCompany(company);
        this.employeeService.persist(employee);

        response.setData(this.registerCompanyAdminDto(employee));
        return ResponseEntity.ok(response);
    }

    /**
     * Validates received data from the HTTP request
     * @param registerCompanyAdminDto
     * @param result
     */
    private void validateData(RegisterCompanyAdminDto registerCompanyAdminDto, BindingResult result) {
        this.companyService.findByCompanyID(registerCompanyAdminDto.getCompanyID())
                .ifPresent(company -> result.addError(new ObjectError("company", "The company already exists.")));

        this.employeeService.findByPersonID(registerCompanyAdminDto.getPersonID())
                .ifPresent(emp -> result.addError(new ObjectError("employee", "Employee already exists.")));

        this.employeeService.findByEmail(registerCompanyAdminDto.getEmail())
                .ifPresent(emp -> result.addError(new ObjectError("employee", "Email already exists.")));
    }

    /**
     * Converts DTO to company
     * @param registerCompanyAdminDto
     * @return Company
     */
    private Company getCompanyFromDto(RegisterCompanyAdminDto registerCompanyAdminDto) {
        Company company = new Company();
        company.setCompanyID(registerCompanyAdminDto.getCompanyID());
        company.setSocialReason(registerCompanyAdminDto.getSocialReason());

        return company;
    }

    /**
     * Converts DTO to employee
     * @param registerCompanyAdminDto
     * @return Employee
     */
    private Employee getEmployeeFromDto(RegisterCompanyAdminDto registerCompanyAdminDto) {
        Employee employee = new Employee();
        employee.setName(registerCompanyAdminDto.getName());
        employee.setEmail(registerCompanyAdminDto.getEmail());
        employee.setProfile(EProfile.ROLE_ADMIN);
        employee.setPersonID(registerCompanyAdminDto.getPersonID());
        employee.setPassword(PasswordUtils.generateBCrypt(registerCompanyAdminDto.getPassword()));

        return employee;
    }

    /**
     * Converts entities data to DTO
     * @param employee
     * @return RegisterCompanyAdminDto
     */
    private RegisterCompanyAdminDto registerCompanyAdminDto(Employee employee) {
        RegisterCompanyAdminDto registerCompanyAdminDto = new RegisterCompanyAdminDto();
        registerCompanyAdminDto.setId(employee.getId());
        registerCompanyAdminDto.setName(employee.getName());
        registerCompanyAdminDto.setEmail(employee.getEmail());
        registerCompanyAdminDto.setCompanyID(employee.getCompany().getCompanyID());
        registerCompanyAdminDto.setSocialReason(employee.getCompany().getSocialReason());
        registerCompanyAdminDto.setPersonID(employee.getPersonID());

        return registerCompanyAdminDto;
    }
}
