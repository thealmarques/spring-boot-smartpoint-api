package com.course.pontointeligente.services;

import com.course.pontointeligente.entities.Company;

import java.util.Optional;

public interface CompanyService {
    /**
     * Returns company given an company ID.
     * @param companyID ID of the company
     * @return the company object
     */
    Optional<Company> findByCompanyID(String companyID);

    /**
     * Registers a new company in the DB.
     * @param company compnay object
     * @return the persisted company object
     */
    Company persist(Company company);
}
