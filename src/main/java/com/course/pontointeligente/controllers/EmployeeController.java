package com.course.pontointeligente.controllers;

import com.course.pontointeligente.dto.EmployeeDto;
import com.course.pontointeligente.entities.Employee;
import com.course.pontointeligente.response.Response;
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
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {
    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    /**
     * Updates an employee given the employee ID
     * @param id
     * @param employeeDto
     * @param result
     * @return
     * @throws NoSuchAlgorithmException
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<EmployeeDto>> update(@PathVariable("id") Long id,
                                                        @Valid @RequestBody EmployeeDto employeeDto,
                                                        BindingResult result) throws NoSuchAlgorithmException {
        log.info("Updating employee... {}", employeeDto.toString());
        Response<EmployeeDto> response = new Response<>();

        Optional<Employee> employee = this.employeeService.findById(id);
        if (!employee.isPresent()) {
            result.addError(new ObjectError("employee", "Employee not found"));
        }

        this.updateEmployeeData(employee.get(), employeeDto, result);

        if (result.hasErrors()) {
            log.error("Error validating employee: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.employeeService.persist(employee.get());
        response.setData(this.convertToDto(employee.get()));

        return ResponseEntity.ok(response);
    }

    /**
     * Converts employee entity to DTO
     * @param employee
     * @return
     */
    private EmployeeDto convertToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setEmail(employee.getEmail());
        employee.getHoursPerDayOpt()
                .ifPresent(hoursDay -> employeeDto.setHoursPerDay(Optional.of(Float.toString(hoursDay))));
        employee.getLunchHoursOpt()
                .ifPresent(lunch -> employeeDto.setLunchHours(Optional.of(Float.toString(lunch))));
        employee.getPricePerHourOpt()
                .ifPresent(price -> employeeDto.setPricePerHour(Optional.of(price.toString())));

        return employeeDto;
    }

    /**
     * Updates employee data with the given DTO
     * @param employee
     * @param employeeDto
     * @param result
     * @throws NoSuchAlgorithmException
     */
    private void updateEmployeeData(Employee employee, EmployeeDto employeeDto, BindingResult result) throws NoSuchAlgorithmException {
        employee.setName(employeeDto.getName());

        if(!employee.getEmail().equals(employeeDto.getEmail())) {
            this.employeeService.findByEmail(employeeDto.getEmail())
                    .ifPresent(emp -> result.addError(new ObjectError("email", "Email already exists")));
            employee.setEmail(employeeDto.getEmail());
        }

        employee.setLunchHours(null);
        employeeDto.getLunchHours()
                .ifPresent(lunch -> employee.setLunchHours(Float.valueOf(lunch)));

        employee.setHoursPerDay(null);
        employeeDto.getHoursPerDay()
                .ifPresent(hoursDay -> employee.setHoursPerDay(Float.valueOf(hoursDay)));

        employee.setPricePerHour(null);
        employeeDto.getPricePerHour()
                .ifPresent(price -> employee.setPricePerHour(new BigDecimal(price)));

        if (employeeDto.getPassword().isPresent()) {
            employee.setPassword(PasswordUtils.generateBCrypt(employeeDto.getPassword().get()));
        }
    }
}
