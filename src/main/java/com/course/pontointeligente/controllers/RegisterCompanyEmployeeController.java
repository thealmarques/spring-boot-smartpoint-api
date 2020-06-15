package com.course.pontointeligente.controllers;

import com.course.pontointeligente.dto.RegisterCompanyEmployeeDto;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping("/api/register-employee")
@CrossOrigin(origins = "*")
public class RegisterCompanyEmployeeController {
    private static final Logger log = LoggerFactory.getLogger(RegisterCompanyEmployeeController.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmployeeService employeeService;

    /**
     * Registers new employee to the DB.
     * @param registerCompanyEmployeeDto employee DTO
     * @return Response entity
     * @throws NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<Response<RegisterCompanyEmployeeDto>> register(
            @Valid @RequestBody RegisterCompanyEmployeeDto registerCompanyEmployeeDto,
            BindingResult result) throws NoSuchAlgorithmException {
        log.info("Registering employee {}", registerCompanyEmployeeDto);

        Response<RegisterCompanyEmployeeDto> response = new Response<RegisterCompanyEmployeeDto>();

        validateData(registerCompanyEmployeeDto, result);

        if (result.hasErrors()) {
            log.error("Error while validating data {}", registerCompanyEmployeeDto);
            result.getAllErrors().forEach(objectError -> response.getErrors().add(objectError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Employee employee = this.convertDtoToEmployee(registerCompanyEmployeeDto, result);
        Optional<Company> company = this.companyService.findByCompanyID(registerCompanyEmployeeDto.getCompanyID());
        company.ifPresent(comp -> employee.setCompany(comp));

        this.employeeService.persist(employee);

        response.setData(this.convertToDto(employee));
        return ResponseEntity.ok(response);
    }

    /**
     * Checks if the company is registered and the employee doesn't exist.
     * @param registerCompanyEmployeeDto
     * @param result
     */
    private void validateData(RegisterCompanyEmployeeDto registerCompanyEmployeeDto, BindingResult result) {
        Optional<Company> company = this.companyService.findByCompanyID(registerCompanyEmployeeDto.getCompanyID());

        if (!company.isPresent()) {
            result.addError(new ObjectError("company", "Company not registered"));
        }

        this.employeeService.findByPersonID(registerCompanyEmployeeDto.getPersonID())
                .ifPresent(employee -> result.addError(new ObjectError("employee", "PersonID already exists.")));

        this.employeeService.findByEmail(registerCompanyEmployeeDto.getEmail())
                .ifPresent(employee -> result.addError(new ObjectError("employee", "Email already exists.")));
    }

    private Employee convertDtoToEmployee(RegisterCompanyEmployeeDto registerCompanyEmployeeDto, BindingResult result) throws NoSuchAlgorithmException {
        Employee employee = new Employee();
        employee.setName(registerCompanyEmployeeDto.getName());
        employee.setEmail(registerCompanyEmployeeDto.getEmail());
        employee.setPersonID(registerCompanyEmployeeDto.getPersonID());
        employee.setProfile(EProfile.ROLE_USER);
        employee.setPassword(PasswordUtils.generateBCrypt(registerCompanyEmployeeDto.getPassword()));

        registerCompanyEmployeeDto.getPriceHour()
                .ifPresent(price -> employee.setPricePerHour(new BigDecimal(price)));
        registerCompanyEmployeeDto.getHoursLunch()
                .ifPresent(lunch -> employee.setLunchHours(Float.valueOf(lunch)));
        registerCompanyEmployeeDto.getHoursPerDay()
                .ifPresent(day -> employee.setHoursPerDay(Float.valueOf(day)));

        return employee;
    }

    /**
     * Converts employee to DTO
     * @param employee
     * @return RegisterCompanyEmployeeDto
     */
    private RegisterCompanyEmployeeDto convertToDto(Employee employee) {
        RegisterCompanyEmployeeDto registerCompanyEmployeeDto = new RegisterCompanyEmployeeDto();
        registerCompanyEmployeeDto.setId(employee.getId());
        registerCompanyEmployeeDto.setName(employee.getName());
        registerCompanyEmployeeDto.setEmail(employee.getEmail());
        registerCompanyEmployeeDto.setPersonID(employee.getPersonID());
        registerCompanyEmployeeDto.setCompanyID(employee.getCompany().getCompanyID());
        employee.getLunchHoursOpt()
                .ifPresent(lunch -> registerCompanyEmployeeDto.setHoursLunch(
                        Optional.of(Float.toString(lunch))
                ));
        employee.getPricePerHourOpt()
                .ifPresent(price -> registerCompanyEmployeeDto.setPriceHour(
                        Optional.of(price.toString())
                ));
        employee.getHoursPerDayOpt()
                .ifPresent(hours -> registerCompanyEmployeeDto.setHoursPerDay(
                        Optional.of(Float.toString(hours))
                ));
        return registerCompanyEmployeeDto;
    }
}
