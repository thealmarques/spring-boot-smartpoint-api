package com.course.pontointeligente.repositories;

import com.course.pontointeligente.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByPersonID(String personID);

    Employee findByEmail(String email);

    Employee findByPersonIDOrEmail(String personID, String email);
}
