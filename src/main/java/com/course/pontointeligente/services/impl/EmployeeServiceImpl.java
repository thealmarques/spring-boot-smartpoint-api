package com.course.pontointeligente.services.impl;

import com.course.pontointeligente.entities.Employee;
import com.course.pontointeligente.repositories.EmployeeRepository;
import com.course.pontointeligente.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee persist(Employee employee) {
        log.info("Persisting employee {}", employee);
        return this.employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> findByPersonID(String personID) {
        log.info("Finding employee with personID {}", personID);
        return Optional.ofNullable(this.employeeRepository.findByPersonID(personID));
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        log.info("Finding employee with email {}", email);
        return Optional.ofNullable(this.employeeRepository.findByEmail(email));
    }

    @Override
    public Optional<Employee> findById(Long id) {
        log.info("Finding employee with id {}", id);
        return Optional.ofNullable(this.employeeRepository.findById(id).orElse(null));
    }
}
