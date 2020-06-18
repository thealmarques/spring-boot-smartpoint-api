package com.course.pontointeligente.security.services;

import com.course.pontointeligente.entities.Employee;
import com.course.pontointeligente.security.JwtUserFactory;
import com.course.pontointeligente.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeService.findByEmail(username);

        if (employee.isPresent()) {
            return JwtUserFactory.create(employee.get());
        }

        throw new UsernameNotFoundException("Email not found.");
    }
}
