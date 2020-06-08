package com.course.pontointeligente.services.impl;

import com.course.pontointeligente.entities.Company;
import com.course.pontointeligente.repositories.CompanyRepository;
import com.course.pontointeligente.services.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private static final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Optional<Company> findByCompanyID(String companyID) {
        log.info("Searching company with ID {}", companyID);
        return Optional.ofNullable(companyRepository.findByCompanyID(companyID));
    }

    @Override
    public Company persist(Company company) {
        log.info("Persisting company {}", company);
        return this.companyRepository.save(company);
    }
}
