package com.course.pontointeligente.repositories;

import com.course.pontointeligente.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Transactional(readOnly = true)
    Company findByCompanyID(String companyID);
}
