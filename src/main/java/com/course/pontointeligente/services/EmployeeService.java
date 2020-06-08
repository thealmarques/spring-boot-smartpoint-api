package com.course.pontointeligente.services;

import com.course.pontointeligente.entities.Employee;

import java.util.Optional;

public interface EmployeeService {
    /**
     * Persists an Employee in the DB.
     *
     * @param employee
     * @return the persisted employee
     */
    Employee persist(Employee employee);

    /**
     * Searches an employee given a personID
     *
     * @param personID
     * @return Optional<Employee>
     */
    Optional<Employee> findByPersonID(String personID);

    /**
     * Searches an returns an employee given an email
     *
     * @param email
     * @return Optional<Employee>
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Searches and returns an Employee given an ID.
     *
     * @param id
     * @return Optional<Funcionario>
     */
    Optional<Employee> findById(Long id);
}
