package com.course.pontointeligente.security;

import com.course.pontointeligente.entities.Employee;
import com.course.pontointeligente.enums.EProfile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class JwtUserFactory {
    /**
     * Converts and generates Jwt user given an employee.
     *
     * @param employee
     * @return JwtUser
     */
    public static JwtUser create(Employee employee) {
        return new JwtUser(employee.getId(), employee.getEmail(), employee.getPassword(),
                mapToGrantedAuthorities(employee.getProfile()));
    }

    /**
     * Converts user profile to Spring Security format.
     *
     * @param profile
     * @return List<GrantedAuthority>
     */
    private static List<GrantedAuthority> mapToGrantedAuthorities(EProfile profile) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(profile.toString()));
        return authorities;
    }
}
